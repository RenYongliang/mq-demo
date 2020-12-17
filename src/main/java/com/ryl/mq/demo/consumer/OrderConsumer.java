package com.ryl.mq.demo.consumer;

import com.ryl.mq.demo.entity.Order;
import com.ryl.mq.demo.entity.OrderLog;
import com.ryl.mq.demo.service.IOrderLogService;
import com.ryl.mq.demo.service.IOrderService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author ryl
 * @description
 * @date 2020-12-17 11:32:07
 */
@Component
@AllArgsConstructor
@Slf4j
public class OrderConsumer {

    private final IOrderService iOrderService;
    private final IOrderLogService iOrderLogService;

    @RabbitListener(queues = "${mq.order.real.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload String orderNo, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag, Channel channel) {
        try {
            Order order = iOrderService.getOrderByOrderNo(orderNo);
            if (order != null) {
                // 0待支付
                if (order.getStatus().equals(0)) {
                    // 订单主表修改订单状态 3已失效
                    order.setStatus(3);
                    iOrderService.updateOrder(order);
                    // 订单日志记录失效时间
                    OrderLog orderLog = new OrderLog();
                    orderLog.setOrderNo(orderNo);
                    iOrderLogService.insertOrderLog(orderLog);
                }
            }
            channel.basicAck(deliverTag, true);
            log.info("realQueue success, orderNo:{} deliverTag:{}",orderNo,deliverTag);
        } catch (Exception ex) {
            log.error("realQueue error, orderNo:{}",orderNo,ex.fillInStackTrace());
        }
    }
}
