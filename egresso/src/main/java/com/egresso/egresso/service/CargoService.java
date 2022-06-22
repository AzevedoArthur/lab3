package com.egresso.egresso.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.ProfEgresso;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class CargoService {
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    ProfEgressoRepository profEgressoRepository;
    public static List<String> errorMessages = List.of(
        // ERR-MSG-00
        "O cargo informado é nulo.",
        // ERR-MSG-01
        "O cargo deve possuir nome (não deve ser nulo).",
        // ERR-MSG-02
        "O nome do cargo informado é vazio.",
        // ERR-MSG-03
        "O cargo possui ID que já consta no banco.",
        // ERR-MSG-04
        "O cargo só deve possuir ID após inserção no banco.",
        // ERR-MSG-05
        "O nome informado do cargo já existe no banco.",
        // ERR-MSG-06
        "O ID do cargo informada é nulo.",
        // ERR-MSG-07
        "Não foi encontrado cargo com o ID informado.",
        // ERR-MSG-08
        "O egresso informado não deve ser nulo.",
        // ERR-MSG-09
        "O egresso informado deve possuir ID.",
        // ERR-MSG-10
        "O egresso informado não consta no banco."
    );
    
    private void verificarCargoValido(Cargo cargo) {
        // Check Cargo
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));

        // Check Nome 
        if (cargo.getNome() == null)
            throw new RegraNegocioRuntime(errorMessages.get(1));
        else if (cargo.getNome().strip().isEmpty())
            throw new RegraNegocioRuntime(errorMessages.get(2));
        // else
        //  nome válido
    }
    private void verificarCargoNovoCampos(Cargo cargo) {
        // Check Cargo
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));

        // Check Nome
        if (cargo.getNome() != null) {
            if (cargoRepository.existsByNome(cargo.getNome())) {
                if (cargoRepository.findByNome(cargo.getNome()).get() != cargo)
                    throw new RegraNegocioRuntime(errorMessages.get(5));
            }
        } else {
            throw new RegraNegocioRuntime(errorMessages.get(1));
        }
    }
    private void verificarCargoExistePorId(Long id) {
        // Check ID
        if (id == null)
            throw new RegraNegocioRuntime(errorMessages.get(6));
        else if (!cargoRepository.existsById(id))
            throw new RegraNegocioRuntime(errorMessages.get(7));
    }
    private void verificarCargoNaoExistePorId(Long id) {
        // Check ID
        if (id != null) {
            if (cargoRepository.existsById(id))
                throw new RegraNegocioRuntime(errorMessages.get(3));
            else
                throw new RegraNegocioRuntime(errorMessages.get(4));
        }
    }
    
    private Cargo salvar(Cargo cargo) {
        verificarCargoValido(cargo);
        verificarCargoNovoCampos(cargo);
        return cargoRepository.save(cargo); 
    }
    public Cargo inserir(Cargo cargo) {
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        verificarCargoNaoExistePorId(cargo.getId());
        return salvar(cargo); 
    }
    public Cargo editar(Cargo cargo) {
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        verificarCargoExistePorId(cargo.getId());
        return salvar(cargo); 
    }
    public void deletar(Cargo cargo) {
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        verificarCargoExistePorId(cargo.getId());
        cargoRepository.delete(cargo);
    }
    public void deletar(Long id) {
        verificarCargoExistePorId(id);
        cargoRepository.deleteById(id);
    }
    public List<Cargo> consultarCargosPorEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRuntime(errorMessages.get(8));
        else if (egresso.getId() == null)
            throw new RegraNegocioRuntime(errorMessages.get(9));
        else if (!egressoRepository.existsById(egresso.getId()))
            throw new RegraNegocioRuntime(errorMessages.get(10));
        ArrayList<Cargo> cargos = new ArrayList<>();
        for (ProfEgresso relation : profEgressoRepository.findAllByEgresso(egresso)){
            cargos.add(relation.getCargo());
        }
        return List.copyOf(cargos); 
    }
    public List<Egresso> consultarEgressosPorCargo(Cargo cargo) {
        if (cargo == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        
        verificarCargoExistePorId(cargo.getId());
        
        ArrayList<Egresso> ocupantes = new ArrayList<>();
        for (ProfEgresso relation : profEgressoRepository.findAllByCargo(cargo)){
            ocupantes.add(relation.getEgresso());
        }
        
        return List.copyOf(ocupantes); 
    }
    public int quantitativoEgressosPorCargo(Cargo cargo) {
        // Prototipo
        return consultarEgressosPorCargo(cargo).size(); 
    }
}
