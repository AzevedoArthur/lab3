package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.FaixaSalario; // Interface

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FaixaSalarioRepositoryTest {
    @Autowired
    FaixaSalarioRepository repository;
    
    @Test
    public void deveSalvarFaixaSalario(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}