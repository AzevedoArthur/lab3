package com.egresso.egresso.service;


import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Egresso;
// import com.egresso.egresso.model.entities.Cargo;
// import com.egresso.egresso.model.entities.Contato;
// import com.egresso.egresso.model.entities.Curso;
// import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.ContatoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class EgressoService {
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    
    public Egresso salvar(Egresso egresso) {
        // Prototipo
        return null; 
    }
    public Egresso editar(Egresso egresso) {
        // Prototipo
        return null; 
    }
    public void deletar(Egresso egresso) {
        // Prototipo
    }
    public Egresso consultar(long id) {
        // Prototipo
        return null; 
    }
}
