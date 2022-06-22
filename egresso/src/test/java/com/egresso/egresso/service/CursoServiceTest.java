package com.egresso.egresso.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.CursoEgresso;

import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.CursoEgressoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CursoServiceTest {
    @Autowired
    CursoService service;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    CursoEgressoRepository cursoEgressoRepository;
    @Autowired
    CursoRepository cursoRepository;
    
    private Egresso getEgressoValido() {
        return Egresso.builder().nome("João")
                                .email("joao.silva@testmail.com")
                                .cpf("11111111111")
                                .url_foto("https//foto.joao.com")
                                .resumo("Egresso de teste")
                                .build();
    }
    private Egresso getEgressoValido2() {
        return Egresso.builder().nome("Maria")
                                .email("maria.silva@testmail.com")
                                .cpf("11111111112")
                                .url_foto("https//foto.maria.com")
                                .resumo("Egresso de teste 2")
                                .build();
    }
    private Curso getCursoValido() {
        Curso curso = Curso.builder().nome("Curso de Teste")
                                     .nivel("Grad").build();;
        return curso;
    }
    private List<Curso> getCursosValidos() {
        return List.of(Curso.builder().nome("Curso de Teste")
                                     .nivel("Grad").build(),
                        Curso.builder().nome("Mestrado de Teste")
                        .nivel("Mestre").build());
    }
    private CursoEgresso getCursoEgressoValido(Egresso egresso, Curso curso) {
        return CursoEgresso.builder().curso(curso)
                                        .egresso(egresso)
                                        .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                        .data_conclusao(Date.valueOf(LocalDate.now())).build();
    }
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveObterNenhumEgressoPorUmCurso() {
        // Cenário
        Curso cursoTeste = getCursoValido();
        Curso cursoSalvo = cursoRepository.save(cursoTeste);
        // Ação
        List<Egresso> consulta = service.consultarEgressosPorCurso(cursoSalvo);
        // Teste
        Assertions.assertNotNull(consulta);
        Assertions.assertTrue(consulta.isEmpty());
    }
    @Test
    public void deveObterEgressosInseridosPorUmCurso() {
        // Cenário
        Curso cursoTeste = getCursoValido();
        Egresso egressoTeste1 = getEgressoValido();
        Egresso egressoTeste2 = getEgressoValido2();
        egressoTeste1.setCursos(Set.of(getCursoEgressoValido(egressoTeste1, cursoTeste)));
        egressoTeste2.setCursos(Set.of(getCursoEgressoValido(egressoTeste2, cursoTeste)));
        
        Curso cursoSalvo = cursoRepository.save(cursoTeste);
        Assertions.assertNotNull(cursoSalvo);
        Egresso egressoSalvo1 = egressoRepository.save(egressoTeste1);
        Assertions.assertNotNull(egressoSalvo1);
        Egresso egressoSalvo2 = egressoRepository.save(egressoTeste2);
        Assertions.assertNotNull(egressoSalvo2);
        // Ação
        List<Egresso> consulta = service.consultarEgressosPorCurso(cursoTeste);
        // Teste
        Set<Egresso> resultadoEsperado = Set.of(egressoSalvo1, egressoSalvo2);
        
        Assertions.assertEquals(resultadoEsperado.size(), consulta.size());
        Assertions.assertEquals(resultadoEsperado, Set.copyOf(consulta));
    }
    @Test
    public void deveObterQuantitativoZeroDeEgressosPorUmCurso() {
        // Cenário
        Curso cursoTeste = getCursoValido();
        Curso cursoSalvo = cursoRepository.save(cursoTeste);
        // Ação
        int consulta = service.qtdEgressosPorCurso(cursoSalvo);
        // Teste
        Assertions.assertEquals(0, consulta);
    }
    @Test
    public void deveObterQuantitativoDeEgressosInseridosPorUmCurso() {
        // Cenário
        Curso cursoTeste = getCursoValido();
        Egresso egressoTeste1 = getEgressoValido();
        Egresso egressoTeste2 = getEgressoValido2();
        egressoTeste1.setCursos(Set.of(getCursoEgressoValido(egressoTeste1, cursoTeste)));
        egressoTeste2.setCursos(Set.of(getCursoEgressoValido(egressoTeste2, cursoTeste)));
        
        Curso cursoSalvo = cursoRepository.save(cursoTeste);
        Assertions.assertNotNull(cursoSalvo);
        Egresso egressoSalvo1 = egressoRepository.save(egressoTeste1);
        Assertions.assertNotNull(egressoSalvo1);
        Egresso egressoSalvo2 = egressoRepository.save(egressoTeste2);
        Assertions.assertNotNull(egressoSalvo2);
        // Ação
        int consulta = service.qtdEgressosPorCurso(cursoTeste);
        // Teste
        Assertions.assertEquals(2, consulta);;
    }
    @Test
    public void deveObterCursosDeUmEgresso() {
        // Cenário
        List<Curso> cursosTeste = getCursosValidos();
        Egresso egressoTeste = getEgressoValido();
        egressoTeste.setCursos(Set.of(getCursoEgressoValido(egressoTeste, cursosTeste.get(0)), getCursoEgressoValido(egressoTeste, cursosTeste.get(1))));
        
        Curso cursoSalvo1 = cursoRepository.save(cursosTeste.get(0));
        Assertions.assertNotNull(cursoSalvo1);
        Curso cursoSalvo2 = cursoRepository.save(cursosTeste.get(1));
        Assertions.assertNotNull(cursoSalvo2);
        Egresso egressoSalvo = egressoRepository.save(egressoTeste);
        Assertions.assertNotNull(egressoSalvo);
        // Ação
        List<Curso> consulta = service.consultarCursosPorEgresso(egressoSalvo);
        // Teste
        Set<Curso> resultadoEsperado = Set.of(cursoSalvo1, cursoSalvo2);
        
        Assertions.assertEquals(resultadoEsperado.size(), consulta.size());
        Assertions.assertEquals(resultadoEsperado, Set.copyOf(consulta));
    }
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//=============================================================================================//

    
}
