package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.CursoEgressoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoEgressoRepositoryTest {
    @Autowired
    CursoEgresso repository;
    
    @Test
    public void deveSalvarCursoEgresso(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}