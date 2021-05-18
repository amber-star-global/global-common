package com.global.common.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQSendMessage {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param sendMessage 发送消息
     */
    public void sendMessage(String exchange, String routingKey, String sendMessage) {
        log.info("exchange:{}, routingKey:{}, message:{}", exchange, routingKey, sendMessage);
        rabbitTemplate.convertAndSend(exchange, routingKey, sendMessage);
    }
}
