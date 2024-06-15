package com.fashionNav.service;

import com.fashionNav.model.dto.request.CreateSurveyRequest;
import com.fashionNav.model.dto.request.OptionRequest;
import com.fashionNav.model.dto.request.QuestionRequest;
import com.fashionNav.model.dto.request.SubmitResponseRequest;
import com.fashionNav.model.entity.Survey;
import com.fashionNav.model.entity.SurveyOption;
import com.fashionNav.model.entity.SurveyQuestion;
import com.fashionNav.model.entity.UserSurveyResponse;
import com.fashionNav.repository.SurveyMapper;
import com.fashionNav.repository.SurveyOptionMapper;
import com.fashionNav.repository.SurveyQuestionMapper;
import com.fashionNav.repository.UserSurveyResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyMapper surveyMapper;
    private final SurveyQuestionMapper surveyQuestionMapper;
    private final SurveyOptionMapper surveyOptionMapper;
    private final UserSurveyResponseMapper userSurveyResponseMapper;

    @Transactional
    public void createSurvey(CreateSurveyRequest request) {
        Survey survey = new Survey();
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        surveyMapper.insertSurvey(survey);

        for (QuestionRequest questionRequest : request.getQuestions()) {
            SurveyQuestion question = new SurveyQuestion();
            question.setSurveyId(survey.getSurveyId());
            question.setQuestionText(questionRequest.getQuestionText());
            surveyQuestionMapper.insertQuestion(question);

            for (OptionRequest optionRequest : questionRequest.getOptions()) {
                SurveyOption option = new SurveyOption();
                option.setQuestionId(question.getQuestionId());
                option.setOptionText(optionRequest.getOptionText());
                surveyOptionMapper.insertOption(option);
            }
        }
    }

    @Transactional
    public void submitResponse(int userId, SubmitResponseRequest request) {
        UserSurveyResponse response = new UserSurveyResponse();
        response.setUserId(userId);
        response.setQuestionId(request.getQuestionId());
        response.setOptionId(request.getOptionId());
        userSurveyResponseMapper.insertResponse(response);
    }

    public List<SurveyQuestion> getSurveyQuestions(int surveyId) {
        return surveyQuestionMapper.findQuestionsBySurveyId(surveyId);
    }

    public List<SurveyOption> getQuestionOptions(int questionId) {
        return surveyOptionMapper.findOptionsByQuestionId(questionId);
    }

    public List<UserSurveyResponse> getUserSurveyResponses(int userId) {
        return userSurveyResponseMapper.findByUserId(userId);
    }
}