package com.egresso.egresso.model.repositories;

import java.util.List;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.entities.CursoEgressoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoEgressoRepository 
    extends JpaRepository<CursoEgresso, CursoEgressoKey> {
    public List<CursoEgresso> findAllByEgresso(Egresso egresso); 
    public List<CursoEgresso> findAllByCurso(Curso curso); 
}
