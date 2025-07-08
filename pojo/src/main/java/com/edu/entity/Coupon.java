package com.edu.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Coupon {
    private long id;
    private String couponCode;
    private Integer couponType;
    private BigDecimal discountValue;
    private BigDecimal minAmount;
    private Date validStart;
    private Date validEnd;
    private Integer totalQuantity;
    private Integer usedQuantity;
    private Integer status;

    // 关联用户优惠券
    private List<UserCoupon> userCoupons;
}
