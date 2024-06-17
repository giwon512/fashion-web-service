package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemImageDetail {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private String itemBrand;
    private String itemStyle;
    private int imageId;
    private String imageUrl;
    private String imageAltText;
    private String imageCaption;
    private boolean isMain;

}
