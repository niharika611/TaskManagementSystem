package com.codewithprojects.entities;

import com.codewithprojects.dto.LikesRatingsDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LikesRatings {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private Long postId;
	
	private boolean liked;
	
	private Long rating;
	
	public LikesRatingsDto getLikesRatingsDto() {
		LikesRatingsDto likesRatingsDto=new LikesRatingsDto();
		likesRatingsDto.setId(id);
		likesRatingsDto.setUserId(userId);
		likesRatingsDto.setPostId(postId);
		likesRatingsDto.setLiked(liked);
		likesRatingsDto.setRating(rating);
		return likesRatingsDto;
	}
}
