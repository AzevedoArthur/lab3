package com.egresso.egresso.model.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.Depoimento; // Interface
import com.egresso.egresso.model.entities.Egresso;

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
public class DepoimentoRepositoryTest{
    @Autowired
    DepoimentoRepository repository;
    @Autowired
    EgressoRepository egressoRepository;
    
    @Test
    public void deveVerificarSalvarDepoimento() {
        // Cenário
        Egresso egresso_ex_a_salvar = Egresso.builder().nome("Testemunha")
                                                        .email("depoi@testmail.com")
                                                        .cpf("11111111111")
                                                        .url_foto("https//foto.dep.com")
                                                        .resumo("Egresso de teste par depoimento").build();

        Egresso egresso_ex_salvo = egressoRepository.save(egresso_ex_a_salvar);
   
        Assertions.assertNotNull(egresso_ex_salvo);
        Assertions.assertEquals(egresso_ex_a_salvar.getNome(), egresso_ex_salvo.getNome());
        Assertions.assertEquals(egresso_ex_a_salvar.getEmail(), egresso_ex_salvo.getEmail());
        Assertions.assertEquals(egresso_ex_a_salvar.getCpf(), egresso_ex_salvo.getCpf());
        Assertions.assertEquals(egresso_ex_a_salvar.getUrl_foto(), egresso_ex_salvo.getUrl_foto());
        Assertions.assertEquals(egresso_ex_a_salvar.getResumo(), egresso_ex_salvo.getResumo());

        Depoimento depoimento_a_salvar = Depoimento.builder().texto("Foi um período exemplar.")
                                                             .data(Date.valueOf(LocalDate.now()))
                                                             .egresso(egresso_ex_salvo).build();

        // Ação - operar no banco
        Depoimento salvo = repository.save(depoimento_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimento_a_salvar.getTexto(), salvo.getTexto());
        Assertions.assertEquals(depoimento_a_salvar.getData(), salvo.getData());
        Assertions.assertEquals(depoimento_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
    }

    @Test
    public void deveVerificarObterTodosDepoimentos(){
        // Ação - operar no banco
        List<Depoimento> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterDepoimentoSalvo(){
        // Cenário
        Egresso egresso_ex_a_salvar = Egresso.builder().nome("Testemunha")
                                                        .email("depoi@testmail.com")
                                                        .cpf("11111111111")
                                                        .url_foto("https//foto.dep.com")
                                                        .resumo("Egresso de teste par depoimento").build();

        Egresso egresso_ex_salvo = egressoRepository.save(egresso_ex_a_salvar);
   
        Assertions.assertNotNull(egresso_ex_salvo);
        Assertions.assertEquals(egresso_ex_a_salvar.getNome(), egresso_ex_salvo.getNome());
        Assertions.assertEquals(egresso_ex_a_salvar.getEmail(), egresso_ex_salvo.getEmail());
        Assertions.assertEquals(egresso_ex_a_salvar.getCpf(), egresso_ex_salvo.getCpf());
        Assertions.assertEquals(egresso_ex_a_salvar.getUrl_foto(), egresso_ex_salvo.getUrl_foto());
        Assertions.assertEquals(egresso_ex_a_salvar.getResumo(), egresso_ex_salvo.getResumo());

        Depoimento depoimento_a_salvar = Depoimento.builder().texto("Foi um período exemplar.")
                                                             .data(Date.valueOf(LocalDate.now()))
                                                             .egresso(egresso_ex_salvo).build();

        // Ação - operar no banco
        Depoimento salvo = repository.save(depoimento_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimento_a_salvar.getTexto(), salvo.getTexto());
        Assertions.assertEquals(depoimento_a_salvar.getData(), salvo.getData());
        Assertions.assertEquals(depoimento_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());

        Optional<Depoimento> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Depoimento q = query.get();
        Assertions.assertEquals(salvo.getTexto(), q.getTexto());
        Assertions.assertEquals(salvo.getData(), q.getData());
        Assertions.assertEquals(salvo.getEgresso().getId(), q.getEgresso().getId());
    }
    
    @Test
    public void deveVerificarRemoverDepoimentoSalvo(){
        // Cenário
        Egresso egresso_ex_a_salvar = Egresso.builder().nome("Testemunha")
                                                        .email("depoi@testmail.com")
                                                        .cpf("11111111111")
                                                        .url_foto("https//foto.dep.com")
                                                        .resumo("Egresso de teste par depoimento").build();

        Egresso egresso_ex_salvo = egressoRepository.save(egresso_ex_a_salvar);
   
        Assertions.assertNotNull(egresso_ex_salvo);
        Assertions.assertEquals(egresso_ex_a_salvar.getNome(), egresso_ex_salvo.getNome());
        Assertions.assertEquals(egresso_ex_a_salvar.getEmail(), egresso_ex_salvo.getEmail());
        Assertions.assertEquals(egresso_ex_a_salvar.getCpf(), egresso_ex_salvo.getCpf());
        Assertions.assertEquals(egresso_ex_a_salvar.getUrl_foto(), egresso_ex_salvo.getUrl_foto());
        Assertions.assertEquals(egresso_ex_a_salvar.getResumo(), egresso_ex_salvo.getResumo());

        Depoimento depoimento_a_salvar = Depoimento.builder().texto("Foi um período exemplar.")
                                                             .data(Date.valueOf(LocalDate.now()))
                                                             .egresso(egresso_ex_salvo).build();

        // Ação - operar no banco
        Depoimento salvo = repository.save(depoimento_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimento_a_salvar.getTexto(), salvo.getTexto());
        Assertions.assertEquals(depoimento_a_salvar.getData(), salvo.getData());
        Assertions.assertEquals(depoimento_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Depoimento> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
}