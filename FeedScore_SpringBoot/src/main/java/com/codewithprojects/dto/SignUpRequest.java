package com.codewithprojects.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String mobile;
}
