CREATE TABLE tasklist.task (
	id INTEGER auto_increment NOT NULL,
	titulo varchar(30) NOT NULL,
	status varchar(10) NOT NULL,
	descricao varchar(200) NULL,
	data_criacao DATETIME NOT NULL,
	data_edicao DATETIME NULL,
	data_remocao DATETIME NULL,
	data_conclusao DATETIME NULL,
	CONSTRAINT task_pk PRIMARY KEY (id)
);