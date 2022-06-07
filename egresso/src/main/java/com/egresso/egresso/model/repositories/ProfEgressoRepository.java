package com.egresso.egresso.model.repositories;

import java.util.List;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.ProfEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfEgressoRepository 
    extends JpaRepository<ProfEgresso, Long> {
    public List<ProfEgresso> findAllByEgresso(Egresso egresso);    
}
