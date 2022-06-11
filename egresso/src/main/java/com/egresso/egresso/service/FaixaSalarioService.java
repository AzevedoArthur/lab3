package com.egresso.egresso.service;

import com.egresso.egresso.model.repositories.FaixaSalarioRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class FaixaSalarioService {
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    @Autowired
    ProfEgressoRepository profEgressoRepository;
    public static List<String> errorMessages = List.of(
        // ERR-MSG-00
        "Faixa de salário informada é nula.",
        // ERR-MSG-01
        "A faixa de salário para busca deve ter sido inserida no banco, possuindo ID.",
        // ERR-MSG-02
        "A faixa de salário para busca não foi encontrada no banco.",
        // ERR-MSG-03
        "A coleção de faixas de salário é nula."
    );
    
    public Set<Egresso> consultarEgressosPorFaixaSalario(FaixaSalario faixaSalario) {
        if (faixaSalario == null) {
            throw new RegraNegocioRuntime(errorMessages.get(0));
        } else if (faixaSalario.getId() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(1));
        } else if (!faixaSalarioRepository.existsById(faixaSalario.getId())) {
            throw new RegraNegocioRuntime(errorMessages.get(2));
        } 

        List<ProfEgresso> profissoes = profEgressoRepository.findAllByFaixaSalario(faixaSalario);
        
        HashSet<Egresso> egressos = new HashSet<>();
        for (ProfEgresso prof : profissoes) egressos.add(prof.getEgresso());
        
        return Set.copyOf(egressos);
    }
    public Set<Egresso> consultarEgressosPorFaixaSalario(Collection<FaixaSalario> faixasSalario) {
        if (faixasSalario == null) throw new RegraNegocioRuntime(errorMessages.get(3));
        HashSet<Egresso> egressos = new HashSet<>();
        for (FaixaSalario faixaSalario : faixasSalario) {
            egressos.addAll(consultarEgressosPorFaixaSalario(faixaSalario));
        }
        return Set.copyOf(egressos);
    }
    public int quantitativoEgressosPorFaixaSalario(FaixaSalario faixaSalario) {
        return consultarEgressosPorFaixaSalario(faixaSalario).size(); 
    }
    public int quantitativoEgressosPorFaixaSalario(Collection<FaixaSalario> faixasSalario) {
        return consultarEgressosPorFaixaSalario(faixasSalario).size(); 
    }
    
}
