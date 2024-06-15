package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.common.success.SuccessCode;
import com.fashionNav.model.dto.request.CreateSurveyRequest;
import com.fashionNav.model.dto.request.SubmitResponseRequest;
import com.fashionNav.model.entity.SurveyOption;
import com.fashionNav.model.entity.SurveyQuestion;
import com.fashionNav.model.entity.User;
import com.fashionNav.model.entity.UserSurveyResponse;
import com.fashionNav.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Survey API", description = "설문조사 관련 API")
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "설문조사 생성", description = "새로운 설문조사를 생성합니다.")
    @PostMapping
    public Api<Void> createSurvey(@RequestBody CreateSurveyRequest request) {
        surveyService.createSurvey(request);
        return Api.OK(SuccessCode.OK);
    }

    @Operation(summary = "설문조사 응답 제출", description = "사용자가 설문조사에 응답을 제출합니다.")
    @PostMapping("/responses")
    public Api<Void> submitResponse(Authentication authentication, @RequestBody SubmitResponseRequest request) {
        int userId = ((User) authentication.getPrincipal()).getUserId();
        surveyService.submitResponse(userId, request);
        return Api.OK(SuccessCode.OK);
    }

    @Operation(summary = "설문조사 질문 조회", description = "특정 설문조사의 질문을 조회합니다.")
    @GetMapping("/{surveyId}/questions")
    public Api<List<SurveyQuestion>> getSurveyQuestions(@PathVariable int surveyId) {
        return Api.OK(surveyService.getSurveyQuestions(surveyId));
    }

    @Operation(summary = "설문조사 옵션 조회", description = "특정 질문의 옵션을 조회합니다.")
    @GetMapping("/questions/{questionId}/options")
    public Api<List<SurveyOption>> getQuestionOptions(@PathVariable int questionId) {
        return Api.OK(surveyService.getQuestionOptions(questionId));
    }

    @Operation(summary = "사용자 설문조사 응답 조회", description = "특정 사용자의 설문조사 응답 내역을 조회합니다.")
    @GetMapping("/responses/user/{userId}")
    public Api<List<UserSurveyResponse>> getUserSurveyResponses(@PathVariable int userId) {
        return Api.OK(surveyService.getUserSurveyResponses(userId));
    }
}