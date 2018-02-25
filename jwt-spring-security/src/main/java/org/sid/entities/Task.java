package org.sid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data @AllArgsConstructor @NoArgsConstructor 
public class Task {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String taskName;
	 
	public Task() {}
	
	public Task(String taskName) {
		this.taskName = taskName;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
