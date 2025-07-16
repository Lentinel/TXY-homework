package com.edu.service.impl;

import com.edu.Exception.LoginException;
import com.edu.Exception.PasswordErrorException;
import com.edu.Exception.RegisterErrorException;
import com.edu.constant.MessageConstant;
import com.edu.constant.UserTypeConstant;
import com.edu.dto.*;
import com.edu.entity.User;
import com.edu.mapper.UserMapper;
import com.edu.result.PageResult;
import com.edu.service.UserService;
import com.edu.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(UserLoginDTO userLoginDTO)
    {
        String password= userLoginDTO.getPassword();
        String userName = userLoginDTO.getUsername();


        User user=new User();
        user = userMapper.getByPhone(userName);
        if(user==null)
        {
            user=userMapper.getByUsername(userName);
            if(user==null) {
                user=userMapper.getByEmail(userName);
                if(user==null) {
                    throw new LoginException(MessageConstant.USER_NOT_FOUND);
                }
            }
        }
        if(user.getUserStatus()!=1)
        {
            throw new RuntimeException("账户状态异常");

        }
        {
            if (!Objects.equals(password, user.getPasswordHash())) {
                throw new LoginException(MessageConstant.PASSWORD_NOT_FIT);
            }
            user.setLastLoginAt(LocalDateTime.now());
            userMapper.login(user);
        }
        return user;
    }

    public User register(UserRegisterDTO userRegisterDTO)
    {

        String password=userRegisterDTO.getPasswordHash();
        String confirmPassword=userRegisterDTO.getComfirmPassword();

        password= DigestUtils.md5DigestAsHex(password.getBytes());
        confirmPassword=DigestUtils.md5DigestAsHex(confirmPassword.getBytes());
        User existingUser = userMapper.getByUsername(userRegisterDTO.getUsername());
        if (existingUser != null) {
            throw new RegisterErrorException("用户名已存在");
        }
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

            PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
            //下一条sql进行分页，自动加入limit关键字分页
            Page<UserVO> page = userMapper.pageQuery(userPageQueryDTO);
            return new PageResult(page.getTotal(), page.getResult());

    }


    public UserVO getUser(long id) {
        UserVO user=userMapper.getUser(id);
        //user.setPasswordHash("******");
        return user;
    }

    @Override
    public void adminUpdate(UserAdminUpdateDTO userAdminUpdateDTO) {
        User user=new User();
        BeanUtils.copyProperties(userAdminUpdateDTO,user);
        UserVO c=userMapper.getUser(userAdminUpdateDTO.getId());
        if(user.getRole()!=null&&(user.getRole()!=1&&user.getRole()!=0)||c.getRole()==2)
        {
            throw new RuntimeException("用户类型操作错误");
        }
        else {
            user.setUpdateTime(LocalDateTime.now());

            userMapper.update(user);
        }
    }

    @Override
    public void startOrStop(Integer status, long id) {
        User user=new User();
        user.setUserStatus(status);
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void resetPassword(long id) {
        User user=new User();
        user.setId(id);
        user.setPasswordHash(UserTypeConstant.PDW);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

}
