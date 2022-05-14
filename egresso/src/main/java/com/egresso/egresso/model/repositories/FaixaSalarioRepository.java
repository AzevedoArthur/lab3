package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.FaixaSalario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaixaSalarioRepository 
    extends JpaRepository<FaixaSalario, long> {
    
}
