package com.fashionNav.model.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSavedPage {
	private Long savedPageId;
    private int userId;
    private Long newsId;
    private LocalDateTime savedDate;
}
