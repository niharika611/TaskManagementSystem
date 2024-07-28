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
import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Validated
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(){
		return ResponseEntity.ok(adminService.getUsers());
	}
	
	@PostMapping("/task")
	public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
		TaskDto createdTaskDto=adminService.createTask(taskDto);
		if(createdTaskDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasks(){
		return ResponseEntity.ok(adminService.getAllTasks());
	}
	
	@DeleteMapping("/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id){
		adminService.deleteTask(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/task/{id}")
	public ResponseEntity<?> updateTask(@PathVariable Long id,@RequestBody TaskDto taskDto){
		TaskDto updatedTask=adminService.updateTask(id,taskDto);
		if(updatedTask==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(adminService.updateTask(id,taskDto));
	}
	
	@GetMapping("/tasks/search/{title}")
	public ResponseEntity<List<TaskDto>> searchTask(@PathVariable String title){
		return ResponseEntity.ok(adminService.searchTaskByTitle(title));
	}
	
	@GetMapping("/task/{id}")
	public ResponseEntity<TaskDto> getTaskDto(@PathVariable Long id){
		return ResponseEntity.ok(adminService.getTaskById(id));
	}
	
	@PostMapping("/task/comment/{id}/{taskId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable Long id,@PathVariable Long taskId,@RequestBody Map<String, String> payload){
		String comment = payload.get("content");
		CommentDto commentDto=adminService.createComment(id,taskId,comment);
		if(commentDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
	}
	
	@GetMapping("/comments/{taskId}")
	public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId){
		return ResponseEntity.ok(adminService.getCommentsByTaskId(taskId));
	}

}
