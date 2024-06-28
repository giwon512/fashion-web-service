package com.fashionNav.service;

import com.fashionNav.model.entity.UserSurvey;
import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.Brand;
import com.fashionNav.repository.UserSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * UserService 클래스는 사용자 설문, 등록, 업데이트, 삭제 등의 기능을 제공하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class UserSurveyService {
    private final UserSurveyMapper userSurveyMapper;

    public List<UserSurvey> getAllUserSurveys() {
        return userSurveyMapper.findAllUserSurveys();
    }

    public boolean userHasSurvey(Long userId) {
        List<UserSurvey> surveys = userSurveyMapper.getUserSurveysByUserId(userId);
        return !surveys.isEmpty();
    }

    @Transactional
    public void createUserSurvey(UserSurvey userSurvey, List<Long> styleIds, List<Long> brandIds) {

        userSurveyMapper.insertUserSurvey(userSurvey);
        Long surveyId = userSurvey.getSurveyId();
        userSurveyMapper.insertUserSurveyStyles(surveyId, styleIds);
        userSurveyMapper.insertUserSurveyBrands(surveyId, brandIds);
    }

    public UserSurvey getUserSurveyById(Long surveyId) {
        return userSurveyMapper.getUserSurveyById(surveyId);
    }

    @Transactional
    public void updateUserSurvey(UserSurvey userSurvey, List<Long> styleIds, List<Long> brandIds) {
        userSurveyMapper.updateUserSurvey(userSurvey);
        Long surveyId = userSurvey.getSurveyId();
        userSurveyMapper.deleteUserSurveyStyles(surveyId);
        userSurveyMapper.deleteUserSurveyBrands(surveyId);
        userSurveyMapper.insertUserSurveyStyles(surveyId, styleIds);
        userSurveyMapper.insertUserSurveyBrands(surveyId, brandIds);
    }

    public void deleteUserSurvey(Long surveyId) {
        userSurveyMapper.deleteUserSurveyStyles(surveyId);
        userSurveyMapper.deleteUserSurveyBrands(surveyId);
        userSurveyMapper.deleteUserSurvey(surveyId);
    }

    public List<UserSurvey> getUserSurveysByUserId(Long userId) {
        return userSurveyMapper.getUserSurveysByUserId(userId);
    }

    public List<Style> findStylesBySurveyId(Long surveyId) {
        return userSurveyMapper.findStylesBySurveyId(surveyId);
    }

    public List<Brand> findBrandsBySurveyId(Long surveyId) {
        return userSurveyMapper.findBrandsBySurveyId(surveyId);
    }
}
