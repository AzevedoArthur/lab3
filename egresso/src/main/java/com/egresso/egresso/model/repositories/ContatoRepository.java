package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository 
    extends JpaRepository<Contato, Long> {
    
}
