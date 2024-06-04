package com.fashionNav.repository;


import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER")
    List<User> findAll();

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findById(int userId);

    @Insert("INSERT INTO USER (password, name, email, created_at, updated_at) " +
            "VALUES (#{password}, #{name}, #{email}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(User user);

    @Update("UPDATE USER SET password = #{password}, name = #{name}, email = #{email}, updated_at = #{updatedAt} " +
            "WHERE user_id = #{userId}")
    void update(User user);

    @Delete("DELETE FROM USER WHERE user_id = #{userId}")
    void delete(int userId);
}