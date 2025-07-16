package com.edu.mapper;

import com.edu.dto.UserPageQueryDTO;
import com.edu.entity.User;
import com.edu.vo.UserVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface UserMapper {
    //@AutoFill(value= OperationType.INSERT)//@Insert("insert into 'user'(password_hash,email,phone,full_name,gender,avatarUrl,birthday,create_at,updated_at,last_login_at)")
    public void CreateUser(User user);

    @Select("select * from `user` where phone = #{phone}")
    User getByPhone(String phone);

    @Update("update `user` set last_login_at = #{lastLoginAt}  where id=#{id}")
    public void login(User user) ;

    Page<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO);
    @Select("select * from `user` where id=#{id}")
    UserVO getUser(long id);

    void update(User user);
    @Select(" select * from `user` where username = #{username} ")
    User getByUsername(String username);
    @Select(" select * from `user` where email = #{email}  ")
    User getByEmail(String email);
}
