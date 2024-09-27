package com.codewithprojects.dto;

import com.codewithprojects.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt;
	
	private Long userId;
	
	private UserRole userRole;
}
