package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.ProfEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfEgressoRepository 
    extends JpaRepository<ProfEgresso, Long> {
    
}
