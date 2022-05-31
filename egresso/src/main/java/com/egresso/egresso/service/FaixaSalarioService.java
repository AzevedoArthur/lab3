package com.egresso.egresso.service;

import com.egresso.egresso.model.repositories.FaixaSalarioRepository;

import java.util.List;

import javax.transaction.Transactional;

import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.repositories.EgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class FaixaSalarioService {
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    @Autowired
    EgressoRepository egressoRepository;
    
    public List<Egresso> consultarEgressosPorFaixaSalario(FaixaSalario faixaSalario) {
        // Prototipo
        return null; 
    }
    public int quantitativoEgressosPorFaixaSalario(FaixaSalario faixaSalario) {
        // Prototipo
        return consultarEgressosPorFaixaSalario(faixaSalario).size(); 
    }
    
}
