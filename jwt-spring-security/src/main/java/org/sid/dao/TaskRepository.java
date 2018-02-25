package org.sid.dao;

import org.sid.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource    Si on met ça on aura l'API Rest directement
//@Repository   

public interface TaskRepository extends JpaRepository<Task,Long>{

}
