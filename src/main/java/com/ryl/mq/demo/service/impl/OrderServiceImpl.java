package com.ryl.mq.demo.service.impl;

import com.ryl.mq.demo.entity.Order;
import com.ryl.mq.demo.mapper.OrderMapper;
import com.ryl.mq.demo.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ryl
 * @description
 * @date 2020-12-14 10:08:54
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;


    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public boolean insertOrder(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderMapper.updateSelective(order);
    }
}
