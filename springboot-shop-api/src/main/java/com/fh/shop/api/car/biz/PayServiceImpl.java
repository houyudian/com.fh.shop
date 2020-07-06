package com.fh.shop.api.car.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.mapper.PayLogMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.PayLog;
import com.fh.shop.api.util.KeyUtil;
import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public ServerResponse checkPayStatus(Long id) {
        String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(id));

        PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
        String outTradeNo = payLog.getOutTradeNo();
        String orderId = payLog.getOrderId();

        try {
            MyConfig myWxConfig = new MyConfig();
            WXPay wxPay = new WXPay(myWxConfig);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outTradeNo);
            int count = 0;
            while (true) {
                Map<String, String> resp = wxPay.orderQuery(data);

                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equals("SUCCESS")) {
                    return ServerResponse.error(9999, return_msg);
                }
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if (!result_code.equals("SUCCESS")) {
                    return ServerResponse.error(9999, err_code_des);
                }
                String trade_state = resp.get("trade_state");
                String trade_state_desc=resp.get("trade_state_desc");
                if (trade_state.equals("SUCCESS")) {

                    Order order = new Order();
                    order.setId(orderId);
                    order.setStatus(SystemConstant.OrderStatus.PAY_SUCCESS);
                    order.setPayTime(new Date());
                    orderService.updateOrder(order);
                    String transaction_id = resp.get("transaction_id");

                    PayLog log = new PayLog();
                    log.setOutTradeNo(outTradeNo);
                    log.setCreateDate(new Date());
                    log.setStatus(SystemConstant.PayStatus.PAY_SUCCESS);
                    log.setTransactionId(transaction_id);
                    payLogMapper.updateById(log);
                    RedisUtil.delete(KeyUtil.buildPayLogKey(id));

                    return ServerResponse.success();
                }
                System.out.println(resp);
                Thread.sleep(2000);
                count++;
                if (count > 30) {
                    return ServerResponse.error(ResponseEnum.ORDER_PAY_TIME_OUT);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

    @Override
    public ServerResponse createNative(Long memberId) {
        try {

            String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(memberId));

            PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);

            BigDecimal payMoney = payLog.getPayMoney();

            int payPrice = payMoney.multiply(new BigDecimal(100)).intValue();
            String outTradeNo = payLog.getOutTradeNo();

            MyConfig myWxConfig = new MyConfig();
            WXPay wxPay = new WXPay(myWxConfig);
            Map<String, String> data = new HashMap<String, String>();

            data.put("body", "飞狐1906B--支付平台");
            data.put("out_trade_no", outTradeNo);
            data.put("fee_type", "CNY");
            Date date = DateUtils.addMinutes(new Date(), 2);
            //data.put("time_expire", DateUtil.getYyyyMMhhmmss(date, DateUtil.yyyyMMhhmmss));
            data.put("total_fee", payPrice + "");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");

            Map<String, String> resulMap = wxPay.unifiedOrder(data);
            System.out.println(resulMap);
            //验证接口是否能够正常访问
            String returnCode = resulMap.get("return_code");
            String returnMsg = resulMap.get("return_msg");
            if (!"SUCCESS".equalsIgnoreCase(returnCode)) {
                return ServerResponse.error(99999, returnMsg);
            }
            //验证业务是否成功
            String resultCode = resulMap.get("result_code");
            String errorCodeDes = resulMap.get("err_code_des");
            if (!"SUCCESS".equalsIgnoreCase(resultCode)) {
                return ServerResponse.error(99999, errorCodeDes);
            }
            String url = resulMap.get("code_url");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("codeUrl", url);
            map.put("outTradeNo", outTradeNo);
            map.put("payMoney", payMoney.toString());
            return ServerResponse.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.CRATER_PAY_ERROR);
        }

    }
}
