package com.codewithprojects.controller.employee;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.services.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@GetMapping("/tasks/{userId}")
	public ResponseEntity<List<TaskDto>> getTaskByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(employeeService.getTasksByUserId(userId));
	}
	
	@GetMapping("/tasks/{id}/{status}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @PathVariable String status){
		TaskDto updatedTaskDto=employeeService.updateTask(id, status);
		if(updatedTaskDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.ok(updatedTaskDto);
	}
	

	@GetMapping("/task/{id}")
	public ResponseEntity<TaskDto> getTaskDto(@PathVariable Long id){
		return ResponseEntity.ok(employeeService.getTaskById(id));
	}
	
	@PostMapping("/task/comment/{id}/{taskId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable Long id,@PathVariable Long taskId,@RequestBody Map<String, String> payload){
		String comment = payload.get("content");
		CommentDto commentDto=employeeService.createComment(id,taskId,comment);
		if(commentDto==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
	}
	
	@GetMapping("/comments/{taskId}")
	public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId){
		return ResponseEntity.ok(employeeService.getCommentsByTaskId(taskId));
	}
	
	
}
