package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Egresso; // Interface

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EgressoRepositoryTest {
    @Autowired
    EgressoRepository repository;
    
    @Test
    public void deveSalvarEgresso(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}