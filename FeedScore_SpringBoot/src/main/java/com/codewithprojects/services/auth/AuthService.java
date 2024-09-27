package com.codewithprojects.services.auth;

import java.util.List;

import com.codewithprojects.dto.CommentStatusDto;
import com.codewithprojects.dto.ResetRequest;
import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;

public interface AuthService {

	UserDto signUpUser(SignUpRequest signUpRequest);
	
	boolean hasUserWithEmail(String email);
	
	void resetPassword(ResetRequest ResetRequest);
	
	void updateCommentStatus(Long id);
	
	List<CommentStatusDto> getCommentStatus();
}
