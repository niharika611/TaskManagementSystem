package com.codewithprojects.controller.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithprojects.dto.AuthenticationRequest;
import com.codewithprojects.dto.AuthenticationResponse;
import com.codewithprojects.dto.CommentStatusDto;
import com.codewithprojects.dto.ResetRequest;
import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entities.User;
import com.codewithprojects.repositories.UserRepository;
import com.codewithprojects.services.auth.AuthService;
import com.codewithprojects.services.jwt.UserService;
import com.codewithprojects.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserService userService;
	
	private final UserRepository userRepository;
	
	private final JwtUtil jwtUtil;
	
//	private final String HEADER_STRING="Authorization";
//	
//	private final String TOKEN_PREFIX="Bearer";
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (Exception e) {
			throw new BadCredentialsException("Incorrect username or password.");
		}
		
		final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		AuthenticationResponse authenticationResponse=new AuthenticationResponse();
		if(optionalUser.isPresent()) {
		authenticationResponse.setJwt(jwt);
		authenticationResponse.setUserId(optionalUser.get().getId());
		authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		}
		return authenticationResponse;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signupRequest){
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("User already exists",HttpStatus.NOT_ACCEPTABLE);
		}
		UserDto userDto=authService.signUpUser(signupRequest);
		if(userDto==null)
			return new ResponseEntity<>("User not created",HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	@PutMapping("/reset")
	public ResponseEntity<?> signUpUser(@RequestBody ResetRequest resetRequest){
		authService.resetPassword(resetRequest);
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Password reset successfully\"}");
	}
	
	@GetMapping("/commentStatus")
	public ResponseEntity<List<CommentStatusDto>> getCommentStatus(){
		return ResponseEntity.ok(authService.getCommentStatus());
	}
	
	@PutMapping("/commentStatus/{id}")
	public ResponseEntity<?> updateCommentStatus(@PathVariable Long id){
		authService.updateCommentStatus(id);
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Comment status updated successfully\"}");
	}
	
}
