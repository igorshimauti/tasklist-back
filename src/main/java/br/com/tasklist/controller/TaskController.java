package br.com.tasklist.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tasklist.model.Status;
import br.com.tasklist.model.Task;
import br.com.tasklist.model.dto.DtoTask;
import br.com.tasklist.model.dto.input.InputTask;
import br.com.tasklist.model.mapper.TaskMapper;
import br.com.tasklist.repository.TaskRepository;
import br.com.tasklist.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DtoTask>> listarAbertosEConcluidos() {
		List<Task> tasks = taskService.listarAbertosEConcluidos();
		return ResponseEntity.ok(taskMapper.toCollectionDto(tasks));
	}
	
	@GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DtoTask> buscar(@PathVariable Integer taskId) {
		return taskRepository.findById(taskId)
				.map(task -> ResponseEntity.ok(taskMapper.toDto(task)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DtoTask> incluir(@Valid @RequestBody InputTask input) {
		Task task = taskMapper.toEntity(input);
		task.setDataCriacao(LocalDateTime.now());
		task.setStatus(Status.ABERTO);
		return ResponseEntity.ok(taskMapper.toDto(taskService.salvar(task)));
	}
	
	@PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DtoTask> editar(@PathVariable Integer taskId, @Valid @RequestBody InputTask input) {
		if (!taskRepository.existsById(taskId)) {
			return ResponseEntity.notFound().build();
		}
		
		Task task = taskService.buscar(taskId);
		task.setTitulo(input.getTitulo());
		task.setDescricao(input.getDescricao());
		task.setStatus(input.getStatus());
		
		if (task.getStatus().equals(Status.CONCLUIDO)) {
			task.setDataConclusao(LocalDateTime.now());
		} else {
			task.setDataEdicao(LocalDateTime.now());
		}		
		
		return ResponseEntity.ok(taskMapper.toDto(taskService.salvar(task)));
	}
	
	@DeleteMapping(value = "/{taskId}")
	public ResponseEntity<Void> remover(@PathVariable Integer taskId) {
		if (!taskRepository.existsById(taskId)) {
			return ResponseEntity.notFound().build();
		}
		
		Task task = taskService.buscar(taskId);
		task.setDataRemocao(LocalDateTime.now());
		task.setStatus(Status.REMOVIDO);
		taskService.salvar(task);
		return ResponseEntity.noContent().build();
	}
}