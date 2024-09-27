package com.codewithprojects.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.LikesRatingsDto;
import com.codewithprojects.dto.PostDto;
import com.codewithprojects.services.admin.AdminService;
import com.codewithprojects.services.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Validated
public class AdminController {
	
	private final AdminService adminService;
	
	@PostMapping("/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto taskDto){
		PostDto createdPostDto=adminService.createPost(taskDto);
		if(createdPostDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDto);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts(){
		return ResponseEntity.ok(adminService.getAllPosts());
	}
	
	@PutMapping("/likePost")
    public ResponseEntity<String> likePost(@RequestBody PostDto postDto) {
		adminService.likePost(postDto.getId(),postDto.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Post liked successfully\"}");
    }
	
	@PutMapping("/ratePost")
	public ResponseEntity<String> ratePost(@RequestBody PostDto postDto) {
	    Long ratingValue = postDto.getRating(); 
	    adminService.ratePost(postDto.getId(), ratingValue, postDto.getUserId());
	    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Post rated successfully\"}");
	}
	
	@DeleteMapping("/post/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id){
		adminService.deletePost(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/post/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id,@RequestBody PostDto postDto){
		PostDto updatedPost=adminService.updatePost(id,postDto);
		if(updatedPost==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(adminService	.updatePost(id,postDto));
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity<PostDto> getPostDto(@PathVariable Long id){
		return ResponseEntity.ok(adminService.getPostById(id));
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<PostDto>> getAllPostsByName(@PathVariable String name){
		List<PostDto> postDtos=adminService.searchPostByTitle(name);
		return ResponseEntity.ok(postDtos);
	}
	
	@PostMapping("/post/comment/{id}/{postId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable Long id,@PathVariable Long postId,@RequestBody Map<String, String> payload){
		String comment = payload.get("content");
		CommentDto commentDto=adminService.createComment(id,postId,comment);
		if(commentDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
	}
	
	@GetMapping("/comments/{postId}")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId){
		return ResponseEntity.ok(adminService.getCommentsByPostId(postId));
	}
	
	@GetMapping("/likesRatings")
	public ResponseEntity<List<LikesRatingsDto>> getAllLikesRatings(){
		return ResponseEntity.ok(adminService.getAllLikesRatings());
	}
}
