package br.com.tasklist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tasklist.model.Status;
import br.com.tasklist.model.Task;
import br.com.tasklist.repository.TaskRepository;
import br.com.tasklist.service.exception.BusinessRulesException;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Transactional
	public Task salvar(Task task) {
		boolean taskDuplicada = taskRepository.findByTitulo(task.getTitulo()).stream().anyMatch(taskExistente -> !taskExistente.equals(task));
		
		if (taskDuplicada) {
			throw new BusinessRulesException("Já existe uma task com o título: " + task.getTitulo());
		}
		
		return taskRepository.save(task);
	}
	
	@Transactional(readOnly = true)
	public List<Task> listarAbertosEConcluidos() {
		return taskRepository.findByStatusNot(Status.REMOVIDO);
	}
	
	@Transactional(readOnly = true)
	public Task buscar(Integer taskId) {
		return taskRepository.findById(taskId).orElseThrow(() -> new BusinessRulesException("Task não encontrada"));
	}

}
