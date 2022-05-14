package com.egresso.egresso.model.repositories;

import org.junit.jupiter.api.Test;
import com.egresso.egresso.model.repositories.CargoRepository; // Interface
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CargoRepositoryTest{
    @Autowired
    Cargo repository;
    
    @Test
    public void deveSalvarCargos(){
        // Cenário
        Cargo cargo = Cargo.builder().nome("Gerente").descricao("Faz alguma coisa X.").build();

        // Ação - operar no banco
        Cargo salvo = repository.save(cargo);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo.getNome(), salvo.getNome());

    }
}