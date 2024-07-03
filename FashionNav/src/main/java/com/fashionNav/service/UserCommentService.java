package com.fashionNav.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fashionNav.model.dto.response.UserCommentResponse;
import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.model.entity.User;
import com.fashionNav.repository.UserCommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommentService {

	private final UserCommentMapper userCommentMapper;

	public Map<String, Object> getUserCommentList(int sizeNum, int pageSize, Long userId) {
		String userName = userCommentMapper.findUserName( userId );
		
		int offSet = ( sizeNum - 1 ) * pageSize;
		List<UserCommentResponse> userComment = userCommentMapper.findByUserComment( offSet, userId, pageSize );
		
		int total = userCommentMapper.postNewsCountByUserId( userId );
		int pageTotal = ( int )Math.round(( double )total / pageSize); 
		
		Map<String, Object> result = new HashMap<>();
		result.put("userName", userName);
		result.put("userComment", userComment);
		result.put("pageTotal", pageTotal);
		
		return result;
	}

	
	public Post getPostById(int postId) {
        Post post = userCommentMapper.findPostById(postId);
        User user = userCommentMapper.findUserById(post.getUserId());
        post.setUserName(user.getName());
        List<Post> replies = userCommentMapper.findRepliesByPostId(postId);
        post.setReplies(replies);
        return post;
    }
	
	
	public RawNews getDetailNews(Long newsId) {
        return userCommentMapper.findByDetailNews(newsId);
    }
	
}
