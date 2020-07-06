package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.car.biz.AddressService;
import com.fh.shop.api.car.po.Address;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.car.po.CarItem;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.config.RabbitConfig;
import com.fh.shop.api.exception.StockLessException;
import com.fh.shop.api.goods.mapper.GoodsMapper;
import com.fh.shop.api.order.mapper.OrderItemMapper;
import com.fh.shop.api.order.mapper.OrderMapper;
import com.fh.shop.api.order.mapper.PayLogMapper;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.order.po.PayLog;
import com.fh.shop.api.util.KeyUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public ServerResponse generateConfirmOrder(Long id) {
        return null;
    }

    @Override
    public ServerResponse generateOrder(OrderParam orderParam) {
        Long memberId = orderParam.getMemberId();
        RedisUtil.delete(KeyUtil.buildOrderErrorKey(memberId));
        RedisUtil.delete(KeyUtil.buildOrderKey(memberId));
        RedisUtil.delete(KeyUtil.buildOrderStockLessKey(memberId));

        String jsonString = JSONObject.toJSONString(orderParam);
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, jsonString);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse getResult(Long id) {
        if (RedisUtil.exist(KeyUtil.buildOrderStockLessKey(id))) {
            return ServerResponse.error(ResponseEnum.ORDER_IS_LESS);
        }
        if (RedisUtil.exist(KeyUtil.buildOrderKey(id))) {
            return ServerResponse.success();
        }
        if (RedisUtil.exist(KeyUtil.buildOrderErrorKey(id))) {
            return ServerResponse.success(ResponseEnum.ORDER_IS_CREATE_FAIL);
        }
        return ServerResponse.success(ResponseEnum.ORDER_IS_QUEUE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Car car, OrderParam orderParam) {
        List<CarItem> carItems = car.getCarItems();
        for (CarItem carItem : carItems) {
            int rowCount = goodsMapper.updateStock(carItem.getGoodsId(), carItem.getNum());
            if (rowCount == 0) {
                throw new StockLessException("库存不足，下单失败！！!");
            }
            Long addressId = orderParam.getAddressId();
            Address address = addressService.getAddressById(addressId);
            Order order = new Order();
            String idStr = IdWorker.getIdStr();
            order.setId(idStr);
            order.setAddressId(addressId);
            order.setPayType(orderParam.getPayType());
            order.setCreateTime(new Date());
            order.setStatus(SystemConstant.OrderStatus.WAIT_PAY);

            BigDecimal totalPrice = new BigDecimal(car.getTotalPrice());

            order.setTotalPrice(totalPrice);
            order.setTotalCount((long) car.getTotalNum());
            Long memberId = orderParam.getMemberId();
            order.setMemberId(memberId);
            order.setArea(address.getArea());
            order.setPhone(address.getPhone());
            order.setAddressName(address.getName());
            orderMapper.insert(order);
            List<OrderItem> orderItems = new ArrayList<>();
            for (CarItem item : carItems) {
                OrderItem o = new OrderItem();
                o.setMemberId(memberId);
                o.setCount((long) item.getNum());
                BigDecimal totalPrice1 = new BigDecimal(item.getSubPrice());
                o.setSubTotalPrice(totalPrice1);
                o.setPrice(new BigDecimal(item.getPrice()));
                o.setProductId(item.getGoodsId());
                o.setOrderId(idStr);
                o.setProductName(item.getName());
                o.setImg(item.getImg());
                orderItems.add(o);
            }
            orderItemMapper.insertBatchs(orderItems);
            PayLog payLog = new PayLog();
            payLog.setOrderId(idStr);
            payLog.setCreateDate(new Date());
            payLog.setMemberId(memberId);
            payLog.setStatus(SystemConstant.PayStatus.WAIT_PAY);
            payLog.setPayType(orderParam.getPayType());
            String outTradeNo = IdWorker.getIdStr();
            payLog.setOutTradeNo(outTradeNo);
           payLog.setPayMoney(totalPrice);
            payLogMapper.insert(payLog);

            String payLogJson = JSONObject.toJSONString(payLog);
            RedisUtil.set(KeyUtil.buildPayLogKey(memberId), payLogJson);

            RedisUtil.delete(KeyUtil.buildCarKey(memberId));
            RedisUtil.set(KeyUtil.buildOrderKey(memberId), "ok");//下单成功
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Order order) {
        orderMapper.updateById(order);

    }
}
