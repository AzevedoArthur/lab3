package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.egresso.egresso.model.entities.Contato;
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
public class EgressoRepositoryTest{
    @Autowired
    EgressoRepository repository;
    @Autowired
    ContatoRepository contatoRepository;
    
    @Test
    public void deveVerificarSalvarEgressoSemRelacoes() {
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());
    }
    
    @Test
    public void deveVerificarSalvarEgressoComRelacoes() {
        // Cenário

        Contato ex1 = Contato.builder().nome("Empresa1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Empresa2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);
        Set<Contato> contatos_salvos = Set.of(contatoRepository.save(ex1), contatoRepository.save(ex2));

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_salvos)
                                                    .build();
        
        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());
        Assertions.assertEquals(contatos_a_salvar, contatos_salvos);
        Assertions.assertEquals(egresso_a_salvar.getContatos(), salvo.getContatos());
    }
    
    @Test
    public void deveVerificarObterTodosEgressos(){
        // Ação - operar no banco
        List<Egresso> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterEgressoSalvo(){
        // Ação - operar no banco
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        Optional<Egresso> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Egresso q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getEmail(), q.getEmail());
        Assertions.assertEquals(salvo.getCpf(), q.getCpf());
        Assertions.assertEquals(salvo.getUrl_foto(), q.getUrl_foto());
        Assertions.assertEquals(salvo.getResumo(), q.getResumo());
    }
    
    @Test
    public void deveVerificarRemoverEgressoSalvoSemRelacoes(){
        // Ação - operar no banco
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Egresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
    @Test
    public void deveVerificarRemoverEgressoSalvoComRelacoes(){
        // Ação - operar no banco
        Contato ex1 = Contato.builder().nome("Empresa1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Empresa2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);
        Set<Contato> contatos_salvos = Set.of(contatoRepository.save(ex1), contatoRepository.save(ex2));

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_salvos)
                                                    .build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());
        Assertions.assertEquals(contatos_a_salvar, contatos_salvos);
        Assertions.assertEquals(egresso_a_salvar.getContatos(), salvo.getContatos());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Egresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
}