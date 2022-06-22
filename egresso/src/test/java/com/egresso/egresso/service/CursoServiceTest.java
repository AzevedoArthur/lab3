package com.egresso.egresso.service;

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
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveObterNenhumEgressoPorUmCurso() {}
    @Test
    public void deveObterEgressosInseridosPorUmCurso() {}
    @Test
    public void deveObterQuantitativoZeroDeEgressosPorUmCurso() {}
    @Test
    public void deveObterQuantitativoDeEgressosInseridosPorUmCurso() {}
    @Test
    public void deveObterCursosDeUmEgresso() {}
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//=============================================================================================//

    
}
