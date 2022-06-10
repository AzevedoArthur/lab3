package com.egresso.egresso.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;
import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Contato;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.ContatoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  
public class EgressoService {
    @Autowired
    private EgressoRepository egressoRepository;
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private FaixaSalarioRepository faixaSalarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public static Set<CharSequence> caracteresProibidosEmail = Set.of(
        " ", 
        "-", 
        ":", 
        "(", 
        ")", 
        ".."
    );
    public static List<String> errorMessages = List.of(
        // ERR-MSG-00
        "Um egresso nulo é inválido.",
        // ERR-MSG-01
        "Este egresso possui ID e já consta no banco.",
        // ERR-MSG-02
        "Um novo egresso só deve ter ID após inserção.",
        // ERR-MSG-03
        "O egresso necessita de um email.",
        // ERR-MSG-04
        "O email fornecido já está em uso por outro egresso cadastrado.",
        // ERR-MSG-05
        "O egresso necessita de um CPF.",
        // ERR-MSG-06
        "O CPF fornecido já está em uso por outro egresso cadastrado.",
        // ERR-MSG-07
        "O egresso necessita de um nome.",
        // ERR-MSG-08
        "O nome de um egresso não deve ser vazio.",
        // ERR-MSG-09
        "O email de um egresso não deve ser vazio.",
        // ERR-MSG-10
        "O email do egresso não pode conter caracteres especiais.",
        // ERR-MSG-11
        "CPF de tamanho inválido informado.",
        // ERR-MSG-12
        "O CPF fornecido deve conter somente números.",
        // ERR-MSG-13
        "Os cursos do egresso devem já ter sido inseridos no banco e possuir ID.",
        // ERR-MSG-14
        "Os cargos do egresso devem já ter sido inseridos no banco e possuir ID.",
        // ERR-MSG-15
        "A faixa salarial do egresso deve já ter sido inserida no banco e possuir ID.",
        // ERR-MSG-16
        "O ID do egresso não foi informado ou é nulo.",
        // ERR-MSG-17
        "Não foi encontrado egresso com o ID informado.",
        // ERR-MSG-18
        "O egresso a ser salvo possui ligação curso-egresso de outro egresso.",
        // ERR-MSG-19
        "O egresso a ser salvo possui ligação profissão-egresso de outro egresso.",
        // ERR-MSG-20
        "O egresso a ser salvo possui depoimento de outro egresso."
    );
    
    private void verificarEgressoNovo(Egresso egresso) {
        // Check Egresso
        if (egresso == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        
        // Check ID
        if (egresso.getId() != null) {
            if (egressoRepository.existsById(egresso.getId()))
                throw new RegraNegocioRuntime(errorMessages.get(1));
            else 
                throw new RegraNegocioRuntime(errorMessages.get(2));
        }
        
        // Não é necessário checar nome

        // Check Email
        if (egresso.getEmail() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(3));
        } else if (egressoRepository.existsByEmail(egresso.getEmail())) {
            throw new RegraNegocioRuntime(errorMessages.get(4));
        }

        // Check CPF
        if (egresso.getCpf() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(5));
        } else if (egressoRepository.existsByCpf(egresso.getCpf())) {
            throw new RegraNegocioRuntime(errorMessages.get(6));
        }

