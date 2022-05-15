package com.egresso.egresso.model.repositories;

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
    public void deveSalvarCargos(){
        // Cenário
        Cargo cargo = Cargo.builder().nome("Gerente").descricao("Faz alguma coisa X.").build();

        // Ação - operar no banco
        Cargo salvo = repository.save(cargo);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo.getNome(), salvo.getNome());
        Assertions.assertEquals(cargo.getDescricao(), salvo.getDescricao());

    }
    
    // @Test
    // public void deveObterCargos(){
    //     // Ação - operar no banco
    //     Cargo salvo = repository.save(cargo);

    //     // Verificação - A ação ocorreu?
   
    //     Assertions.assertNotNull(salvo);
    //     Assertions.assertEquals(cargo.getNome(), salvo.getNome());

    // }
}