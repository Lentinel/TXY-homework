package com.edu.controller.admin;

import com.edu.dto.UserAdminUpdateDTO;
import com.edu.dto.UserPageQueryDTO;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.UserService;
import com.edu.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@Slf4j
public class AdminUserController {
    @Autowired
    private UserService userService;
    //功能已完成测试
    /*

     */
    @GetMapping("/page")
    public Result<PageResult> getUsers(@RequestBody UserPageQueryDTO userPageQueryDTO)
    {
        log.info("用户查询{}",userPageQueryDTO);

        PageResult pageResult= userService.getUsers(userPageQueryDTO);
        return Result.success(pageResult);
    }
    //现实用户详细信息（管理员端）
    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable("id")long id)
    {
        log.info("用户具体信息{}",id);
        UserVO user = userService.getUser(id);
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<String> adminUpdate(@RequestBody UserAdminUpdateDTO userUpdateDTO,
                                      @PathVariable("id")long id)
    {
        userUpdateDTO.setId(id);
        log.info("管理员用户更新操作{}",userUpdateDTO);
        userService.adminUpdate(userUpdateDTO);
        return Result.success();
    }
    @PostMapping("/{id}/status/{status}")
    public Result<String> startOrStop(@PathVariable("status")Integer status,
                                      @PathVariable("id")long id)
    {
        log.info("启用禁用用户{},{}",id,status);
        userService.startOrStop(status,id);
        return Result.success();
    }
    @PostMapping("/{id}/reset_pdw")
    public Result<String> resetPassword(@PathVariable("id")long id)
    {
        log.info("重置密码{}",id);
        userService.resetPassword(id);
        return Result.success();
    }

}
