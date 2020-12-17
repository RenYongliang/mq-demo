package com.ryl.mq.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ryl
 * @description
 * @date 2020-12-17 09:54:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLog {

    /**
     * id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 失效时间
     */
    private Date createTime;
}
