package com.codewithprojects.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.codewithprojects.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskDto {
	private Long id;
	
	private String title;
	
	private String description;
	
	private Date dueDate;
	
	private String priority;
	
	private TaskStatus taskStatus;
	
	private Long employeeId;
	
	private String employeeName;

}
