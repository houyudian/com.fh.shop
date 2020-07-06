package com.fh.shop.api.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.config.RabbitConfig;
import com.fh.shop.api.goods.po.Goods;
import com.fh.shop.api.rabbitmq.po.MsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public AmqpTemplate amqpTemplate;

    public void send() {
        amqpTemplate.convertAndSend("goodsExchange", "goods", RabbitConfig.GOODS_ROUTE_kEY);
    }

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

    public void sendGoodsMessage(Goods goods) {
        String goodsJson = JSONObject.toJSONString(goods);
        amqpTemplate.convertAndSend(RabbitConfig.GOODS_EXCHANGE, RabbitConfig.GOODS_ROUTE_kEY, goodsJson);
    }

    public void sendBrandMessage(Brand brand) {
        String brandJson = JSONObject.toJSONString(brand);
        rabbitTemplate.convertAndSend(RabbitConfig.BRAND_QUEUE, brandJson);
    }

    public void sendMailMessage(MsgLog msgLog) {
        String mailJson = JSONObject.toJSONString(msgLog);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE, "", mailJson);

    }

}
