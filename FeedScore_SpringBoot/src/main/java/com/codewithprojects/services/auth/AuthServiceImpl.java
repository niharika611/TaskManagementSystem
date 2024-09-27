package com.codewithprojects.services.auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithprojects.dto.CommentStatusDto;
import com.codewithprojects.dto.ResetRequest;
import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entities.CommentStatus;
import com.codewithprojects.entities.LikesRatings;
import com.codewithprojects.entities.Post;
import com.codewithprojects.entities.User;
import com.codewithprojects.enums.UserRole;
import com.codewithprojects.repositories.CommentStatusRepository;
import com.codewithprojects.repositories.LikesRatingsRepository;
import com.codewithprojects.repositories.PostRepository;
import com.codewithprojects.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	
private final UserRepository userRepository;

private final PostRepository postRepository;

private final LikesRatingsRepository likesRatingsRepository;

private final CommentStatusRepository commentStatusRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount=userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			User newAdminAccount=new User();
			newAdminAccount.setName("Admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setMobile("123456789");
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
		user.setMobile(signUpRequest.getMobile());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
		user.setUserRole(UserRole.EMPLOYEE);
		User createdUser=userRepository.save(user);
		List<Post> posts = postRepository.findAll();
		for (Post post : posts) {
	        LikesRatings likesRatings = new LikesRatings();
	        likesRatings.setPostId(post.getId()); 
	        likesRatings.setUserId(createdUser.getId());   
	        likesRatings.setLiked(false);
	        likesRatings.setRating((long) 0);
	        likesRatingsRepository.save(likesRatings); 
	    }
		return createdUser.getUserDto();
	}
	
	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

	@Override
	public void resetPassword(ResetRequest resetRequest) {
		Optional<User> optionalUser= userRepository.findFirstByEmail(resetRequest.getEmail());
		if(optionalUser.isPresent()) {
			User existingUser=optionalUser.get();
			existingUser.setPassword(new BCryptPasswordEncoder().encode(resetRequest.getPassword()));
			 userRepository.save(existingUser).getUserDto();
		}
	}
	
	@Override
	public List<CommentStatusDto> getCommentStatus() {
		List<CommentStatus> list=commentStatusRepository.findAll();
		return list.stream().map(CommentStatus::getCommentStatusDto).collect(Collectors.toList());
	}

	@Override
	public void updateCommentStatus(Long id) {
		commentStatusRepository.markNotificationsAsRead(id);
	}

}
