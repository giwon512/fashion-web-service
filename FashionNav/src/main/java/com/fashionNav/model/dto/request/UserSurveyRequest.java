package com.fashionNav.model.dto.request;

import com.fashionNav.model.entity.UserSurvey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSurveyRequest {
    private UserSurvey userSurvey;
    private List<Long> styleIds;
    private List<Long> brandIds;
}
