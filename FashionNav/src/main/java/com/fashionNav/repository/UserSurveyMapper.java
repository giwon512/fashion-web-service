package com.fashionNav.repository;

import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.Brand;
import com.fashionNav.model.entity.UserSurvey;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserSurveyMapper {


    @Select("SELECT * FROM UserSurvey")
    List<UserSurvey> findAllUserSurveys();

    @Insert("INSERT INTO UserSurvey (user_id, gender, age_group) VALUES (#{userId}, #{gender}, #{ageGroup})")
    @Options(useGeneratedKeys = true, keyProperty = "surveyId")
    void insertUserSurvey(UserSurvey userSurvey);

    @Insert({
            "<script>",
            "INSERT INTO UserSurveyStyle (survey_id, style_id) VALUES ",
            "<foreach collection='styleIds' item='styleId' separator=','>",
            "(#{surveyId}, #{styleId})",
            "</foreach>",
            "</script>"
    })
    void insertUserSurveyStyles(@Param("surveyId") Long surveyId, @Param("styleIds") List<Long> styleIds);

    @Insert({
            "<script>",
            "INSERT INTO UserSurveyBrand (survey_id, brand_id) VALUES ",
            "<foreach collection='brandIds' item='brandId' separator=','>",
            "(#{surveyId}, #{brandId})",
            "</foreach>",
            "</script>"
    })
    void insertUserSurveyBrands(@Param("surveyId") Long surveyId, @Param("brandIds") List<Long> brandIds);

    @Select("SELECT * FROM UserSurvey WHERE survey_id = #{surveyId}")
    @Results({
            @Result(property = "surveyId", column = "survey_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "ageGroup", column = "age_group"),
            @Result(property = "styles", column = "survey_id",
                    many = @Many(select = "findStylesBySurveyId")),
            @Result(property = "brands", column = "survey_id",
                    many = @Many(select = "findBrandsBySurveyId"))
    })
    UserSurvey getUserSurveyById(Long surveyId);

    @Select("SELECT s.style_id, s.name FROM Style s JOIN UserSurveyStyle uss ON s.style_id = uss.style_id WHERE uss.survey_id = #{surveyId}")
    List<Style> findStylesBySurveyId(Long surveyId);

    @Select("SELECT b.brand_id, b.name FROM Brand b JOIN UserSurveyBrand usb ON b.brand_id = usb.brand_id WHERE usb.survey_id = #{surveyId}")
    List<Brand> findBrandsBySurveyId(Long surveyId);

    @Update("UPDATE UserSurvey SET gender = #{gender}, age_group = #{ageGroup} WHERE survey_id = #{surveyId}")
    void updateUserSurvey(UserSurvey userSurvey);

    @Delete("DELETE FROM UserSurvey WHERE survey_id = #{surveyId}")
    void deleteUserSurvey(Long surveyId);

    @Select("SELECT * FROM UserSurvey WHERE user_id = #{userId}")
    List<UserSurvey> getUserSurveysByUserId(Long userId);

    @Delete("DELETE FROM UserSurveyStyle WHERE survey_id = #{surveyId}")
    void deleteUserSurveyStyles(Long surveyId);

    @Delete("DELETE FROM UserSurveyBrand WHERE survey_id = #{surveyId}")
    void deleteUserSurveyBrands(Long surveyId);
}
