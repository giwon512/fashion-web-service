package com.fashionNav.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindEmailRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String phoneNumber;
}
