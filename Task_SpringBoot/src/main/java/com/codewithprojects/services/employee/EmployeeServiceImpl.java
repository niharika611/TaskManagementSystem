package com.codewithprojects.services.employee;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.entities.Comment;
import com.codewithprojects.entities.Task;
import com.codewithprojects.entities.User;
import com.codewithprojects.enums.TaskStatus;
import com.codewithprojects.repositories.CommentRepository;
import com.codewithprojects.repositories.TaskRepository;
import com.codewithprojects.repositories.UserRepository;
import com.codewithprojects.utils.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
	
	private final TaskRepository taskRepository;
	
	private final UserRepository userRepository;
	
	private final CommentRepository commentRepository;
	
	private final JwtUtil jwtUtil;

	@Override
	public List<TaskDto> getTasksByUserId(Long userId) {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent()) {
			User user1=user.get();
			return taskRepository.findAllByUserId(user1.getId())
			.stream().sorted(Comparator.comparing(Task::getDueDate).reversed()).map(Task::getTaskDto)
					.collect(Collectors.toList());
		}
		throw new EntityNotFoundException("User not found");
	}

	@Override
	public TaskDto updateTask(Long id, String status) {
		Optional<Task> optionalTask=taskRepository.findById(id);
		if(optionalTask.isPresent()) {
			Task existingTask=optionalTask.get();
			existingTask.setTaskStatus(mapStringToTaskStatus(status));
			return taskRepository.save(existingTask).getTaskDto();
		}
		throw new EntityNotFoundException("Task not found");
	}
	
	private TaskStatus mapStringToTaskStatus(String status) {
		status = status.trim().toUpperCase();
		return switch(status) {
		case "PENDING" -> TaskStatus.PENDING;
		case "INPROGRESS" -> TaskStatus.INPROGRESS;
		case "COMPLETED" -> TaskStatus.COMPLETED;
		case "DEFERRED" -> TaskStatus.DEFERRED;
		default -> TaskStatus.CANCELLED;
		};
	}
	
	@Override
	public TaskDto getTaskById(Long id) {
		Optional<Task> optionalTask= taskRepository.findById(id);
		return optionalTask.map(Task::getTaskDto).orElse(null);
	}


	@Override
	public CommentDto createComment(Long userId, Long taskId, String content) {
		Optional<Task> optionalTask=taskRepository.findById(taskId);
		Optional<User> user=userRepository.findById(userId);
		if(optionalTask.isPresent() && user.isPresent()) {
			Comment comment =new Comment();
			comment.setCreatedAt(new Date());
			comment.setContent(content);
			comment.setTask(optionalTask.get());
			comment.setUser(user.get());
			return commentRepository.save(comment).getCommentDto();
		}
		throw new EntityNotFoundException("User or Task not found");
	}

	@Override
	public List<CommentDto> getCommentsByTaskId(Long taskId) {
		return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDto).collect(Collectors.toList());
	}

}
