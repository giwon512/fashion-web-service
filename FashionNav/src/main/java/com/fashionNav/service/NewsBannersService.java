package com.fashionNav.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.exception.ApiException;
import com.fashionNav.model.dto.request.NewsBannerRequest;
import com.fashionNav.model.dto.response.NewsBannersResponse;
import com.fashionNav.repository.NewsBannersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsBannersService {

	private final NewsBannersMapper bannersMapper;

	// 가공된 뉴스 배너에 넣는 기능
	public void insertProcessedBanner(NewsBannerRequest dto) {
		// created_date 값이 null인 경우 현재 시간으로 설정
	    if (dto.getCreatedDate() == null) {
	        dto.setCreatedDate(LocalDateTime.now());
	    }
		
		// 넘어온 데이터가 하나라도 없으면 에러
		for (Field field : dto.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if (field.get(dto) == null) {
					throw new ApiException(ErrorCode.NULL_POINT, "값을 입력 해 주세요");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// 중복이면 에러
		// 배너 테이블에 있는 url이랑 dto url이 같으면 에러
		String bannerUrl = bannersMapper.selectBannerUrl(dto.getUrl());

		if (bannerUrl != null) {
			throw new ApiException(ErrorCode.BAD_REQUEST, "중복된 뉴스");
		}

		// 배너에 넣기
		bannersMapper.insertBanner(dto);
	}

	// 뉴스 배너 리스트
	public List<NewsBannersResponse> NewsBannersList() {
		List<NewsBannersResponse> list = bannersMapper.newsbannerslist();

		return list;
	}

	// 뉴스 배너 수정
	public void updatedBanner(Long bannerId, NewsBannerRequest dto) {
		// dto가 없으면 에러
		if (dto == null) {
			throw new ApiException(ErrorCode.NULL_POINT, "입력한 값이 없습니다");
		}

		// 배너아이디가 없으면 에러
		if (bannerId == null) {
			throw new ApiException(ErrorCode.NULL_POINT, "뉴스 배너가 없습니다");
		}

		// 배너 아이디 사용해서 업데이트
		bannersMapper.updatedBanner(bannerId, dto);
	}

	// 배너 삭제
	public void deleteBanner(Long bannerId) {
		// 배너 있는지 확인
		if (bannerId == null) {
			throw new ApiException(ErrorCode.NULL_POINT, "선택한 배너 뉴스가 없습니다");
		}
		// 삭제
		bannersMapper.deleteBanner(bannerId);
	}

}
