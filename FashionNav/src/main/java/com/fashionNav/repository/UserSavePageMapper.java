package com.fashionNav.repository;

import java.util.List;

import com.fashionNav.model.dto.response.SavePageListResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.fashionNav.model.entity.UserSavedPage;

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
	
	
}
