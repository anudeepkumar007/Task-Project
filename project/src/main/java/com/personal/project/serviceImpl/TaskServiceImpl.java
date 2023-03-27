package com.personal.project.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.personal.project.entity.Task;
import com.personal.project.entity.Users;
import com.personal.project.exception.APIException;
import com.personal.project.exception.TaskNotFound;
import com.personal.project.exception.UserNotFound;
import com.personal.project.payload.TaskDto;
import com.personal.project.repository.TaskRepository;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Users users=userRepository.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task=modelMapper.map(taskDto, Task.class);
		task.setUsers(users);
		Task savedTask=taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {
		userRepository.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		List<Task> tasks=taskRepository.findAllByUsersId(userid);
		return tasks.stream().map(
				task->modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		Users user=userRepository.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task=taskRepository.findById(taskid).orElseThrow(
				()-> new TaskNotFound(String.format("Todo Id %d not found", taskid))
				);
		if(user.getId() !=task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d doesnot match to User Id %d", taskid,userid)); 
		}
		
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		
		Users user=userRepository.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task=taskRepository.findById(taskid).orElseThrow(
				()-> new TaskNotFound(String.format("Todo Id %d not found", taskid))
				);
		if(user.getId() !=task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d doesnot match to User Id %d", taskid,userid)); 
		}
		
		taskRepository.deleteById(taskid);
		
		
	} 

}
