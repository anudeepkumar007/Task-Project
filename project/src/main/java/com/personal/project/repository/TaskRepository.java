package com.personal.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.project.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findAllByUsersId(long userid);

}

