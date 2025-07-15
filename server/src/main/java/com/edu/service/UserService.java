package com.edu.service;

import com.edu.dto.*;
import com.edu.entity.User;
import com.edu.result.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User login(UserLoginDTO userLoginDTO);
    User register(UserRegisterDTO userRegisterDTO);


    PageResult getUsers(UserPageQueryDTO userPageQueryDTO);

    User getUser(long id);

    void adminUpdate(UserAdminUpdateDTO userAdminUpdateDTO);

    void startOrStop(Integer status, long id);

    void resetPassword(long id);
}
