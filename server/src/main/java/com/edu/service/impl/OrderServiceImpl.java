package com.edu.service.impl;

import com.edu.dto.UserOrderQueryDTO;
import com.edu.entity.Order;
import com.edu.result.PageResult;
import com.edu.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public PageResult query(UserOrderQueryDTO userOrderQueryDTO) {
        return null;
    }

    @Override
    public Order query(long orderId) {
        return null;
    }
}
