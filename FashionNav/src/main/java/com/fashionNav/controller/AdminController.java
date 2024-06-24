package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin API", description = "관리자 전용 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Operation(summary = "관리자 전용 API 예제", description = "ROLE_ADMIN 유저만 접근할 수 있는 API입니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public Api<String> getAdminDashboard() {
        return Api.OK("관리자 대시보드에 접근했습니다.");
    }

}
