package com.edu.service.impl;

import com.edu.Exception.LoginException;
import com.edu.Exception.PasswordErrorException;
import com.edu.Exception.RegisterErrorException;
import com.edu.constant.MessageConstant;
import com.edu.dto.*;
import com.edu.entity.User;
import com.edu.mapper.UserMapper;
import com.edu.result.PageResult;
import com.edu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(UserLoginDTO userLoginDTO)
    {
        String password= userLoginDTO.getPassword();
        String userName = userLoginDTO.getUserName();
        User user = userMapper.getByPhone(userName);
        if(user==null)
        {
            throw new LoginException(MessageConstant.USER_NOT_FOUND);
        }
        if(password!=user.getPasswordHash())
        {
            throw new LoginException(MessageConstant.PASSWORD_NOT_FIT);
        }
        user.setLastLoginAt(LocalDateTime.now());
        userMapper.login(user);
        return user;
    }

    public User register(UserRegisterDTO userRegisterDTO)
    {

        String password=userRegisterDTO.getPasswordHash();
        String confirmPassword=userRegisterDTO.getComfirmPasswordHash();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        confirmPassword=DigestUtils.md5DigestAsHex(confirmPassword.getBytes());
        if(password.equals(confirmPassword))
        {
            if(userRegisterDTO.getPhone()!= null &&userRegisterDTO.getUsername()!=null) {
                {
                    User user=userMapper.getByPhone(userRegisterDTO.getPhone());
                    if(user == null) {
                        User user1=new User();
                        BeanUtils.copyProperties(userRegisterDTO,user1);
                        user1.setCreateTime(LocalDateTime.now());
                        user1.setUpdateTime(LocalDateTime.now());
                        user1.setLastLoginAt(LocalDateTime.now());
                        userMapper.CreateUser(user1);
                        user1 = userMapper.getByPhone(userRegisterDTO.getPhone());
                        return user1;
                    }
                    else
                    {
                        throw new RegisterErrorException(MessageConstant.PHONE_REGISTERED);
                    }
                }
            }
            else
            {
                throw new RegisterErrorException(MessageConstant.NESSESARY_RIGESTER_INFO_NOT_FOUND);
            }
        }
        else
        {
            throw new PasswordErrorException(MessageConstant.CONFIRMED_PASSWORD_NOT_EQUAL);
        }
    }

    @Override
    public PageResult getUsers(UserPageQueryDTO userPageQueryDTO) {
        return null;
    }

    @Override
    public User getUser(UserQueryDTO userQueryDTO) {
        return null;
    }

    @Override
    public void adminUpdate(UserAdminUpdateDTO userAdminUpdateDTO) {

    }

}
