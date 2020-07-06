package com.fh.shop.api;

import com.fh.shop.api.rabbitmq.HelloSender;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;



public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topic","order","路由消息");
    }
    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "demo", "key的路由信息");
    }

    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs","","fanout模型发送得消息");
    }

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }


}
