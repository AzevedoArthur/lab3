package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Egresso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoimentoRepository 
    extends JpaRepository<Depoimento, Long> {
    public List<Depoimento> findAllByEgresso(Egresso egresso); 
}
