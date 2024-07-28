package com.codewithprojects.services.admin;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entities.Comment;
import com.codewithprojects.entities.Task;
import com.codewithprojects.entities.User;
import com.codewithprojects.enums.TaskStatus;
import com.codewithprojects.enums.UserRole;
import com.codewithprojects.repositories.CommentRepository;
import com.codewithprojects.repositories.TaskRepository;
import com.codewithprojects.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final UserRepository userRepository;
	
	private final TaskRepository taskRepository;
	
	private final CommentRepository commentRepository;

	@Override
	public List<UserDto> getUsers() {
		return userRepository.findAll().stream().filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
				.map(User::getUserDto)
				.collect(Collectors.toList());
	}

	@Override
	public TaskDto createTask(TaskDto taskDto) {
		Optional<User> optionalUser=userRepository.findById(taskDto.getEmployeeId());
		if(optionalUser.isPresent()) {
			Task task=new Task();
			task.setTitle(taskDto.getTitle());
			task.setDescription(taskDto.getDescription());
			task.setPriority(taskDto.getPriority());
			task.setDueDate(taskDto.getDueDate());
			task.setTaskStatus(TaskStatus.INPROGRESS);
			task.setUser(optionalUser.get());
			return taskRepository.save(task).getTaskDto();	
		}
		return null;
	}

	@Override
	public List<TaskDto> getAllTasks() {
		return taskRepository.findAll().stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
				.map(Task::getTaskDto).collect(Collectors.toList());
	}

	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public TaskDto getTaskById(Long id) {
		Optional<Task> optionalTask= taskRepository.findById(id);
		return optionalTask.map(Task::getTaskDto).orElse(null);
	}

	@Override
	public TaskDto updateTask(Long id, TaskDto taskDto) {
		Optional<Task> optionalTask= taskRepository.findById(id);
		Optional<User> optionalUser=userRepository.findById(taskDto.getEmployeeId());
		if(optionalTask.isPresent() && optionalUser.isPresent()) {
			Task existingTask=optionalTask.get();
			existingTask.setTitle(taskDto.getTitle());
			existingTask.setDescription(taskDto.getDescription());
			existingTask.setDueDate(taskDto.getDueDate());
			existingTask.setPriority(taskDto.getPriority());
			existingTask.setUser(optionalUser.get());
			existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
			return taskRepository.save(existingTask).getTaskDto();
		}
		return null;
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
	public List<TaskDto> searchTaskByTitle(String title) {
		return taskRepository.findAllByTitleContaining(title)
				.stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
				.map(Task::getTaskDto).collect(Collectors.toList());
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
