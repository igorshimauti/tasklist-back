package br.com.tasklist.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tasklist.model.Task;
import br.com.tasklist.model.dto.DtoTask;
import br.com.tasklist.model.dto.input.InputTask;

@Component
public class TaskMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public DtoTask toDto(Task task) {
		return modelMapper.map(task, DtoTask.class);
	}
	
	public List<DtoTask> toCollectionDto(List<Task> tasks) {
		return tasks.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	public Task toEntity(InputTask input) {
		return modelMapper.map(input, Task.class);
	}
}