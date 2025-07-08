package com.edu.entity;

import java.math.BigDecimal;
import java.util.Date;
public class PaymentRecord {
    private long id;
    private Long orderId;
    private String transactionId;
    private BigDecimal paymentAmount;
    private Integer paymentStatus;
    private Date paymentTime;
    private Integer paymentChannel;
    private String errorMsg;

    // 关联订单
    private Order order;

}
