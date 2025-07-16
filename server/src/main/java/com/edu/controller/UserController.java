package com.edu.controller;

import com.edu.constant.JwtClaimsConstant;
import com.edu.dto.UserLoginDTO;
import com.edu.dto.UserRegisterDTO;
import com.edu.entity.User;
import com.edu.properties.JwtProperties;
import com.edu.result.Result;
import com.edu.service.UserService;
import com.edu.vo.UserLoginVO;
import com.edu.vo.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/register")
    public Result<UserRegisterVO> register(@RequestBody UserRegisterDTO userRegisterDTO)
    {
        log.info("注册:{}",userRegisterDTO);
        User user=userService.register(userRegisterDTO);

        //注册成功后生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserRegisterVO userRegisterVO = UserRegisterVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();


        return Result.success(userRegisterVO);
    }
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        log.info("登录");
        User user=userService.login(userLoginDTO);

        //登录成功后生成jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .token(token)
                .role(user.getRole())
                .build();
        return Result.success(userLoginVO);
    }

}
