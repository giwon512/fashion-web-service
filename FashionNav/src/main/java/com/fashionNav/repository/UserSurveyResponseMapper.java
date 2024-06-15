package com.fashionNav.repository;

import com.fashionNav.model.entity.UserSurveyResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserSurveyResponseMapper {
    @Insert("INSERT INTO USER_SURVEY_RESPONSE(user_id, question_id, option_id, response_date) VALUES (#{userId}, #{questionId}, #{optionId}, #{responseDate})")
    void insertResponse(UserSurveyResponse response);

    @Select("SELECT * FROM USER_SURVEY_RESPONSE WHERE user_id = #{userId}")
    List<UserSurveyResponse> findByUserId(int userId);
}