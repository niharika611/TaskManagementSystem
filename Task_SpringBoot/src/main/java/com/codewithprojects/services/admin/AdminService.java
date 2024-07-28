package com.codewithprojects.services.admin;

import java.util.List;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.dto.UserDto;

public interface AdminService {
	
	List<UserDto> getUsers();
	
	TaskDto createTask(TaskDto taskDto);
	
	List<TaskDto> getAllTasks();
	
	void deleteTask(Long id);
	
	
	
	TaskDto updateTask(Long id,TaskDto taskDto);
	
	List<TaskDto> searchTaskByTitle(String title);
	
	TaskDto getTaskById(Long id);
	
	CommentDto createComment(Long userId, Long taskId, String content);
	
	List<CommentDto> getCommentsByTaskId(Long taskId);
}

