package com.ryl.mq.demo.config;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ryl
 * @description
 * @date 2020-12-02 15:11:35
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class RabbitmqConfig {

    private final Environment env;
    private final CachingConnectionFactory connectionFactory;
    private final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者实例配置
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 多消费者实力配置，针对高并发业务场景
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * 自定义配置rabbitTemplate
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) -> {
            log.info("消息发送成功：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        });
        rabbitTemplate.setReturnsCallback((message) -> {
            log.info("消息丢失：exchange({}),route({}),replyCode({}),replyText({}),message:{}",message.getExchange(),message.getRoutingKey(),message.getReplyCode(),message.getReplyText(),message.getMessage());
        });
        return rabbitTemplate;
    }

    /**
     * 创建队列
     * @return
     */
    @Bean(name = "orderDeadQueue")
    public Queue orderDeadQueue() {
        Map<String,Object> args = new HashMap<>(8);
        args.put("x-dead-letter-exchange",env.getProperty("mq.order.dead.exchange.name"));
        args.put("x-dead-letter-routing-key",env.getProperty("mq.order.dead.routing.key.name"));
        args.put("x-message-ttl",60000);
        return new Queue(env.getProperty("mq.order.dead.queue.name"),true,false,false,args);
    }

    @Bean(name = "orderRealQueue")
    public Queue orderRealQueue() {
        return new Queue(env.getProperty("mq.order.real.queue.name"),true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public TopicExchange orderProducerExchange() {
        return new TopicExchange(env.getProperty("mq.order.producer.exchange.name"),true,false);
    }

    @Bean
    public TopicExchange orderDeadExchange() {
        return new TopicExchange(env.getProperty("mq.order.dead.exchange.name"),true,false);
    }

    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding orderProducerBinding() {
        return BindingBuilder.bind(orderDeadQueue()).to(orderProducerExchange()).with(env.getProperty("mq.order.producer.routing.key.name"));
    }

    @Bean
    public Binding orderDeadBinding() {
        return BindingBuilder.bind(orderRealQueue()).to(orderDeadExchange()).with(env.getProperty("mq.order.dead.routing.key.name"));
    }




}
