package org.sid;

import java.util.stream.Stream;
import org.sid.dao.TaskRepository;
import org.sid.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner{

	@Autowired
	private TaskRepository taskRepository;
	
	@Bean
	public BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityApplication.class, args);	
	}
	
	@Override
	public void run (String... args) throws Exception
	{
		Stream.of("T1","T2","T3").forEach(t->{
			Task task =new Task(t);
			task.setTaskName(t);
			taskRepository.save(task);
		});
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});
	}
} 
