package com.fashionNav.service;

import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.exception.ApiException;
import com.fashionNav.model.dto.response.SavePageListResponse;
import com.fashionNav.model.entity.User;
import com.fashionNav.model.entity.UserSavedPage;
import com.fashionNav.repository.UserSavePageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSavePageService {

	private final UserSavePageMapper userSavePageMapper;

	// 사용자가 저장한 페이지
	public void archiveSave(User principal, @Valid Long newsId) {
		if (principal == null) {
			throw new ApiException(ErrorCode.BAD_REQUEST, "로그인을 하세요");
		}
		userSavePageMapper.insertUserIdPageId(principal.getUserId(), newsId);
	}

	// 저장한 페이지 보여주는 기능
	public List<SavePageListResponse> archive(User principal) {
		Long userId = principal.getUserId();
		List<UserSavedPage> userSavedPages = userSavePageMapper.findByUserId(userId);

		List<Long> newsIds = userSavedPages.stream()
				.map(UserSavedPage::getNewsId)
				.collect(Collectors.toList());

		if (newsIds.isEmpty()) {
			log.debug("No saved news found for user with id: {}", userId);
			return Collections.emptyList(); // 빈 리스트 반환
		}

		log.debug("Fetching saved news for user with id: {} and news ids: {}", userId, newsIds);
		return userSavePageMapper.findByNewsIds(newsIds);
	}

	// 저장한 페이지 삭제
	public void archiveDelete(User principal, Long newsId) {
		Long userId = principal.getUserId();
		userSavePageMapper.deleteUserPageByUserIdAndNewsId(userId, newsId);
	}
}