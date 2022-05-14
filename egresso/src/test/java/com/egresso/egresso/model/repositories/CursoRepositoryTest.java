package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.CursoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoRepositoryTest {
    @Autowired
    Curso repository;
    
    @Test
    public void deveSalvarCurso(){
        // Cenário

        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?
   
    }
}