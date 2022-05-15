package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoimentoRepository 
    extends JpaRepository<Depoimento, Long> {
    
}
