package com.fashionNav.repository;

import com.fashionNav.model.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM ADMIN WHERE admin_id = #{adminId}")
    Admin findById(String adminId);

    @Insert("INSERT INTO ADMIN (admin_id, admin_pw) VALUES (#{adminId}, #{adminPw})")
    void insert(Admin admin);

    @Update("UPDATE ADMIN SET admin_pw = #{adminPw} WHERE admin_id = #{adminId}")
    void update(Admin admin);

    @Delete("DELETE FROM ADMIN WHERE admin_id = #{adminId}")
    void delete(String adminId);
}