        // Não é necessário checar Resumo, URL foto e Contatos
    }
    
    private void verificarEgressoValido(Egresso egresso) {
        // Check Egresso
        if (egresso == null) throw new RegraNegocioRuntime(errorMessages.get(0));
        
        // Check Nome
        if (egresso.getNome() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(7));
        } else if (egresso.getNome().strip().isEmpty()) {
            throw new RegraNegocioRuntime(errorMessages.get(8));
        }
        egresso.setNome(egresso.getNome().strip());

        // Check Email
        if (egresso.getEmail() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(3));
        } else if (egresso.getEmail().isEmpty()) {
            throw new RegraNegocioRuntime(errorMessages.get(9));
        } else { 
            for (CharSequence charSequence : caracteresProibidosEmail)
                if (egresso.getEmail().contains(charSequence))
                    throw new RegraNegocioRuntime(errorMessages.get(10));
        }
        // Check CPF
        if (egresso.getCpf() == null) {
            throw new RegraNegocioRuntime(errorMessages.get(5));
        } else if (egresso.getCpf().length() != 11) {
            throw new RegraNegocioRuntime(errorMessages.get(11));
        } else if (!Pattern.matches("[0-9]+", egresso.getCpf())){ 
            throw new RegraNegocioRuntime(errorMessages.get(12));
        }

        // Não é necessário checar Resumo, URL foto

        // Check Contatos
        if (egresso.getContatos() != null) {
            for (Contato contato : egresso.getContatos()) {
                Set<Egresso> e = contato.getEgressos();
                if (e == null)
                    contato.setEgressos(new HashSet<>(Set.of(egresso)));
                else if (!e.contains(egresso)) {
                    HashSet<Egresso> novos_e = new HashSet<>(e);
                    novos_e.add(egresso);
                    contato.setEgressos(novos_e);
                }
                // else
                    // O egresso da ligação é igual ao egresso a ser salvo. Ideal.
                if (contato.getId() == null) contatoRepository.save(contato);
            }
        }

        // Check Depoimentos
        if (egresso.getDepoimentos() != null) {
            for (Depoimento depoimento : egresso.getDepoimentos()) {
                Egresso e = depoimento.getEgresso();
                if (e == null)
                    depoimento.setEgresso(egresso);
                else if (e != egresso)
                    throw new RegraNegocioRuntime(errorMessages.get(20));
                // else
                    // O egresso da ligação é igual ao egresso a ser salvo. Ideal.
            }
        }

        // Check Cursos
        if (egresso.getCursos() != null) {
            for (CursoEgresso cursoEgresso : egresso.getCursos()) {
                Egresso e = cursoEgresso.getEgresso();
                if (e == null)
                    cursoEgresso.setEgresso(egresso);
                else if (e != egresso)
                    throw new RegraNegocioRuntime(errorMessages.get(18));
                // else
                    // O egresso da ligação é igual ao egresso a ser salvo. Ideal.
                Curso curso = cursoEgresso.getCurso();
                if (curso == null || curso.getId() == null || !cursoRepository.existsById(curso.getId()))
                    throw new RegraNegocioRuntime(errorMessages.get(13));
            }
        }

        // Check Profissões
        if (egresso.getProfissoes() != null) {
            for (ProfEgresso profissao : egresso.getProfissoes()) {
                Egresso e = profissao.getEgresso();
                if (e == null)
                    profissao.setEgresso(egresso);
                else if (e != egresso)
                    throw new RegraNegocioRuntime(errorMessages.get(19));
                // else
                    // O egresso da ligação é igual ao egresso a ser salvo. Ideal.
                Cargo cargo = profissao.getCargo();
                if (cargo == null || cargo.getId() == null || !cargoRepository.existsById(cargo.getId()))
                    throw new RegraNegocioRuntime(errorMessages.get(14));
                FaixaSalario faixaSalario = profissao.getFaixa_salario();
                if (faixaSalario == null || faixaSalario.getId() == null || !faixaSalarioRepository.existsById(faixaSalario.getId()))
                    throw new RegraNegocioRuntime(errorMessages.get(15));
            }
        }
    }
    
    private void verificarEgressoExistePorID(Long id) {
        if (id == null) {
            throw new RegraNegocioRuntime(errorMessages.get(16));
        } else if (!egressoRepository.existsById(id)) {
            throw new RegraNegocioRuntime(errorMessages.get(17));
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
        // Depoimentos e Ligações (com Profissões, Cursos e Contatos) são deletados automaticamente.
        egressoRepository.delete(egresso);
    }
    public void deletar(Long id) {
        Egresso toBeDeleted = consultarEgresso(id);
        deletar(toBeDeleted);
    }
}
