package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.EgressoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EgressoRepositoryTest {
    @Autowired
    Egresso repository;
    
    @Test
    public void deveSalvarEgresso(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}