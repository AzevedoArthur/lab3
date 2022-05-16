package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.Cargo; // Interface

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
public class CargoRepositoryTest{
    @Autowired
    CargoRepository repository;
    
    @Test
    public void deveVerificarSalvarCargos() {
        // Cenário
        Cargo cargo_a_salvar = Cargo.builder().nome("Gerente").descricao("Faz alguma coisa X.").build();

        // Ação - operar no banco
        Cargo salvo = repository.save(cargo_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(cargo_a_salvar.getDescricao(), salvo.getDescricao());
    }
    
    @Test
    public void deveVerificarObterTodosCargos(){
        // Ação - operar no banco
        List<Cargo> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterCargoSalvo(){
        // Ação - operar no banco
        Cargo cargo_a_salvar = Cargo.builder().nome("Gerente").descricao("Faz alguma coisa X.").build();

        // Ação - operar no banco
        Cargo salvo = repository.save(cargo_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(cargo_a_salvar.getDescricao(), salvo.getDescricao());

        Optional<Cargo> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Cargo q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getDescricao(), q.getDescricao());
    }
    
    @Test
    public void deveVerificarRemoverCargoSalvo(){
        // Ação - operar no banco
        Cargo cargo_a_salvar = Cargo.builder().nome("Gerente").descricao("Faz alguma coisa X.").build();

        // Ação - operar no banco
        Cargo salvo = repository.save(cargo_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(cargo_a_salvar.getDescricao(), salvo.getDescricao());

        repository.deleteById(salvo.getId());
        Optional<Cargo> query = repository.findById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Assertions.assertTrue(query.isEmpty());
    }
}