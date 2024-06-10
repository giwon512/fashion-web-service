package com.fashionNav.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageItemDetail {
    private String itemName;
    private String itemStyle;
    private String imageUrl;
}
