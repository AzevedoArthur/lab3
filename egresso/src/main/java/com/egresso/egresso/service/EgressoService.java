package com.egresso.egresso.service;


import java.util.regex.Pattern;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;
import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Contato;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
// import com.egresso.egresso.model.entities.Cargo;
// import com.egresso.egresso.model.entities.Curso;
// import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.ContatoRepository;
import com.egresso.egresso.model.repositories.CursoEgressoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;
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
    ProfEgressoRepository profEgressoRepository;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    @Autowired
    CursoEgressoRepository cursoEgressoRepository;
    @Autowired
    CursoRepository cursoRepository;
    
    private void verificarEgressoNovo(Egresso egresso) {
        // Check Egresso
        if (egresso == null) throw new RegraNegocioRuntime("Um egresso nulo é inválido.");
        
        // Check ID
        if (egresso.getId() != null) {
            if (egressoRepository.existsById(egresso.getId()))
                throw new RegraNegocioRuntime("Este egresso possui ID e já consta no banco.");
            else 
                throw new RegraNegocioRuntime("Um novo egresso só deve ter ID após inserção.");
        }
        
        // Não é necessário checar nome

        // Check Email
        if (egresso.getEmail() == null) {
            throw new RegraNegocioRuntime("O egresso necessita de um email.");
        } else if (egressoRepository.existsByEmail(egresso.getEmail())) {
            throw new RegraNegocioRuntime("O email fornecido já está em uso por outro egresso cadastrado.");
        }

        // Check CPF
        if (egresso.getCpf() == null) {
            throw new RegraNegocioRuntime("O egresso necessita de um CPF.");
        } else if (egressoRepository.existsByCpf(egresso.getCpf())) {
            throw new RegraNegocioRuntime("O CPF fornecido já está em uso por outro egresso cadastrado.");
        }

        // Não é necessário checar Resumo, URL foto e Contatos
    }
    
    private void verificarEgressoValido(Egresso egresso) {
        // Check Egresso
        if (egresso == null) throw new RegraNegocioRuntime("Um egresso nulo é inválido.");
        
        // Check Nome
        if (egresso.getNome() == null) {
            throw new RegraNegocioRuntime("O egresso necessita de um nome.");
        } else if (egresso.getNome().strip().length() == 0) {
            throw new RegraNegocioRuntime("O nome de um egresso não deve ser vazio.");
        }
        egresso.setNome(egresso.getNome().strip());

        // Check Email
        if (egresso.getEmail() == null) {
            throw new RegraNegocioRuntime("O egresso necessita de um email.");
        } else if (egresso.getEmail().isEmpty()) {
            throw new RegraNegocioRuntime("O email de um egresso não deve ser vazio.");
        } else { 
            CharSequence[] especiais = new CharSequence[] {" ", "-", ":", "(", ")", ".."};
            for (CharSequence charSequence : especiais)
                if (egresso.getEmail().contains(charSequence))
                    throw new RegraNegocioRuntime("O email do egresso não pode conter caracteres epeciais (" + charSequence + ").");
        }
        // Check CPF
        if (egresso.getCpf() == null) {
            throw new RegraNegocioRuntime("O egresso necessita de um CPF.");
        } else if (egresso.getCpf().length() != 11) {
            throw new RegraNegocioRuntime("O CPF de tamanho inválido informado.");
        } else if (!Pattern.matches("[0-9]+", egresso.getCpf())){ 
            throw new RegraNegocioRuntime("O CPF fornecido deve conter somente números.");
        }

        // Não é necessário checar Resumo e URL foto

        // Check Contatos
        for (Contato contato : egresso.getContatos()) {
            if (contato.getId() == null | !contatoRepository.existsById(contato.getId()))
                throw new RegraNegocioRuntime("Os contatos do egresso devem já ter sido inseridos no banco e possuir ID.");
        }

        // Check Cursos
        for (CursoEgresso cursoEgresso : egresso.getCursos()) {
            Curso curso = cursoEgresso.getCurso();
            if (curso == null | !cursoRepository.existsById(curso.getId()))
                throw new RegraNegocioRuntime("Os cursos do egresso devem já ter sido inseridos no banco e possuir ID.");
        }

        // Check Profissões
        for (ProfEgresso profissao : egresso.getProfissoes()) {
            Cargo cargo = profissao.getCargo();
            if (cargo == null | !cargoRepository.existsById(cargo.getId()))
                throw new RegraNegocioRuntime("Os cargos do egresso devem já ter sido inseridos no banco e possuir ID.");
            FaixaSalario faixaSalario = profissao.getFaixa_salario();
            if (faixaSalario == null | !faixaSalarioRepository.existsById(cargo.getId()))
                throw new RegraNegocioRuntime("A faixa salarial do egresso devem já ter sido inserida no banco e possuir ID.");
        }

    }
    
    private void verificarEgressoExistePorID(Long id) {
        if (id == null) {
            throw new RegraNegocioRuntime("O egresso possui não possui ID.");
        } else if (!egressoRepository.existsById(id)) {
            throw new RegraNegocioRuntime("O ID de egresso é inválido.");
        }
    }

    private Egresso salvar(Egresso egresso) {
        verificarEgressoValido(egresso);
        return egressoRepository.save(egresso);
    }
    public Egresso inserir(Egresso egresso) {
        verificarEgressoNovo(egresso);
        return salvar(egresso);
    }
    public Egresso editar(Egresso egresso) {
        verificarEgressoExistePorID(egresso.getId());
        return salvar(egresso);
    }
    public Egresso consultarEgresso(Long id) {
        verificarEgressoExistePorID(id);
        return egressoRepository.findById(id).get();
    }
    public void deletar(Egresso egresso) {
        verificarEgressoExistePorID(egresso.getId());
        // Profissões, Cursos e Depoimentos são deletados automaticamente
        egressoRepository.delete(egresso);
    }
    public void deletar(Long id) {
        Egresso toBeDeleted = consultarEgresso(id);
        deletar(toBeDeleted);
    }
}
