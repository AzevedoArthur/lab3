package com.egresso.egresso.model.repositories;

import java.util.Optional;

import com.egresso.egresso.model.entities.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EgressoRepository 
    extends JpaRepository<Egresso, Long> {
    public Optional<Egresso> findByEmail(String email);    
    public boolean existsByEmail(String email);  
    public Optional<Egresso> findByCpf(String cpf);    
    public boolean existsByCpf(String cpf);   
}
