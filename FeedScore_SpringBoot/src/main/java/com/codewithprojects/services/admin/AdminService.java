package com.codewithprojects.services.admin;

import java.util.List;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.LikesRatingsDto;
import com.codewithprojects.dto.PostDto;

public interface AdminService {
	
PostDto createPost(PostDto taskDto);
	
	List<PostDto> getAllPosts();
	
	List<LikesRatingsDto> getAllLikesRatings();
	
//	void likePost(Long postId);
//	
//	void ratePost(Long postId,Long rating);
	

	void likePost(Long postId, Long userId);

	void ratePost(Long postId, Long rating, Long userId);
	
	void deletePost(Long id);
	
	PostDto updatePost(Long id,PostDto postDto);
	
	PostDto getPostById(Long id);
	
	List<PostDto> searchPostByTitle(String name);
	
CommentDto createComment(Long userId, Long postId, String content);
	
	List<CommentDto> getCommentsByPostId(Long postId);
	
}

