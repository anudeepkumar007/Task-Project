package com.personal.project.service;

import java.util.List;

import com.personal.project.payload.TaskDto;

public interface TaskService {
	public TaskDto saveTask(long userid,TaskDto taskDto);
	
	public List<TaskDto> getAllTasks(long userid);
	
	public TaskDto getTask(long userid,long taskid);
	
	public void deleteTask(long userid,long taskid);

}
