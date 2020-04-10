package com.global.common.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.util.concurrent.Executor;

@Slf4j
public class RabbitMQConfig extends AbstractRabbitMQConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    /**
     * 创建MQ连接工厂
     * @param host 连接地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @param virtualHost VHost
     */
    protected ConnectionFactory connectionFactory(String host, int port, String username,
                                                  String password, String virtualHost) {
        log.info("创建RabbitMQ连接工厂...");
        log.info("host:{}, port:{}, username:{}, password:{}, virtualHost:{}",
                host, port, username, password, virtualHost);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    /**
     * 创建MQ管理
     * @param connectionFactory 连接工厂
     */
    protected RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        log.info("创建rabbitAdmin...");
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 创建MQ模板
     * @param connectionFactory 连接工厂
     */
    protected RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        log.info("创建rabbitTemplate...");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
        rabbitTemplate.setConfirmCallback(this::confirm);
        return rabbitTemplate;
    }

    /**
     * 创建MQ消息监听工厂
     * @param connectionFactory 连接工厂
     * @param taskExecutor 线程池
     */
    protected SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, Executor taskExecutor){
        log.info("创建rabbitListenerContainerFactory...");
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        if (taskExecutor != null)
            listenerContainerFactory.setTaskExecutor(taskExecutor);
        listenerContainerFactory.setMessageConverter(new SimpleMessageConverter());
        return listenerContainerFactory;
    }

    /**
     * 注册routingKey
     * @param rabbitAdmin MQ管理
     * @param queue 队列
     * @param exchange 交换机
     * @param routingKeys 路由键
     */
    protected RabbitAdmin registryRoutes(RabbitAdmin rabbitAdmin, Queue queue, Exchange exchange, String... routingKeys){
        for (String routingKey : routingKeys) {
            Binding productToCustomerRequest = BindingBuilder.bind(queue).to(exchange)
                    .with(routingKey).noargs();
            rabbitAdmin.declareBinding(productToCustomerRequest);
        }
        return rabbitAdmin;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        log.info("message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
                message, replyCode, replyText, exchange, routingKey);
    }
}
