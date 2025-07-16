package com.edu.controller.student;

import com.edu.dto.UserOrderQueryDTO;
import com.edu.entity.Order;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/student")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Result<PageResult> pageQuery(UserOrderQueryDTO userOrderQueryDTO)
    {

        PageResult pageResult= orderService.query(userOrderQueryDTO);
        return Result.success(pageResult);

    }
    @GetMapping("/order/{orderId}")
    public Result<Order> query(@PathVariable("orderId")long orderId)
    {
        Order order=orderService.query(orderId);
        return Result.success(order);
    }
    @PostMapping("/course/{courseId}/order")
    public Result<String> startOrder()
    {
        return null;
    }@DeleteMapping("/course/{courseId}/order")
    public Result<String> giveupOrder()
    {
        return null;
    }
    @PostMapping("/course/{courseId}/order/confirm")
    public Result<String> polishOrder()
    {
        return null;
    }
}
