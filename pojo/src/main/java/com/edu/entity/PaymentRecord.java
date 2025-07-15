package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
