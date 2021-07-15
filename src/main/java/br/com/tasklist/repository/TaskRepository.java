package br.com.tasklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tasklist.model.Status;
import br.com.tasklist.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByTitulo(String titulo);
	List<Task> findByStatusNot(Status status);
}