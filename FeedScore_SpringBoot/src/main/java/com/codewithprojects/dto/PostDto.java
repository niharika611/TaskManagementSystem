package com.codewithprojects.dto;

import lombok.Data;

@Data
public class PostDto {
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private boolean liked;
	
	private Long rating;
	
	private byte[] image;

	private Long userId;
}
