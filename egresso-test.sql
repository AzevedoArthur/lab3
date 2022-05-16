-- test.cargo definition

-- Drop table

-- DROP TABLE test.cargo;


CREATE TABLE IF NOT EXISTS test.cargo (
	id_cargo serial4 NOT NULL,
	nome varchar NOT NULL,
	descricao varchar NULL,
	CONSTRAINT cargo_pk PRIMARY KEY (cargo_id)
);


-- test.contato definition

-- Drop table

-- DROP TABLE test.contato;

CREATE TABLE IF NOT EXISTS test.contato (
	id_contato serial4 NOT NULL,
	nome varchar NOT NULL,
	url_logo varchar NULL,
	CONSTRAINT contato_pk PRIMARY KEY (id_contato)
);


-- test.egresso definition

-- Drop table

-- DROP TABLE test.egresso;

CREATE TABLE IF NOT EXISTS test.egresso (
	id_egresso serial4 NOT NULL,
	nome varchar NOT NULL,
	email varchar NOT NULL,
	cpf varchar NOT NULL,
	resumo varchar NULL,
	url_foto varchar NULL,
	CONSTRAINT egresso_pk PRIMARY KEY (id_egresso)
);


-- test.curso definition

-- Drop table

-- DROP TABLE test.curso;

CREATE TABLE IF NOT EXISTS test.curso (
	id_curso serial4 NOT NULL,
	nome varchar NOT NULL,
	nivel varchar NOT NULL DEFAULT 'Graduação'::character varying,
	CONSTRAINT curso_pk PRIMARY KEY (id_curso)
);


-- test.faixa_salario definition

-- Drop table

-- DROP TABLE test.faixa_salario;

CREATE TABLE IF NOT EXISTS test.faixa_salario (
	id_faixa_salario serial4 NOT NULL,
	descricao varchar NOT NULL,
	CONSTRAINT faixa_salario_pk PRIMARY KEY (id_faixa_salario)
);


-- test.contato_egresso definition

-- Drop table

-- DROP TABLE test.contato_egresso;

CREATE TABLE IF NOT EXISTS test.contato_egresso (
	egresso_id int4 NOT NULL,
	contato_id int4 NOT NULL,
	CONSTRAINT contato_egresso_pk PRIMARY KEY (egresso_id, contato_id),
	CONSTRAINT contato_fk FOREIGN KEY (contato_id) REFERENCES test.contato(id_contato),
	CONSTRAINT egresso_fk FOREIGN KEY (egresso_id) REFERENCES test.egresso(id_egresso)
);


-- test.curso_egresso definition

-- Drop table

-- DROP TABLE test.curso_egresso;

CREATE TABLE IF NOT EXISTS test.curso_egresso (
	egresso_id int4 NOT NULL,
	curso_id int4 NOT NULL,
	data_inicio date NOT NULL,
	data_conclusao date NULL,
	CONSTRAINT curso_egresso_pk PRIMARY KEY (egresso_id, curso_id),
	CONSTRAINT curso_fk FOREIGN KEY (curso_id) REFERENCES test.curso(id_curso),
	CONSTRAINT egresso_fk FOREIGN KEY (egresso_id) REFERENCES test.egresso(id_egresso)
);


-- test.depoimento definition

-- Drop table

-- DROP TABLE test.depoimento;

CREATE TABLE IF NOT EXISTS test.depoimento (
	id_depoimento serial4 NOT NULL,
	egresso_id int4 NOT NULL,
	texto varchar NOT NULL,
	"data" date NOT NULL,
	CONSTRAINT depoimento_pk PRIMARY KEY (id_depoimento),
	CONSTRAINT egresso_fk FOREIGN KEY (egresso_id) REFERENCES test.egresso(id_egresso)
);


-- test.prof_egresso definition

-- Drop table

-- DROP TABLE test.prof_egresso;

CREATE TABLE IF NOT EXISTS test.prof_egresso (
	id_prof_egresso serial4 NOT NULL,
	egresso_id int4 NOT NULL,
	cargo_id int4 NOT NULL,
	faixa_salario_id int4 NOT NULL,
	empresa varchar NULL,
	descricao varchar NULL,
	data_registro date NOT NULL DEFAULT CURRENT_DATE,
	CONSTRAINT prof_egresso_pk PRIMARY KEY (id_prof_egresso),
	CONSTRAINT cargo_fk FOREIGN KEY (cargo_id) REFERENCES test.cargo(cargo_id),
	CONSTRAINT egresso_fk FOREIGN KEY (egresso_id) REFERENCES test.egresso(id_egresso),
	CONSTRAINT faixa_salario_fk FOREIGN KEY (faixa_salario_id) REFERENCES test.faixa_salario(id_faixa_salario)
);