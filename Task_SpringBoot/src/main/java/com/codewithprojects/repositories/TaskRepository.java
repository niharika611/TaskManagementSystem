package com.codewithprojects.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithprojects.dto.TaskDto;
import com.codewithprojects.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findAllByTitleContaining(String title);

	List<Task> findAllByUserId(Long id);

}
