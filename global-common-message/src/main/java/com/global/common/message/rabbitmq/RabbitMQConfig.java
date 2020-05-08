package com.global.common.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
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
        // 需要手动确认消息
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /**
     * 创建MQ消息监听工厂
     * @param connectionFactory 连接工厂
     * @param taskExecutor 线程池
     */
    protected SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, Executor taskExecutor, AcknowledgeMode acknowledgeMode){
        log.info("创建rabbitListenerContainerFactory...");
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(new SimpleMessageConverter());
        listenerContainerFactory.setAcknowledgeMode(acknowledgeMode);
        if (taskExecutor != null)
            listenerContainerFactory.setTaskExecutor(taskExecutor);
        return listenerContainerFactory;
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("本次消息的唯一标识: {}", correlationData.getId());
        if (ack)
            log.info("消息已确认");
        else
            log.info("消息被拒绝, 拒绝原因: {}", cause);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        log.info("message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
                message, replyCode, replyText, exchange, routingKey);
    }
}
