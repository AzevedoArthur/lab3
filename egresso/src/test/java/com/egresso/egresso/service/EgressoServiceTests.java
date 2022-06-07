package com.egresso.egresso.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EgressoServiceTests {
    @Autowired
    EgressoService service;
    
    @Test
    public void deveSalvarEgresso() {
        // Prototipo
    }
    
}
