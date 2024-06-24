package com.fashionNav.model.dto.response;

import com.fashionNav.model.entity.Brand;
import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.UserSurvey;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class UserSurveyResponse {
    private UserSurvey userSurvey;
    private List<Style> styles;
    private List<Brand> brands;

}
