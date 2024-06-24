package com.fashionNav.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {
    private Long userId;
    private String gender;
    private String ageGroup;
    private List<Long> styleIds;
    private List<Long> brandIds;
}