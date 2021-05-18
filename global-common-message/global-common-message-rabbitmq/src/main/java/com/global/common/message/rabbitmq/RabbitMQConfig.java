package com.global.common.message.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-04-01 上午 11:47
 * @Version: v1.0
 */
public interface RabbitMQConfig {

    /**
     * 创建MQ连接工厂
     */
    ConnectionFactory connectionFactory();


    /**
     * 创建MQ管理
     * @param connectionFactory 连接工厂
     */
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory);


    /**
     * 创建MQ模板
     * @param connectionFactory 连接工厂
     */
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory);

    /**
     * 创建MQ消息监听工厂
     * @param connectionFactory 连接工厂
     */
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory);
}
