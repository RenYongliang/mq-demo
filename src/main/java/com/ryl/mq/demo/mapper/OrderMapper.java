package com.ryl.mq.demo.mapper;

import com.ryl.mq.demo.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author ryl
 * @description
 * @date 2020-12-02 15:14:18
 */
public interface OrderMapper {

    /**
     * 根据订单号查询订单
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 新增订单
     * @param order
     * @return
     */
    boolean insertSelective(Order order);

    /**
     * 修改订单状态
     * @param order
     * @return
     */
    boolean updateSelective(Order order);
}
