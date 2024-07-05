package com.fashionNav.repository;

import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * UserMapper 인터페이스는 사용자 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 사용자 데이터를 조회, 삽입, 업데이트 및 삭제하는 메서드를 제공합니다.
 */
@Mapper
public interface UserMapper {

    @Select("SELECT email FROM USER WHERE name = #{name} AND phoneNumber = #{phoneNumber}")
    Optional<String> findEmailByNameAndPhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber);

    @Select("SELECT * FROM USER WHERE email = #{email} AND name = #{name}")
    Optional<User> findUserByEmailAndName(@Param("email") String email, @Param("name") String name);

    @Update("UPDATE USER SET password = #{password}, updated_at = NOW() WHERE email = #{email}")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    /**
     * 주어진 이메일에 해당하는 사용자를 조회합니다.
     *
     * @param email 사용자의 이메일
     * @return 사용자의 Optional 객체
     */
    @Select("SELECT * FROM USER WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);

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
    Optional<User> findById(@Param("userId") Long userId);

    /**
     * 새로운 사용자를 USER 테이블에 삽입합니다.
     *
     * @param user 사용자 객체
     */
    @Insert("INSERT INTO USER (password, name, email, gender, phoneNumber, birthdate, role, created_at, updated_at) " +
            "VALUES (#{password}, #{name}, #{email}, #{gender}, #{phoneNumber}, #{birthdate}, #{role}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(User user);

    /**
     * 기존 사용자의 정보를 업데이트합니다.
     *
     * @param user 사용자 객체
     */
    @Update("UPDATE USER SET password = #{password}, name = #{name}, email = #{email}, gender = #{gender}, phoneNumber = #{phoneNumber}, birthdate = #{birthdate}, role = #{role}, updated_at = NOW() " +
            "WHERE user_id = #{userId}")
    void update(User user);

    /**
     * 주어진 사용자 ID에 해당하는 사용자를 삭제합니다.
     *
     * @param userId 사용자의 ID
     */
    @Delete("DELETE FROM USER WHERE user_id = #{userId}")
    void deleteUser(@Param("userId") Long userId);

    @Select("SELECT name FROM USER WHERE user_id = #{userId}")
    String findUserNameById(@Param("userId") Long userId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(@Param("userId") Long userId);
}
