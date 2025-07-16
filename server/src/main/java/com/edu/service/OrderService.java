package com.edu.service;

import com.edu.dto.UserOrderQueryDTO;
import com.edu.entity.Order;
import com.edu.result.PageResult;

public interface OrderService {
    PageResult query(UserOrderQueryDTO userOrderQueryDTO);

    Order query(long orderId);
}
