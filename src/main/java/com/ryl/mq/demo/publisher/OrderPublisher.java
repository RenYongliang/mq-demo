package com.ryl.mq.demo.publisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ryl
 * @description
 * @date 2020-12-17 11:13:30
 */
@Component
@AllArgsConstructor
@Slf4j
public class OrderPublisher {

    private final Environment env;
    private final RabbitTemplate rabbitTemplate;

    public void sendMsg(String orderNo) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.order.producer.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.order.producer.routing.key.name"));
            rabbitTemplate.convertAndSend(orderNo);
            log.info("orderDeadQueue success:{}",orderNo);
        } catch (Exception ex) {
            log.info("orderDeadQueue error:{}",orderNo,ex.fillInStackTrace());
        }
    }
}
