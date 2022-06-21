package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Insert("INSERT INTO \"user\"  ( username, password, role )  VALUES  ( #{username}, #{password}, #{role} )")
    int Insert(@Param("username") String username,@Param("password") String password,@Param("role") int role);

    @Select("select * from \"user\" where username = #{username}")
    User selectByName(@Param("username") String username);

}
