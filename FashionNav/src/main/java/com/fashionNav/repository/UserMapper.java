package com.fashionNav.repository;


import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    static void deleteUser(User user) {
    }

    /**
     * 주어진 이메일에 해당하는 사용자를 조회합니다.
     *
     * @param email 사용자의 이메일
     * @return 사용자의 Optional 객체
     */
    @Select("SELECT * FROM USER WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    /**
     * 모든 사용자를 조회합니다.
     *
     * @return 사용자 목록
     */
    @Select("SELECT * FROM USER")
    List<User> findAll();

    /**
     * 주어진 사용자 ID에 해당하는 사용자를 조회합니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자 객체
     */
    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    Optional<User> findById(int userId);

    /**
     * 새로운 사용자를 USER 테이블에 삽입합니다.
     *
     * @param user 사용자 객체
     */
    @Insert("INSERT INTO USER (password, name, email, role, created_at, updated_at) " +
            "VALUES (#{password}, #{name}, #{email}, #{role}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(User user);

    /**
     * 기존 사용자의 정보를 업데이트합니다.
     *
     * @param user 사용자 객체
     */
    @Update("UPDATE USER SET password = #{password}, name = #{name}, email = #{email}, role = #{role}, updated_at = #{updatedAt} " +
            "WHERE user_id = #{userId}")
    void update(User user);

    /**
     * 주어진 사용자 ID에 해당하는 사용자를 삭제합니다.
     *
     * @param userId 사용자의 ID
     */
    @Delete("DELETE FROM USER WHERE user_id = #{userId}")
    void deleteUser(int userId);

    @Select("SELECT name FROM USER WHERE user_id = #{userId}")
    String findUserNameById(int userId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(int userId);




}
