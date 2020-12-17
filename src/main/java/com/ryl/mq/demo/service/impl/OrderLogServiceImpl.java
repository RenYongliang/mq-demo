package com.ryl.mq.demo.service.impl;

import com.ryl.mq.demo.entity.OrderLog;
import com.ryl.mq.demo.service.IOrderLogService;
import com.ryl.mq.demo.mapper.OrderLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ryl
 * @description
 * @date 2020-12-14 10:07:49
 */
@Service
@AllArgsConstructor
public class OrderLogServiceImpl implements IOrderLogService {

    private final OrderLogMapper orderLogMapper;


    @Override
    public boolean insertOrderLog(OrderLog orderLog) {
        return orderLogMapper.insertSelective(orderLog);
    }
}
