package com.edu.mapper;

import com.edu.annotation.AutoFill;
import com.edu.dto.UserPageQueryDTO;
import com.edu.entity.User;
import com.edu.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface UserMapper {
    @AutoFill(value= OperationType.INSERT)//@Insert("insert into 'user'(password_hash,email,phone,full_name,gender,avatarUrl,birthday,create_at,updated_at,last_login_at)")
    public void CreateUser(User user);

    @Select("select * from user where phone = #{phone}")
    User getByPhone(String phone);

    @Update("update last_login_at #{lastLoginAt} from 'user' where id=#{id}")
    public void login(User user) ;

    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);
    @Select("select * from user where id=#{id}")
    User getUser(long id);

    void update(User user);
}
