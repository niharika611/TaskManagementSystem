package com.codewithprojects.dto;

import lombok.Data;

@Data
public class LikesRatingsDto {
	
	private Long id;
	
	private Long userId;
	
	private Long postId;
	
	private boolean liked;
	
	private Long rating;
}
