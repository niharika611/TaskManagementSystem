package com.codewithprojects.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entities.User;
import com.codewithprojects.enums.UserRole;
import com.codewithprojects.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	
private final UserRepository userRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount=userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			User newAdminAccount=new User();
			newAdminAccount.setName("Admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdminAccount.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminAccount);
			System.out.println("Admin account created successfully");
		}
		else {
			System.out.println("Admin account already exists");
		}
	}

	@Override
	public UserDto signUpUser(SignUpRequest signUpRequest) {
		User user=new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
		user.setUserRole(UserRole.EMPLOYEE);
		User createdUser=userRepository.save(user);
		return createdUser.getUserDto();
	}
	
	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

}
