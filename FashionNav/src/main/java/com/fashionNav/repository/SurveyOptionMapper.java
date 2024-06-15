package com.fashionNav.repository;

import com.fashionNav.model.entity.SurveyOption;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SurveyOptionMapper {

    @Insert("INSERT INTO SURVEY_OPTION (question_id, option_text) VALUES (#{questionId}, #{optionText})")
    @Options(useGeneratedKeys = true, keyProperty = "optionId")
    void insertOption(SurveyOption option);

    @Select("SELECT * FROM SURVEY_OPTION WHERE question_id = #{questionId}")
    List<SurveyOption> findOptionsByQuestionId(int questionId);
}