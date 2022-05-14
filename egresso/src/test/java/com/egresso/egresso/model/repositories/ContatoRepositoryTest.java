package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.ContatoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContatoRepository{
    @Autowired
    Contato repository;
    
    @Test
    public void deveSalvarContatos() {
        // Cenário
        
        // Ação - operar no banco
        
        // Verificação - A ação ocorreu?

    }
}