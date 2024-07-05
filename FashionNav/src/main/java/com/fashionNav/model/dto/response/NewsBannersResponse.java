package com.fashionNav.model.dto.response;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBannersResponse {

    private String title;
    private LocalDateTime createdDate;
}
