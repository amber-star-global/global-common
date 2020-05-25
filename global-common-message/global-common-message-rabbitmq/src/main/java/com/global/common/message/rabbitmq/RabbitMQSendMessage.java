package com.global.common.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQSendMessage {

    /**
     * 发送消息
     * @param rabbitTemplate 消息模板
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param sendMessage 发送消息
     */
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, String sendMessage) {
        log.info("exchange:{}, routingKey:{}, message:{}", exchange, routingKey, sendMessage);
        rabbitTemplate.convertAndSend(exchange, routingKey, sendMessage);
    }
}
