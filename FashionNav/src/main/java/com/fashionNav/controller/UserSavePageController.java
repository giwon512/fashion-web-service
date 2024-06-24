package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.model.dto.response.SavePageListResponse;
import com.fashionNav.model.entity.User;
import com.fashionNav.service.UserSavePageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserSavePage", description = "저장한 페이지")
@Slf4j
@RestController
@RequestMapping("/api/page")
@RequiredArgsConstructor
public class UserSavePageController {
	
	private final UserSavePageService savePageService;
	
	// 사용자가 저장한 페이지
	@PostMapping("/archivesave/{newsId}")
    public Api<String> archiveSave( @Valid @PathVariable("newsId") Long newsId, Authentication authentication ) {
    	
		savePageService.archiveSave( (User)authentication.getPrincipal(), newsId );
    	
    	return Api.OK( "페이지 저장 완료" ); 
    }
	
	// 저장한 페이지 보여주기
	@GetMapping("/archive")
	public Api<List<SavePageListResponse>> archive(Authentication authentication) {
	    List<SavePageListResponse> page = savePageService.archive((User) authentication.getPrincipal());
	    return Api.OK(page);
	}
 	
	// 저장한 페이지 삭제하기
	@DeleteMapping("/archive/{newsId}")
	public Api<String> archiveDelete( @Valid @PathVariable("newsId") Long newsId ,Authentication authentication ) {
		savePageService.archiveDelete( (User) authentication.getPrincipal(), newsId );
		
		return Api.OK("저장한 페이지 삭제 성공");
	}
}
