package org.sid.web;

import java.util.List;

import org.sid.dao.TaskRepository;
import org.sid.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRestController {
	
	@Autowired  //Si on a un construteur avec parametre on n'a pas besoin de Autowired 
	private TaskRepository taskRepository;

	/*public TaskRestController(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}*/
	
	@GetMapping("/tasks")
	public List<Task> getTasks()
	{
		return taskRepository.findAll();
	}
	
	@PostMapping("/tasks")
	public Task saveTask(@RequestBody Task t)
	{
		return taskRepository.save(t);
	}
	
   
}
