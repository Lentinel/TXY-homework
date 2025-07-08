package com.edu.controller.admin;

import com.edu.dto.UserAdminUpdateDTO;
import com.edu.dto.UserPageQueryDTO;
import com.edu.dto.UserQueryDTO;
import com.edu.entity.User;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user")
@Slf4j
public class AdminUserController {
    @Autowired
    private UserService userService;

    /*

     */
    @GetMapping("/page")
    public Result<PageResult> getUsers(UserPageQueryDTO userPageQueryDTO)
    {
        log.info("用户查询{}",userPageQueryDTO);

        PageResult pageResult= userService.getUsers(userPageQueryDTO);
        return Result.success(pageResult);
    }
    //现实用户详细信息（管理员端）
    @GetMapping("/{id}")
    public Result<User> getUser(UserQueryDTO userQueryDTO)
    {
        log.info("用户具体信息{}",userQueryDTO);
        User user = userService.getUser(userQueryDTO);
        return Result.success(user);
    }
    @PutMapping("/{id}")
    public Result<String> adminUpdate(UserAdminUpdateDTO userAdminUpdateDTO)
    {
        log.info("管理员用户更新操作{}",userAdminUpdateDTO);
        userService.adminUpdate(userAdminUpdateDTO);
        return Result.success();
    }

}
