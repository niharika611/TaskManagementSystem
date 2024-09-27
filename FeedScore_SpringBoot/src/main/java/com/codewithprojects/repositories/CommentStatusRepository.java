package com.codewithprojects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithprojects.entities.CommentStatus;

import jakarta.transaction.Transactional;

@Repository
public interface CommentStatusRepository extends JpaRepository<CommentStatus, Long>{

	@Modifying
    @Transactional
    @Query("UPDATE CommentStatus cs SET cs.readStatus = true WHERE cs.postCreatedUserId = :postCreatedUserId")
    void markNotificationsAsRead(@Param("postCreatedUserId") Long postCreatedUserId);

}
