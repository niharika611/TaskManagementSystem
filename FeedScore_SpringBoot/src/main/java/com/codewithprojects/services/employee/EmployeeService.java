package com.codewithprojects.services.employee;

import java.util.List;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.CommentStatusDto;
import com.codewithprojects.dto.LikesRatingsDto;
import com.codewithprojects.dto.PostDto;

public interface EmployeeService {
	
	PostDto createPost(PostDto taskDto);
	
	List<PostDto> getAllPosts();
	
	//void likePost(Long postId);
	
	//void ratePost(Long postId,Long rating);
	
	void deletePost(Long id);
	
	PostDto updatePost(Long id,PostDto postDto);
	
	PostDto getPostById(Long id);
	
	List<PostDto> searchPostByTitle(String name);
	
	List<LikesRatingsDto> getAllLikesRatings();
	
	CommentDto createComment(Long userId, Long postId, String content);
	
	List<CommentDto> getCommentsByPostId(Long postId);

	void likePost(Long postId, Long userId);

//	List<CommentStatusDto> getCommentStatus();
	
	void ratePost(Long postId, Long rating, Long userId);
	
//	void updateCommentStatus(Long id);
	
//	CommentDto createComment(Long userId, Long taskId, String content);
//	
//	List<CommentDto> getCommentsByTaskId(Long taskId);

}
