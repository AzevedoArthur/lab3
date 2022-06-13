package com.egresso.egresso.model.repositories;

import java.util.List;

import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfEgressoRepository 
    extends JpaRepository<ProfEgresso, Long> {
    public List<ProfEgresso> findAllByEgresso(Egresso egresso);    
    public List<ProfEgresso> findAllByCargo(Cargo cargo);    
    public List<ProfEgresso> findAllByFaixaSalario(FaixaSalario faixaSalario);    
}
