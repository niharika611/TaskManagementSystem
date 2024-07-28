package com.codewithprojects.services.auth;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;

public interface AuthService {

	UserDto signUpUser(SignUpRequest signUpRequest);
	
	boolean hasUserWithEmail(String email);
}
