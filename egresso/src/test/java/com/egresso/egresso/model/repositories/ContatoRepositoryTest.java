package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.egresso.egresso.model.entities.Contato; // Interface
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
public class ContatoRepositoryTest{
    @Autowired
    ContatoRepository repository;
    @Autowired
    EgressoRepository egressoRepository;
    
    @Test
    public void deveVerificarSalvarContatoSemRelacoes() {
        // Cenário
        Contato contato_a_salvar = Contato.builder().nome("Empresa").url_logo("https://logo.com").build();

        // Ação - operar no banco
        Contato salvo = repository.save(contato_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contato_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(contato_a_salvar.getUrl_logo(), salvo.getUrl_logo());
    }
    @Test
    public void deveVerificarSalvarContatoComRelacoes() {
        // Cenário
        Egresso ex1 = Egresso.builder().nome("Contatador1")
                                        .email("contato@testmail.com")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.ctt.com")
                                        .resumo("Egresso de teste 1 para contato").build();
        Egresso ex2 = Egresso.builder().nome("Contatador2")
                                        .email("contato@testmail.com")
                                        .cpf("22222222222")
                                        .url_foto("https//foto.ctt.com")
                                        .resumo("Egresso de teste 2 para contato").build();
        Set<Egresso> egressos_a_salvar = Set.of(ex1, ex2);
        Set<Egresso> egressos_salvos = Set.of(egressoRepository.save(ex1), egressoRepository.save(ex2));

        Contato contato_a_salvar = Contato.builder().nome("Empresa").url_logo("https://logo.com").egressos(egressos_salvos).build();

        // Ação - operar no banco
        Contato salvo = repository.save(contato_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contato_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(contato_a_salvar.getUrl_logo(), salvo.getUrl_logo());
        Assertions.assertEquals(egressos_a_salvar, egressos_salvos);
        Assertions.assertEquals(contato_a_salvar.getEgressos(), salvo.getEgressos());
    }
    
    @Test
    public void deveVerificarObterTodosContatos(){
        // Ação - operar no banco
        List<Contato> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterContatoSalvo(){
        // Ação - operar no banco
        Contato contato_a_salvar = Contato.builder().nome("Empresa").url_logo("https:\\\\logo.com").build();

        // Ação - operar no banco
        Contato salvo = repository.save(contato_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contato_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(contato_a_salvar.getUrl_logo(), salvo.getUrl_logo());

        Optional<Contato> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Contato q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getUrl_logo(), q.getUrl_logo());
    }
    
    @Test
    public void deveVerificarRemoverContatoSalvoSemRelacoes(){
        // Ação - operar no banco
        Contato contato_a_salvar = Contato.builder().nome("Empresa").url_logo("https:\\\\logo.com").build();

        // Ação - operar no banco
        Contato salvo = repository.save(contato_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contato_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(contato_a_salvar.getUrl_logo(), salvo.getUrl_logo());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Contato> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
    
    @Test
    public void deveVerificarRemoverContatoSalvoComRelacoes(){
        // Cenário
        Egresso ex1 = Egresso.builder().nome("Contatador1")
                                        .email("contato@testmail.com")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.ctt.com")
                                        .resumo("Egresso de teste 1 para contato").build();
        Egresso ex2 = Egresso.builder().nome("Contatador2")
                                        .email("contato@testmail.com")
                                        .cpf("22222222222")
                                        .url_foto("https//foto.ctt.com")
                                        .resumo("Egresso de teste 2 para contato").build();
        Set<Egresso> egressos_a_salvar = Set.of(ex1, ex2);
        Set<Egresso> egressos_salvos = Set.of(egressoRepository.save(ex1), egressoRepository.save(ex2));

        Contato contato_a_salvar = Contato.builder().nome("Empresa").url_logo("https://logo.com").egressos(egressos_salvos).build();

        // Ação - operar no banco
        Contato salvo = repository.save(contato_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contato_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(contato_a_salvar.getUrl_logo(), salvo.getUrl_logo());
        Assertions.assertEquals(egressos_a_salvar, egressos_salvos);
        Assertions.assertEquals(contato_a_salvar.getEgressos(), salvo.getEgressos());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Contato> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
}
