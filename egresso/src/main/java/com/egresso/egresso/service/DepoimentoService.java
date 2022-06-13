package com.egresso.egresso.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.repositories.DepoimentoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class DepoimentoService {
    @Autowired
    DepoimentoRepository depoimentoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    public static List<String> errorMessages = List.of(
        // ERR-MSG-00
        "O depoimento informado é nulo.",
        // ERR-MSG-01
        "O depoimento deve possuir um egresso.",
        // ERR-MSG-02
        "O egresso do depoimento deve possuir um ID e constar no banco.",
        // ERR-MSG-03
        "O texto do depoimento deve ser informado (não pode ser nulo).",
        // ERR-MSG-04
        "O texto do depoimento deve ser informado (não pode ser vazio).",
        // ERR-MSG-05
        "A data do depoimento deve ser informada (não nula).",
        // ERR-MSG-06
        "A data do depoimento informada é inválida (data no futuro).",
        // ERR-MSG-07
        "A data do depoimento informada é inválida (data muito antiga).",
        // ERR-MSG-08
        "O depoimento a ser inserido já possui ID e consta no banco.",
        // ERR-MSG-09
        "O depoimento a ser inserido só deve possuir ID após inserção.",
        // ERR-MSG-10
        "O depoimento informado não possui ID.",
        // ERR-MSG-11
        "O depoimento informado não consta no banco.",
        // ERR-MSG-12
        "O egresso informado não deve ser nulo.",
        // ERR-MSG-13
        "O egresso informado deve possuir ID.",
        // ERR-MSG-14
        "O egresso informado não consta no banco."
    );
    
    private void verificarDepoimentoValido(Depoimento depoimento) {
        // Check Depoimento
        if (depoimento == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        
        // Check Egresso
        if (depoimento.getEgresso() == null) 
            throw new RegraNegocioRuntime(errorMessages.get(1));
        else if (depoimento.getEgresso().getId() == null || !egressoRepository.existsById(depoimento.getEgresso().getId())) 
            throw new RegraNegocioRuntime(errorMessages.get(2));
        //else
        //  egresso correto

        // Check Texto
        if (depoimento.getTexto() == null)
            throw new RegraNegocioRuntime(errorMessages.get(3));
        else if (depoimento.getTexto().isEmpty())
            throw new RegraNegocioRuntime(errorMessages.get(4));
        //else
        //  texto correto
         
        // Check Data
        if (depoimento.getData() == null)
            throw new RegraNegocioRuntime(errorMessages.get(5));
        else if (LocalDate.parse(depoimento.getData().toString()).isAfter(LocalDate.now()))
            throw new RegraNegocioRuntime(errorMessages.get(6));
        else if (LocalDate.parse(depoimento.getData().toString()).isBefore(LocalDate.parse("2022-01-01")))
            throw new RegraNegocioRuntime(errorMessages.get(7));
        //else
        //  data correta
    }
    private void verificarDepoimentoNovo(Depoimento depoimento) {
        // Check Depoimento
        if (depoimento == null) throw new RegraNegocioRuntime(errorMessages.get(0));

        // Check ID
        if (depoimento.getId() != null) {
            if (depoimentoRepository.existsById(depoimento.getId()))
                throw new RegraNegocioRuntime(errorMessages.get(8));
            else
                throw new RegraNegocioRuntime(errorMessages.get(9));
        }
    }
    private void verificarDepoimentoExistePorID(Long id) {
        // Check ID
        if (id == null) 
            throw new RegraNegocioRuntime(errorMessages.get(10));
        else if (!depoimentoRepository.existsById(id)) 
            throw new RegraNegocioRuntime(errorMessages.get(11));
    }
    
    private Depoimento salvar(Depoimento depoimento) {
        verificarDepoimentoValido(depoimento);
        return depoimentoRepository.save(depoimento); 
    }
    public Depoimento inserir(Depoimento depoimento) {
        verificarDepoimentoNovo(depoimento);
        return salvar(depoimento); 
    }
    public Depoimento editar(Depoimento depoimento) {
        verificarDepoimentoExistePorID(depoimento.getId());
        return salvar(depoimento); 
    }
    public void deletar(Long id) {
        verificarDepoimentoExistePorID(id);
        depoimentoRepository.deleteById(id);
    }
    public void deletar(Depoimento depoimento) {
        verificarDepoimentoExistePorID(depoimento.getId());
        depoimentoRepository.delete(depoimento);
    }
    private class DepoimentoDataComparator implements Comparator<Depoimento> {
        @Override
        public int compare(Depoimento o1, Depoimento o2) {
            if (o1.getData() == o2.getData()) 
                return 0;
            else if (LocalDate.parse(o1.getData().toString())
                     .isBefore(
                     LocalDate.parse(o2.getData().toString())
                    ))
                return -1;
            else
                return 1;
        }
    }
    public List<Depoimento> consultarDepoimentosOrdenadosPorRecente() {
        List<Depoimento> deps = new ArrayList<>(depoimentoRepository.findAll());
        deps.sort(new DepoimentoDataComparator());
        return deps;
    }
    public List<Depoimento> consultarDepoimentosPorEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRuntime(errorMessages.get(12));
        else if (egresso.getId() == null)
            throw new RegraNegocioRuntime(errorMessages.get(13));
        else if (!egressoRepository.existsById(egresso.getId()))
            throw new RegraNegocioRuntime(errorMessages.get(14));
        return depoimentoRepository.findAllByEgresso(egresso); 
    }
}
