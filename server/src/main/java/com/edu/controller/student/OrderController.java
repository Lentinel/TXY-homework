package com.edu.controller.student;

import com.edu.dto.UserOrderQueryDTO;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/student")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    private Result<PageResult> query(UserOrderQueryDTO userOrderQueryDTO)
    {

        PageResult pageResult= orderService.query(userOrderQueryDTO);
        return Result.success(pageResult);

    }

}
