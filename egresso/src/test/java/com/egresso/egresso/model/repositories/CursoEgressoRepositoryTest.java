package com.egresso.egresso.model.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.CursoEgresso; // Interface
import com.egresso.egresso.model.entities.CursoEgressoKey;
import com.egresso.egresso.model.entities.Curso; // Interface
import com.egresso.egresso.model.entities.Egresso; // Interface

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
public class CursoEgressoRepositoryTest{
    @Autowired
    CursoEgressoRepository repository;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    CursoRepository cursoRepository;
    
    @Test
    public void deveVerificarSalvarCursoEgresso() {
        // Cenário
        Curso curso_salvo = cursoRepository.save(Curso.builder().nome("Curso Egressado")
                                                                .nivel("Grad").build());
        Assertions.assertNotNull(curso_salvo);

        Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
                                                                .email("terminou.curso@testmail.com")
                                                                .cpf("11111111111")
                                                                .url_foto("https//foto.form.com")
                                                                .resumo("Egresso de teste para curso_egresso").build());
        Assertions.assertNotNull(egresso_salvo);

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_salvo)
                                                                    .egresso(egresso_salvo)
                                                                    .id(CursoEgressoKey.builder().curso_id(curso_salvo.getId()).egresso_id(egresso_salvo.getId()).build())
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        Assertions.assertEquals(curso_egresso_a_salvar.getId().getCurso_id(), curso_salvo.getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getId().getEgresso_id(), egresso_salvo.getId());
        // Ação - operar no banco
        CursoEgresso salvo = repository.save(curso_egresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_egresso_a_salvar.getCurso().getId(), salvo.getCurso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_inicio(), salvo.getData_inicio());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_conclusao(), salvo.getData_conclusao());
    }
    
    @Test
    public void deveVerificarObterTodosCursoEgressos(){
        // Ação - operar no banco
        List<CursoEgresso> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterCursoEgressoSalvo(){
        // Cenário
        Curso curso_salvo = cursoRepository.save(Curso.builder().nome("Curso Egressado")
                                                                .nivel("Grad").build());
        Assertions.assertNotNull(curso_salvo);

        Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
                                                                .email("terminou.curso@testmail.com")
                                                                .cpf("11111111111")
                                                                .url_foto("https//foto.form.com")
                                                                .resumo("Egresso de teste para curso_egresso").build());
        Assertions.assertNotNull(egresso_salvo);

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_salvo)
                                                                    .egresso(egresso_salvo)
                                                                    .id(CursoEgressoKey.builder().curso_id(curso_salvo.getId()).egresso_id(egresso_salvo.getId()).build())
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        CursoEgresso salvo = repository.save(curso_egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_egresso_a_salvar.getCurso().getId(), salvo.getCurso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_inicio(), salvo.getData_inicio());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_conclusao(), salvo.getData_conclusao());

        // Ação - operar no banco
        Optional<CursoEgresso> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        CursoEgresso q = query.get();
        Assertions.assertEquals(salvo.getCurso().getId(), q.getCurso().getId());
        Assertions.assertEquals(salvo.getEgresso().getId(), q.getEgresso().getId());
        Assertions.assertEquals(salvo.getData_inicio(), q.getData_inicio());
        Assertions.assertEquals(salvo.getData_conclusao(), q.getData_conclusao());
    }
    
    @Test
    public void deveVerificarRemoverCursoEgressoSalvo(){
        // Cenário
        Curso curso_salvo = cursoRepository.save(Curso.builder().nome("Curso Egressado")
                                                                .nivel("Grad").build());
        Assertions.assertNotNull(curso_salvo);

        Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
                                                                .email("terminou.curso@testmail.com")
                                                                .cpf("11111111111")
                                                                .url_foto("https//foto.form.com")
                                                                .resumo("Egresso de teste para curso_egresso").build());
        Assertions.assertNotNull(egresso_salvo);

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_salvo)
                                                                    .egresso(egresso_salvo)
                                                                    .id(CursoEgressoKey.builder().curso_id(curso_salvo.getId()).egresso_id(egresso_salvo.getId()).build())
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        CursoEgresso salvo = repository.save(curso_egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_egresso_a_salvar.getCurso().getId(), salvo.getCurso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_inicio(), salvo.getData_inicio());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_conclusao(), salvo.getData_conclusao());

        // Ação - operar no banco
        repository.deleteById(salvo.getId());

        // Verificação - A ação ocorreu?
        Optional<CursoEgresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
}