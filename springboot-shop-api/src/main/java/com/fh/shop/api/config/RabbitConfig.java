package com.fh.shop.api.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitConfig {

    public static final String GOODS_EXCHANGE = "goodsExchange";
    public static final String GOODS_QUEUE = "goods-queue";
    public static final String BRAND_QUEUE = "brand-queue";
    public static final String ORDER_QUEUE = "order-queue";
    public static final String GOODS_ROUTE_kEY = "goods";
    public static final String GOODS_DLX_EXCHANGE = "goodsDLXExchange";

    public static final String GOODS_DLX_QUEUE = "goods-dlx-queue";

    public static final String MAIL_EXCHANGE = "mailExchange";
    public static final String MAIL_QUEUE = "mail-queue";

    @Bean
    public TopicExchange goodsDLXExchange() {
        return new TopicExchange(GOODS_DLX_EXCHANGE, true, false);
    }

    @Bean
    public Queue goodsDLXQueue() {
        return new Queue(BRAND_QUEUE, true);
    }

    @Bean
    public Binding goodsDLXBinding() {
        return BindingBuilder.bind(goodsDLXQueue()).to(goodsExchange()).with("#");
    }

    @Bean
    public DirectExchange goodsExchange() {
        return new DirectExchange(GOODS_EXCHANGE, true, false);
    }

    @Bean
    public Queue goodsQueue() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange", GOODS_DLX_EXCHANGE);
        args.put("x-max-length", 10);
        return new Queue(GOODS_QUEUE, true);
    }
    @Bean
    public Queue orderQuery() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public Binding goodsBinding() {
        return BindingBuilder.bind(goodsQueue()).to(goodsExchange()).with(GOODS_QUEUE );
    }
    @Bean
    public FanoutExchange mailExchange(){
        return new FanoutExchange(MAIL_EXCHANGE,true,false);
    }
    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE,true);
    }
    @Bean
    public Binding mailBining(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange());
    };



}
