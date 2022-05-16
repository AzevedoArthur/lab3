package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.Curso; // Interface

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CursoRepositoryTest{
    @Autowired
    CursoRepository repository;
    
    @Test
    public void deveVerificarSalvarCurso() {
        // Cenário
        Curso curso_a_salvar = Curso.builder().nome("Ciência da Computação").nivel("Graduação").build();

        // Ação - operar no banco
        Curso salvo = repository.save(curso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(curso_a_salvar.getNivel(), salvo.getNivel());
    }
    
    @Test
    public void deveVerificarObterTodosCursos(){
        // Ação - operar no banco
        List<Curso> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterCursoSalvo(){
        // Ação - operar no banco
        Curso curso_a_salvar = Curso.builder().nome("Empresa").nivel("Graduação").build();

        // Ação - operar no banco
        Curso salvo = repository.save(curso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(curso_a_salvar.getNivel(), salvo.getNivel());

        Optional<Curso> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Curso q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getNivel(), q.getNivel());
    }
    
    @Test
    public void deveVerificarRemoverCursoSalvo(){
        // Ação - operar no banco
        Curso curso_a_salvar = Curso.builder().nome("Empresa").nivel("Graduação").build();

        // Ação - operar no banco
        Curso salvo = repository.save(curso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(curso_a_salvar.getNivel(), salvo.getNivel());

        repository.deleteById(salvo.getId());
        Optional<Curso> query = repository.findById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Assertions.assertTrue(query.isEmpty());
    }
}