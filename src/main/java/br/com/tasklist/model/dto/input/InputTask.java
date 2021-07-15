package br.com.tasklist.model.dto.input;

import javax.validation.constraints.NotBlank;

import br.com.tasklist.model.Status;

public class InputTask {

	@NotBlank
	private String titulo;
	
	private Status status;
	private String descricao;
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
