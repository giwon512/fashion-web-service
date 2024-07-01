package com.fashionNav.repository;

import java.util.List;

import com.fashionNav.model.dto.response.SavePageListResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.fashionNav.model.entity.UserSavedPage;

/**
 * UserSavePageMapper 인터페이스는 사용자 저장 페이지 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 사용자 저장 페이지 데이터를 조회, 삽입 및 삭제하는 메서드를 제공합니다.
 */
@Mapper
public interface UserSavePageMapper {

	@Insert("INSERT INTO UserSavedPage ( user_Id, news_Id ) VALUES ( #{userId}, #{newsId} )")
	void insertUserIdPageId(@Param("userId") Long userId, @Param("newsId") Long newsId );
	
	@Select("SELECT news_id AS newsId, saved_date AS savedDate FROM UserSavedPage WHERE user_id = #{userId}")
    List<UserSavedPage> findByUserId(Long userId);
	
	@Select("<script>"
            + "SELECT news_id AS newsId, title, image_url AS imageUrl "
            + "FROM Raw_News "
            + "WHERE news_id IN "
            + "<foreach item='newsId' collection='newsIds' open='(' separator=',' close=')'>"
            + "#{newsId}"
            + "</foreach>"
            + "</script>")
    List<SavePageListResponse> findByNewsIds(@Param("newsIds") List<Long> newsIds);

	@Delete("DELETE FROM UserSavedPage WHERE user_Id = #{userId} AND news_Id = #{newsId}")
    void deleteUserPageByUserIdAndNewsId(@Param("userId") Long userId, @Param("newsId") Long newsId);


	@Delete("DELETE FROM UserSavedPage WHERE news_Id = #{newsId}")
    void deleteByNewsId(@Param("newsId") Long newsId);
}
