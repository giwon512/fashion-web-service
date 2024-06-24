package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSurvey {
    private Long surveyId;
    private Long userId;
    private String gender;
    private String ageGroup;
    private List<Style> styles;
    private List<Brand> brands;
}
