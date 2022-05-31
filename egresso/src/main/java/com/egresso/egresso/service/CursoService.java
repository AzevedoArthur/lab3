package com.egresso.egresso.service;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional  
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    
    public List<Egresso> consultarEgressosPorCurso(Curso curso) {
        // Prototipo
        return null; 
    }
    public int quantitativoEgressosPorCurso(Curso curso) {
        // Prototipo
        return consultarEgressosPorCurso(curso).size(); 
    }
}
