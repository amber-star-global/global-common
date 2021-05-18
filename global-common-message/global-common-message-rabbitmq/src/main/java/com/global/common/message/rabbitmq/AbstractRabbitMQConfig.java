package com.global.common.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-09 上午 11:19
 * @Version: v1.0
 */
@Slf4j
public abstract class AbstractRabbitMQConfig implements RabbitMQConfig, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitProperties rabbitProperties;

    protected abstract Executor taskExecutor();

    /**
     * 创建MQ连接工厂
     */
    @Bean
    @ConditionalOnBean(annotation = EnableRabbit.class)
    @Override
    public ConnectionFactory connectionFactory() {
        log.info("创建RabbitMQ连接工厂...");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
        return connectionFactory;
    }

    /**
     * 创建MQ管理
     * @param connectionFactory 连接工厂
     */
    @Bean
    @ConditionalOnBean(annotation = EnableRabbit.class)
    @Override
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        log.info("创建rabbitAdmin...");
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 创建MQ模板
     * @param connectionFactory 连接工厂
     */
    @Bean
    @ConditionalOnBean(annotation = EnableRabbit.class)
    @Override
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        log.info("创建rabbitTemplate...");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        // 需要手动确认消息
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /**
     * 创建MQ消息监听工厂
     * @param connectionFactory 连接工厂
     */
    @Bean
    @ConditionalOnBean(annotation = EnableRabbit.class)
    @Override
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        log.info("创建rabbitListenerContainerFactory...");
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(new SimpleMessageConverter());
        listenerContainerFactory.setAcknowledgeMode(rabbitProperties.getListener().getSimple().getAcknowledgeMode());
        listenerContainerFactory.setTaskExecutor(taskExecutor());
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
