package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.entities.CursoEgressoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoEgressoRepository 
    extends JpaRepository<CursoEgresso, CursoEgressoKey> {
    
}
