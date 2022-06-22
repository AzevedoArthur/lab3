package com.egresso.egresso.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.repositories.CursoEgressoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    CursoEgressoRepository cursoEgressoRepository;
    
    public static List<String> errorMessages = List.of(
        // ERR-MSG-00
        "O curso informado é nulo.",
        // ERR-MSG-01
        "O curso deve possuir nome (não deve ser nulo).",
        // ERR-MSG-02
        "O nome do curso informado é vazio.",
        // ERR-MSG-03
        "O curso já possui ID que já consta no banco.",
        // ERR-MSG-04
        "O curso só deve possuir ID após inserção no banco.",
        // ERR-MSG-05
        "O nome informado do curso já existe no banco.",
        // ERR-MSG-06
        "O ID do curso informada é nulo.",
        // ERR-MSG-07
        "Não foi encontrado curso com o ID informado."//,
        // ERR-MSG-08
        //"O egresso informado é nulo.",
        // ERR-MSG-09
        //"egresso.getId() == null",
        // ERR-MSG-10
        //"Não foi encontrado egresso com o ID informado."
    );
    

    private void verificarCursoValido(Curso curso) {
        // Check Curso
        if (curso == null) throw new RegraNegocioRuntime(errorMessages.get(0));

        // Check Nome 
        if (curso.getNome() == null)
            throw new RegraNegocioRuntime(errorMessages.get(1));
        else if (curso.getNome().strip().isEmpty())
            throw new RegraNegocioRuntime(errorMessages.get(2));
    
    }

    private void verificarCursoExistePorId(Long id) {
        // Check ID
        if (id == null)
            throw new RegraNegocioRuntime(errorMessages.get(6));
        else if (cursoRepository.existsById(id))
            throw new RegraNegocioRuntime(errorMessages.get(7));
    }
    private void verificarCursoNaoExistePorId(Long id) {
        // Check ID
        if (id != null) {
            if (cursoRepository.existsById(id))
                throw new RegraNegocioRuntime(errorMessages.get(3));
            else
                throw new RegraNegocioRuntime(errorMessages.get(4));
        }
    }
    
    private Curso salvar(Curso curso) {
        verificarCursoValido(curso);
        return cursoRepository.save(curso); 
    }

    public Curso inserir(Curso curso) {
        if (curso == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        verificarCursoNaoExistePorId(curso.getId());
        return salvar(curso); 
    }

    public Curso editar(Curso curso) {
        if (curso == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        verificarCursoExistePorId(curso.getId());
        return salvar(curso); 
    }

    public void deletar(Curso curso) {
        verificarCursoExistePorId(curso.getId());
        cursoRepository.delete(curso);
    }
    public void deletar(Long id) {
        verificarCursoExistePorId(id);
        cursoRepository.deleteById(id);
    }

    /* 
    public Curso consultarCursoDoEgresso(Egresso egresso) {
        if (egresso == null)
        throw new RegraNegocioRuntime(errorMessages.get(8));
        else if (egresso.getId() == null)
        throw new RegraNegocioRuntime(errorMessages.get(9));
        else if (!egressoRepository.existsById(egresso.getId()))
        throw new RegraNegocioRuntime(errorMessages.get(10));
        //// Terminar protótipo
        return null // deve retornar um objeto 'Curso' 
    }
    */

    public List<Egresso> consultarEgressosPorCurso(Curso curso) {
        if (curso == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        
        verificarCursoExistePorId(curso.getId());
        
        ArrayList<Egresso> egressos = new ArrayList<>();
        for (CursoEgresso relation : cursoEgressoRepository.findAllByCurso(curso)){
            egressos.add(relation.getEgresso());
        }

        return List.copyOf(egressos); 
    }

    public int qtdEgressosPorCurso(Curso curso) {
        if (curso != null){
            return consultarEgressosPorCurso(curso).size();
        }
        else throw new RegraNegocioRuntime(errorMessages.get(0));
    }

}
