package com.ryl.mq.demo.controller;

import com.ryl.mq.demo.entity.Order;
import com.ryl.mq.demo.publisher.OrderPublisher;
import com.ryl.mq.demo.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ryl
 * @description
 * @date 2020-12-02 15:14:18
 */
@RestController
@AllArgsConstructor
public class TestController {

    private final OrderPublisher orderPublisher;
    private final IOrderService iOrderService;

    @PostMapping("/send")
    public String send() {
        Order order1 = new Order();
        Order order2 = new Order();
        order1.setOrderNo("A2012171147000001");
        order1.setUserId(1L);
        order2.setOrderNo("A2012171147000002");
        order2.setUserId(2L);
        order2.setStatus(1);

        iOrderService.insertOrder(order1);
        iOrderService.insertOrder(order2);

        orderPublisher.sendMsg(order1.getOrderNo());
        orderPublisher.sendMsg(order2.getOrderNo());

        return "ok";
    }


}
