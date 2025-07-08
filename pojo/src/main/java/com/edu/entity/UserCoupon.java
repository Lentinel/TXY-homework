package com.edu.entity;

import java.util.Date;

public class UserCoupon {
    private Long userId;
    private Long couponId;
    private Long orderId;
    private Integer status;
    private Date getTime;
    private Date useTime;

    // 关联用户
    private User user;

    // 关联优惠券
    private Coupon coupon;

    // 关联订单
    private Order order;
    private long id;
}
