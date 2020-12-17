package com.ryl.mq.demo.mapper;

import com.ryl.mq.demo.entity.OrderLog;

/**
 * @author ryl
 * @description
 * @date 2020-12-02 15:14:18
 */
public interface OrderLogMapper {

    /**
     * 新增订单失效记录
     * @param orderLog
     * @return
     */
    boolean insertSelective(OrderLog orderLog);
}
