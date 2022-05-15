package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EgressoRepository 
    extends JpaRepository<Egresso, Long> {
    
}
