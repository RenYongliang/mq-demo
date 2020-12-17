package com.ryl.mq.demo.service;

import com.ryl.mq.demo.entity.OrderLog;

/**
 * @author ryl
 * @description
 * @date 2020-12-14 10:07:49
 */
public interface IOrderLogService {

    /**
     * 新增订单失效日志
     * @param orderLog
     * @return
     */
    boolean insertOrderLog(OrderLog orderLog);
}
