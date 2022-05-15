package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository 
    extends JpaRepository<Curso, Long> {
    
}
