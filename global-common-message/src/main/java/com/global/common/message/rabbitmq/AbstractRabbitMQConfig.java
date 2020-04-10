package com.global.common.message.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.concurrent.Executor;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-09 上午 11:19
 * @Version: v1.0
 */
public abstract class AbstractRabbitMQConfig {


    /**
     * 创建MQ连接工厂
     * @param host 连接地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @param virtualHost VHost
     */
    abstract ConnectionFactory connectionFactory(String host, int port, String username, String password, String virtualHost);


    /**
     * 创建MQ管理
     * @param connectionFactory 连接工厂
     */
    abstract RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory);


    /**
     * 创建MQ模板
     * @param connectionFactory 连接工厂
     */
    abstract RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory);

    /**
     * 创建MQ消息监听工厂
     * @param connectionFactory 连接工厂
     * @param taskExecutor 线程池
     */
    abstract SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Executor taskExecutor);
}
