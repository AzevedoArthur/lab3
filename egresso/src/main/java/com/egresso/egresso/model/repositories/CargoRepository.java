package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository 
    extends JpaRepository<Cargo, Long> {
    
}
