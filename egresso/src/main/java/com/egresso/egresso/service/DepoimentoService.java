package com.egresso.egresso.service;


import java.util.List;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.repositories.DepoimentoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class DepoimentoService {
    @Autowired
    DepoimentoRepository depoimentoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    
    public Depoimento salvar(Depoimento depoimento) {
        // Prototipo
        return null; 
    }
    public Depoimento editar(Depoimento depoimento) {
        // Prototipo
        return null; 
    }
    public void deletar(Depoimento depoimento) {
        // Prototipo
    }
    public List<Depoimento> consultarDepoimentosOrdenadosPorRecente() {
        // Prototipo
        return null; 
    }
    public List<Depoimento> consultarDepoimentosPorEgresso(Egresso egresso) {
        // Prototipo
        return null; 
    }
}
