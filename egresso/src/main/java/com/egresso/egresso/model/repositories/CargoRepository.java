package com.egresso.egresso.model.repositories;

import com.egresso.egresso.model.entities.Cargo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository 
    extends JpaRepository<Cargo, Long> {
    public Optional<Cargo> findByNome(String nome);    
    public boolean existsByNome(String nome); 
}
