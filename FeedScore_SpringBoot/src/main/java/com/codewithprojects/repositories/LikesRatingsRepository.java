package com.codewithprojects.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithprojects.entities.LikesRatings;
import com.codewithprojects.entities.User;
import com.codewithprojects.enums.UserRole;

import jakarta.transaction.Transactional;

@Repository
public interface LikesRatingsRepository extends JpaRepository<LikesRatings, Long>{

	LikesRatings findByPostIdAndUserId(Long postId, Long userId);
	
	

}
