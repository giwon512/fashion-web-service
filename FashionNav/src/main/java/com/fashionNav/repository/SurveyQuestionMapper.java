package com.fashionNav.repository;

import com.fashionNav.model.entity.SurveyQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SurveyQuestionMapper {
    @Insert("INSERT INTO SURVEY_QUESTION(survey_id, question_text) VALUES(#{surveyId}, #{questionText})")
    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    void insertQuestion(SurveyQuestion question);

    @Select("SELECT * FROM SURVEY_QUESTION WHERE survey_id = #{surveyId}")
    List<SurveyQuestion> findQuestionsBySurveyId(int surveyId);
}