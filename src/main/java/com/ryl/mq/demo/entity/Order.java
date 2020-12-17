package com.ryl.mq.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ryl
 * @description
 * @date 2020-12-17 09:49:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    /**
     * id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单状态 0待付款 1已支付 2已取消 3已失效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
