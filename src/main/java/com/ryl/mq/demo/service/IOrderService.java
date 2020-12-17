package com.ryl.mq.demo.service;

import com.ryl.mq.demo.entity.Order;

/**
 * @author ryl
 * @description
 * @date 2020-12-14 10:08:54
 */
public interface IOrderService {

    /**
     * 根据订单号获取订单
     * @param orderNo
     * @return
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 新增订单
     * @param order
     * @return
     */
    boolean insertOrder(Order order);

    /**
     * 更新订单
     * @param order
     * @return
     */
    boolean updateOrder(Order order);
}
