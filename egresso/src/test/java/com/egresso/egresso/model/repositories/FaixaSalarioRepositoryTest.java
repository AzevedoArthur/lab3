package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.FaixaSalario; // Interface

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
public class FaixaSalarioRepositoryTest{
    @Autowired
    FaixaSalarioRepository repository;
    
    @Test
    public void deveVerificarSalvarFaixaSalario() {
        // Cenário
        FaixaSalario faixa_salario_a_salvar = FaixaSalario.builder().descricao("10 salarios mínimos").build();

        // Ação - operar no banco
        FaixaSalario salvo = repository.save(faixa_salario_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(faixa_salario_a_salvar.getDescricao(), salvo.getDescricao());
    }
    
    @Test
    public void deveVerificarObterTodosFaixaSalarios(){
        // Ação - operar no banco
        List<FaixaSalario> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterFaixaSalarioSalvo(){
        // Ação - operar no banco
        FaixaSalario faixa_salario_a_salvar = FaixaSalario.builder().descricao("10 salarios mínimos").build();

        // Ação - operar no banco
        FaixaSalario salvo = repository.save(faixa_salario_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(faixa_salario_a_salvar.getDescricao(), salvo.getDescricao());

        Optional<FaixaSalario> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        FaixaSalario q = query.get();
        Assertions.assertEquals(salvo.getDescricao(), q.getDescricao());
    }
    
    @Test
    public void deveVerificarRemoverFaixaSalarioSalvo(){
        // Ação - operar no banco
        FaixaSalario faixa_salario_a_salvar = FaixaSalario.builder().descricao("10 salarios mínimos").build();

        // Ação - operar no banco
        FaixaSalario salvo = repository.save(faixa_salario_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(faixa_salario_a_salvar.getDescricao(), salvo.getDescricao());

        repository.deleteById(salvo.getId());
        Optional<FaixaSalario> query = repository.findById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Assertions.assertTrue(query.isEmpty());
    }
}