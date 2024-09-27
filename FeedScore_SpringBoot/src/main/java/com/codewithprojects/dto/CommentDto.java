package com.codewithprojects.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {
	
	private Long id;
	
	private String content;
	
	private Date createdAt;
	
	private Long postId;
	
	private Long userId;
	
	private String postedBy;
}
