package com.egresso.egresso.service;

import java.util.List;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class CargoService {
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    
    public Cargo salvar(Cargo cargo) {
        // Prototipo
        return null; 
    }
    public Cargo editar(Cargo cargo) {
        // Prototipo
        return null; 
    }
    public void deletar(Cargo cargo) {
        // Prototipo
    }
    public List<Cargo> consultarCargosPorEgresso(Egresso egresso) {
        // Prototipo
        return null; 
    }
    public List<Egresso> consultarEgressosPorCargo(Cargo cargo) {
        // Prototipo
        return null; 
    }
    public int quantitativoEgressosPorCargo(Cargo cargo) {
        // Prototipo
        return consultarEgressosPorCargo(cargo).size(); 
    }
}
