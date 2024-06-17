package com.fashionNav.model.dto.request;

import com.fashionNav.model.entity.Images;
import com.fashionNav.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveItemRequest {
    private ItemInfo item;
    private ImagesInfo images;
    private boolean isMain;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {
        private String name;
        private String description;
        private double price;
        private String brand;
        private String style;

        public Item toEntity() {
            return Item.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .brand(brand)
                    .style(style)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImagesInfo {
        private String url;
        private String altText;
        private String caption;

        public Images toEntity() {
            return Images.builder()
                    .url(url)
                    .altText(altText)
                    .caption(caption)
                    .build();
        }
    }
}