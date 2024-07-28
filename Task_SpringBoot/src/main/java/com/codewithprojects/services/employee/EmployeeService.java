package com.codewithprojects.services.employee;

import java.util.List;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.TaskDto;

public interface EmployeeService {
	
	List<TaskDto> getTasksByUserId(Long userId);
	
	TaskDto updateTask(Long id, String status);
	
	TaskDto getTaskById(Long id);
	
	CommentDto createComment(Long userId, Long taskId, String content);
	
	List<CommentDto> getCommentsByTaskId(Long taskId);

}
