package com.edu.controller.admin;

import com.edu.dto.OrderPageQueryDTO;
import com.edu.entity.Order;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(OrderPageQueryDTO orderPageQueryDTO)
    {
        return null;
    }
    @GetMapping("/{id}/detail")
    public Result<Order> query(@PathVariable("id")long id)
    {
        return null;
    }
    @PostMapping("/{id}/refund")
    public Result refund(@PathVariable("id")long id)
    {
        return null;
    }

}
