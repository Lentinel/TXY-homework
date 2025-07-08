package com.edu.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderNo;
    private Long userId;
    private Long courseId;
    private BigDecimal orderAmount;
    private BigDecimal actualAmount;
    private BigDecimal discountAmount;
    private Integer orderStatus;
    private Integer paymentMethod;
    private Date paymentTime;
    private String refundReason;
    private Date refundTime;

    // 关联用户
    private User user;

    // 关联课程
    private Course course;

    // 关联支付记录
    private List<PaymentRecord> paymentRecords;

    // 关联优惠券
    private UserCoupon userCoupon;

}
