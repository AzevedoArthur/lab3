package com.egresso.egresso.model.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.ProfEgresso; // Interface
import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;

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
public class ProfEgressoRepositoryTest{
    @Autowired
    ProfEgressoRepository repository;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    @Autowired
    CargoRepository CargoRepository;
    
    @Test
    public void deveVerificarSalvarProfEgresso() {
        // Cenário

        Egresso egresso_ex_salvo = egressoRepository.save(Egresso.builder().nome("Professor")
                                                                 .email("professor@testmail.com")
                                                                 .cpf("11111111111")
                                                                 .url_foto("https//foto.prof.com")
                                                                 .resumo("Egresso de teste para ProfEgresso").build());                 
        Assertions.assertNotNull(egresso_ex_salvo);

        Cargo cargo_ex_salvo = CargoRepository.save(Cargo.builder().nome("Professor Egresso")
                                                                   .descricao("Cargo de teste para ProfEgresso").build());                                 
        Assertions.assertNotNull(cargo_ex_salvo);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build());                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        ProfEgresso profEgresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_ex_salvo)
                                                                .cargo(cargo_ex_salvo)
                                                                .faixa_salario(faixaSalario_ex_salvo).build();

        // Ação - operar no banco
        ProfEgresso salvo = repository.save(profEgresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(profEgresso_a_salvar.getEmpresa(), salvo.getEmpresa());
        Assertions.assertEquals(profEgresso_a_salvar.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(profEgresso_a_salvar.getData_registro(), salvo.getData_registro());
        Assertions.assertEquals(profEgresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getCargo().getId(), salvo.getCargo().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getFaixa_salario().getId(), salvo.getFaixa_salario().getId());
    }

    @Test
    public void deveVerificarObterTodosProfEgressos(){
        // Ação - operar no banco
        List<ProfEgresso> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterProfEgressoSalvo(){

        Egresso egresso_ex_salvo = egressoRepository.save(Egresso.builder().nome("Professor")
                                                                 .email("professor@testmail.com")
                                                                 .cpf("11111111111")
                                                                 .url_foto("https//foto.prof.com")
                                                                 .resumo("Egresso de teste para ProfEgresso").build());                 
        Assertions.assertNotNull(egresso_ex_salvo);

        Cargo cargo_ex_salvo = CargoRepository.save(Cargo.builder().nome("Professor Egresso")
                                                                   .descricao("Cargo de teste para ProfEgresso").build());                                 
        Assertions.assertNotNull(cargo_ex_salvo);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build());                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        ProfEgresso profEgresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_ex_salvo)
                                                                .cargo(cargo_ex_salvo)
                                                                .faixa_salario(faixaSalario_ex_salvo).build();

        // Ação - operar no banco
        ProfEgresso salvo = repository.save(profEgresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(profEgresso_a_salvar.getEmpresa(), salvo.getEmpresa());
        Assertions.assertEquals(profEgresso_a_salvar.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(profEgresso_a_salvar.getData_registro(), salvo.getData_registro());
        Assertions.assertEquals(profEgresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getCargo().getId(), salvo.getCargo().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getFaixa_salario().getId(), salvo.getFaixa_salario().getId());

        Optional<ProfEgresso> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        ProfEgresso q = query.get();
        Assertions.assertEquals(salvo.getEmpresa(), q.getEmpresa());
        Assertions.assertEquals(salvo.getDescricao(), q.getDescricao());
        Assertions.assertEquals(salvo.getData_registro(), q.getData_registro());
        Assertions.assertEquals(salvo.getEgresso().getId(), q.getEgresso().getId());
        Assertions.assertEquals(salvo.getCargo().getId(), q.getCargo().getId());
        Assertions.assertEquals(salvo.getFaixa_salario().getId(), q.getFaixa_salario().getId());
    }
    
    @Test
    public void deveVerificarRemoverProfEgressoSalvo(){
        // Cenário

        Egresso egresso_ex_salvo = egressoRepository.save(Egresso.builder().nome("Professor")
                                                            .email("professor@testmail.com")
                                                            .cpf("11111111111")
                                                            .url_foto("https//foto.prof.com")
                                                            .resumo("Egresso de teste para ProfEgresso").build());                 
        Assertions.assertNotNull(egresso_ex_salvo);

        Cargo cargo_ex_salvo = CargoRepository.save(Cargo.builder().nome("Professor Egresso")
                                                    .descricao("Cargo de teste para ProfEgresso").build());                                 
        Assertions.assertNotNull(cargo_ex_salvo);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build());                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        ProfEgresso profEgresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
            .descricao("Exemplo")
            .data_registro(Date.valueOf(LocalDate.now()))
            .egresso(egresso_ex_salvo)
            .cargo(cargo_ex_salvo)
            .faixa_salario(faixaSalario_ex_salvo).build();

        // Ação - operar no banco
        ProfEgresso salvo = repository.save(profEgresso_a_salvar);

        // Verificação - A ação ocorreu?

        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(profEgresso_a_salvar.getEmpresa(), salvo.getEmpresa());
        Assertions.assertEquals(profEgresso_a_salvar.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(profEgresso_a_salvar.getData_registro(), salvo.getData_registro());
        Assertions.assertEquals(profEgresso_a_salvar.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getCargo().getId(), salvo.getCargo().getId());
        Assertions.assertEquals(profEgresso_a_salvar.getFaixa_salario().getId(), salvo.getFaixa_salario().getId());

        // Ação - operar no banco
        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<ProfEgresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
}