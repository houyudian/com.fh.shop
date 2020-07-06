package com.fh.shop.api.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.car.po.Car;
import com.fh.shop.api.car.po.CarItem;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.config.RabbitConfig;
import com.fh.shop.api.exception.StockLessException;
import com.fh.shop.api.goods.mapper.GoodsMapper;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.rabbitmq.po.MsgLog;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MailUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelloReceiver {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MailUtil mailUtil;

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void handleOrderMessage(String orderJson, Message message, Channel channel) throws IOException {

        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        OrderParam orderParam = JSONObject.parseObject(orderJson, OrderParam.class);

        Long memberId = orderParam.getMemberId();
        String carJson = RedisUtil.get(KeyUtil.buildCarKey(memberId));
        Car car = JSONObject.parseObject(carJson, Car.class);
        if (car==null){
            return ;
        }

        List<CarItem> carItems = car.getCarItems();
        List<Long> idList = carItems.stream().map(x -> x.getGoodsId()).collect(Collectors.toList());
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.in("id", idList);
        List<Goods> goodsList = goodsMapper.selectList(goodsQueryWrapper);
        for (CarItem item : carItems) {
            for (Goods goods : goodsList) {
                if (item.getGoodsId().longValue() == goods.getId().longValue()) {
                    if (item.getNum() == goods.getStock()) {
                        RedisUtil.set(KeyUtil.buildOrderStockLessKey(memberId), "stock less");
                        channel.basicAck(deliveryTag, false);
                        return;
                    }
                }
            }
        }
        try {
            orderService.createOrder(car, orderParam);
            channel.basicAck(deliveryTag, false);
        } catch (StockLessException e) {
            e.printStackTrace();
            RedisUtil.set(KeyUtil.buildOrderStockLessKey(memberId), "stock less");

            channel.basicAck(deliveryTag, false);
        }catch (Exception e){
            e.printStackTrace();
            RedisUtil.set(KeyUtil.buildOrderErrorKey(memberId), "error");
            channel.basicNack(deliveryTag,false,false);
        }

    }

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE)
    public void handleMailMessage(String mailJson, Message message, Channel channel) throws IOException {
        MessageProperties m = message.getMessageProperties();
        long tag = m.getDeliveryTag();
        try {

            MsgLog msg = JSONObject.parseObject(mailJson, MsgLog.class);
            String msgId = msg.getMsgId();

            if (RedisUtil.exist("message:" + msgId)) {
                System.out.println("消费过");
                return;
            }
            System.out.println("==========" + mailJson);
            RedisUtil.set("message:" + msgId, "ok");
            channel.basicAck(tag, false);
            RedisUtil.delete("message:" + msgId);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(tag, false, false);
        }

        MsgLog msg = JSONObject.parseObject(mailJson, MsgLog.class);
        String mail = msg.getMail();
        String realName = msg.getRealName();
        String loginDate = msg.getLoginDate();
        mailUtil.sendMail(mail, "登录提醒", "欢迎" + realName + "在" + loginDate + "登录了！！");

    }
}
