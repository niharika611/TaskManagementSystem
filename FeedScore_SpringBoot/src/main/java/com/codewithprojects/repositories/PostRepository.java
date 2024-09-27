package com.codewithprojects.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithprojects.entities.Post;

import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findAllByTitleContaining(String title);

	List<Post> findAllByTitleContainingOrDescriptionContaining(String title,String description);

	@Modifying
    @Transactional
    @Query("UPDATE Post p SET p.liked = CASE WHEN p.liked = true THEN false ELSE true END WHERE p.id = :postId")
    void likePostById(Long postId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Post p SET p.rating = :rating WHERE p.id = :postId")
	void ratePostById(Long postId, @Param("rating") Long rating);

}
