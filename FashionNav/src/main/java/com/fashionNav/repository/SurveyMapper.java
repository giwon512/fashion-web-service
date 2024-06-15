package com.fashionNav.repository;


import com.fashionNav.model.entity.Survey;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SurveyMapper {

    @Select("SELECT * FROM SURVEY")
    List<Survey> findAll();

    @Select("SELECT * FROM SURVEY WHERE survey_id = #{surveyId}")
    Survey findById(int surveyId);

    @Insert("INSERT INTO SURVEY (title, description, created_at) " +
            "VALUES (#{title}, #{description}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "surveyId")
    void insertSurvey(Survey survey);

    @Update("UPDATE SURVEY SET title = #{title}, description = #{description} " +
            "WHERE survey_id = #{surveyId}")
    void update(Survey survey);

    @Delete("DELETE FROM SURVEY WHERE survey_id = #{surveyId}")
    void delete(int surveyId);
}