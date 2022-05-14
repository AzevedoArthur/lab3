package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.ProfEgressoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfEgressoRepositoryTest {
    @Autowired
    ProfEgresso repository;
    
    @Test
    public void deveSalvarProfEgresso(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}