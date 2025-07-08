package com.edu.service;

import com.edu.dto.UserOrderQueryDTO;
import com.edu.result.PageResult;

public interface OrderService {
    PageResult query(UserOrderQueryDTO userOrderQueryDTO);
}
