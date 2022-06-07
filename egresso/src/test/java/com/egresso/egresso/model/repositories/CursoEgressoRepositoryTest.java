package com.egresso.egresso.model.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.egresso.egresso.model.entities.CursoEgresso; // Interface
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
@Transactional
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
    public void deveVerificarNaoObterCursoEgressoByEgresso(){
        // Cenário
        Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
                                                                .email("terminou.curso@testmail.com")
                                                                .cpf("11111111111")
                                                                .url_foto("https//foto.form.com")
                                                                .resumo("Egresso de teste para curso_egresso").build());
        Assertions.assertNotNull(egresso_salvo);

        // Ação - operar no banco
        List<CursoEgresso> query = repository.findAllByEgresso(egresso_salvo);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertTrue(query.isEmpty());
    }
    
    @Test
    public void deveVerificarObterCursoEgressoSalvoByEgresso(){
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
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        CursoEgresso salvo = repository.save(curso_egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_egresso_a_salvar.getCurso().getId(), salvo.getCurso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_inicio(), salvo.getData_inicio());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_conclusao(), salvo.getData_conclusao());

        // Ação - operar no banco
        List<CursoEgresso> query = repository.findAllByEgresso(egresso_salvo);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        CursoEgresso q = query.get(0);
        Assertions.assertEquals(salvo.getCurso().getId(), q.getCurso().getId());
        Assertions.assertEquals(salvo.getEgresso().getId(), q.getEgresso().getId());
        Assertions.assertEquals(salvo.getData_inicio(), q.getData_inicio());
        Assertions.assertEquals(salvo.getData_conclusao(), q.getData_conclusao());
    }
    
    @Test
    public void deveVerificarObterMultiplosCursoEgressoSalvosByEgresso(){
        // Cenário
        Curso curso_salvo = cursoRepository.save(Curso.builder().nome("Curso Egressado")
                                                                .nivel("Grad").build());
        Assertions.assertNotNull(curso_salvo);
        Curso curso2_salvo = cursoRepository.save(Curso.builder().nome("Mestrado Egressado")
                                                                .nivel("Mestrado").build());
        Assertions.assertNotNull(curso2_salvo);

        Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
                                                                .email("terminou.curso@testmail.com")
                                                                .cpf("11111111111")
                                                                .url_foto("https//foto.form.com")
                                                                .resumo("Egresso de teste para curso_egresso").build());
        Assertions.assertNotNull(egresso_salvo);

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_salvo)
                                                                    .egresso(egresso_salvo)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        CursoEgresso salvo = repository.save(curso_egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso_egresso_a_salvar.getCurso().getId(), salvo.getCurso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_inicio(), salvo.getData_inicio());
        Assertions.assertEquals(curso_egresso_a_salvar.getData_conclusao(), salvo.getData_conclusao());

        CursoEgresso curso_egresso2_a_salvar = CursoEgresso.builder().curso(curso2_salvo)
                                                                    .egresso(egresso_salvo)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now().plusDays(2))).build();

        CursoEgresso salvo2 = repository.save(curso_egresso2_a_salvar);
   
        Assertions.assertNotNull(salvo2);
        Assertions.assertEquals(curso_egresso2_a_salvar.getCurso().getId(), salvo2.getCurso().getId());
        Assertions.assertEquals(curso_egresso2_a_salvar.getEgresso().getId(), salvo2.getEgresso().getId());
        Assertions.assertEquals(curso_egresso2_a_salvar.getData_inicio(), salvo2.getData_inicio());
        Assertions.assertEquals(curso_egresso2_a_salvar.getData_conclusao(), salvo2.getData_conclusao());

        // Ação - operar no banco
        List<CursoEgresso> query = repository.findAllByEgresso(egresso_salvo);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        CursoEgresso q = query.get(0);
        Assertions.assertEquals(salvo.getCurso().getId(), q.getCurso().getId());
        Assertions.assertEquals(salvo.getEgresso().getId(), q.getEgresso().getId());
        Assertions.assertEquals(salvo.getData_inicio(), q.getData_inicio());
        Assertions.assertEquals(salvo.getData_conclusao(), q.getData_conclusao());
        CursoEgresso q2 = query.get(1);
        Assertions.assertEquals(salvo2.getCurso().getId(), q2.getCurso().getId());
        Assertions.assertEquals(salvo2.getEgresso().getId(), q2.getEgresso().getId());
        Assertions.assertEquals(salvo2.getData_inicio(), q2.getData_inicio());
        Assertions.assertEquals(salvo2.getData_conclusao(), q2.getData_conclusao());
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
    
    // @Test
    // public void test1(){
    //     // Cenário
    //     Curso curso_salvo = cursoRepository.save(Curso.builder().nome("Curso Egressado")
    //                                                             .nivel("Grad").build());
    //     Assertions.assertNotNull(curso_salvo);

    //     Egresso egresso_salvo = egressoRepository.save(Egresso.builder().nome("Cursante")
    //                                                             .email("terminou.curso@testmail.com")
    //                                                             .cpf("11111111111")
    //                                                             .url_foto("https//foto.form.com")
    //                                                             .resumo("Egresso de teste para curso_egresso").build());
    //     Assertions.assertNotNull(egresso_salvo);
    //     Assertions.assertNull(egresso_salvo.getContatos());
    //     Assertions.assertNull(egresso_salvo.getDepoimentos());
    //     Assertions.assertNull(egresso_salvo.getCursos());
    //     Assertions.assertNull(egresso_salvo.getProfissoes());

    //     CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_salvo)
    //                                                                 .egresso(egresso_salvo)
    //                                                                 .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
    //                                                                 .data_conclusao(Date.valueOf(LocalDate.now())).build();

    //     CursoEgresso salvo = repository.save(curso_egresso_a_salvar);
    //     Assertions.assertNotNull(salvo);

    //     Egresso egressoAtualizado = salvo.getEgresso();
    //     Assertions.assertNotNull(egressoAtualizado);
    //     Assertions.assertNotNull(egressoAtualizado.getCursos());
    // }
}