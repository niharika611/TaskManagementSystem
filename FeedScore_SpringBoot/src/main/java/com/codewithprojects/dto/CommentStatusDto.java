package com.codewithprojects.dto;

import lombok.Data;

@Data
public class CommentStatusDto {
	
	private Long id;
	
	private String content;
	
	private Long commentedUserId;
	
	private String commentedUserName;
	
	private Long postId;
	
	private Long postCreatedUserId;
	
	private boolean readStatus;
	
}
