package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.common.error.UserErrorCode;
import com.fashionNav.common.success.SuccessCode;
import com.fashionNav.exception.ApiException;
import com.fashionNav.model.dto.request.UserSurveyRequest;
import com.fashionNav.model.dto.response.UserSurveyResponse;
import com.fashionNav.model.entity.Brand;
import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.User;
import com.fashionNav.model.entity.UserSurvey;
import com.fashionNav.service.UserSurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class UserSurveyController {
    private final UserSurveyService userSurveyService;

    @GetMapping
    public Api<List<UserSurveyResponse>> getAllUserSurveys() {
        List<UserSurvey> userSurveys = userSurveyService.getAllUserSurveys();
        List<UserSurveyResponse> responses = userSurveys.stream()
                .map(survey -> new UserSurveyResponse(survey,
                        userSurveyService.findStylesBySurveyId(survey.getSurveyId()),
                        userSurveyService.findBrandsBySurveyId(survey.getSurveyId())))
                .collect(Collectors.toList());
        return Api.OK(responses, SuccessCode.OK);
    }

    @PostMapping
    public Api<Void> createUserSurvey(@RequestBody UserSurveyRequest request, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        // Check if the user already has a survey
        if (userSurveyService.userHasSurvey(userId)) {
            throw new ApiException(UserErrorCode.USER_NOT_ALLOWED);

        }

        userSurveyService.createUserSurvey(request.getUserSurvey(), request.getStyleIds(), request.getBrandIds());
        return Api.OK(SuccessCode.OK);
    }




    @GetMapping("/{surveyId}")
    public Api<UserSurveyResponse> getUserSurveyById(@PathVariable Long surveyId) {

        UserSurvey userSurvey = userSurveyService.getUserSurveyById(surveyId);
        List<Style> styles = userSurveyService.findStylesBySurveyId(surveyId);
        List<Brand> brands = userSurveyService.findBrandsBySurveyId(surveyId);
        UserSurveyResponse response = new UserSurveyResponse(userSurvey, styles, brands);


        log.info("{}",response);
        return Api.OK(response);
    }

    @PutMapping("/{surveyId}")
    public Api<Void> updateUserSurvey(@PathVariable Long surveyId, @RequestBody UserSurveyRequest request) {
        UserSurvey userSurvey = request.getUserSurvey();
        userSurvey.setSurveyId(surveyId); // Set the surveyId to the correct value
        userSurveyService.updateUserSurvey(userSurvey, request.getStyleIds(), request.getBrandIds());
        return Api.OK(SuccessCode.OK);
    }

    @DeleteMapping("/{surveyId}")
    public Api<Void> deleteUserSurvey(@PathVariable Long surveyId) {
        userSurveyService.deleteUserSurvey(surveyId);
        return Api.OK(SuccessCode.OK);
    }

    @GetMapping("/user")
    public Api<List<UserSurveyResponse>> getUserSurveysByUserId(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        List<UserSurvey> userSurveys = userSurveyService.getUserSurveysByUserId(userId);
        List<UserSurveyResponse> responses = userSurveys.stream()
                .map(survey -> new UserSurveyResponse(survey,
                        userSurveyService.findStylesBySurveyId(survey.getSurveyId()),
                        userSurveyService.findBrandsBySurveyId(survey.getSurveyId())))
                .collect(Collectors.toList());
        log.info("{}",responses);
        return Api.OK(responses);
    }
}
