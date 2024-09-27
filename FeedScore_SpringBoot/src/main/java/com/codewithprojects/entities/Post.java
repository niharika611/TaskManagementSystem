package com.codewithprojects.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.codewithprojects.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private boolean liked;
	
	private Long rating;
	
	private Long userId;
	
	@Column(columnDefinition="bytea")
	private byte[] image;
	
	public int compareTo(Post other) {
        return this.id.compareTo(other.id);
    }

	public PostDto getPostDto() {
		PostDto postDto=new PostDto();
		postDto.setId(id);
		postDto.setTitle(title);
		postDto.setDescription(description);
		postDto.setLiked(liked);
		postDto.setRating(rating);
		postDto.setImage(image);
		postDto.setUserId(userId);
		return postDto;
	}
	
}
