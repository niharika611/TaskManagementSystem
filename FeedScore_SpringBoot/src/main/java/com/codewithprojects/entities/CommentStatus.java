package com.codewithprojects.entities;

import com.codewithprojects.dto.CommentStatusDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CommentStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	
	private Long commentedUserId;
	
	private String commentedUserName;
	
	private Long postId;
	
	private Long postCreatedUserId;
	
	private boolean readStatus;
	
	public CommentStatusDto getCommentStatusDto() {
		CommentStatusDto commentStatusDto=new CommentStatusDto();
		commentStatusDto.setId(id);
		commentStatusDto.setPostId(postId);
		commentStatusDto.setContent(content);
		commentStatusDto.setCommentedUserId(commentedUserId);
		commentStatusDto.setCommentedUserName(commentedUserName);
		commentStatusDto.setPostCreatedUserId(postCreatedUserId);
		commentStatusDto.setReadStatus(readStatus);
		return commentStatusDto;
	}
}
