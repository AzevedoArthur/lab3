package com.egresso.egresso.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.sql.Date;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Contato;
import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.entities.Egresso; // Interface
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;

import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.ContatoRepository;
import com.egresso.egresso.model.repositories.DepoimentoRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;
import com.egresso.egresso.model.repositories.CursoEgressoRepository;
import com.egresso.egresso.model.repositories.CursoRepository;

import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EgressoServiceTest {
    @Autowired
    EgressoService egressoService;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    DepoimentoRepository depoimentoRepository;
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
    
    private Egresso getEgressoValido() {
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = new HashSet<>(Set.of(ex1, ex2));

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = new HashSet<>(Set.of(d1, d2));
        
        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(new HashSet<>(Set.of(curso_egresso_a_salvar)));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(new HashSet<>(Set.of(prof_egresso_a_salvar)));

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);
        Assertions.assertNotNull(cargo_salvo);   

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);    
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);

        return egresso_a_salvar;
    }
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveInserirEgressoValido() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação
            () -> egressoService.inserir(egresso_a_salvar)
        );
    }
    @Test
    public void deveEditarCamposDeEgressoInserido() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso salvo = egressoService.inserir(egresso_a_salvar);

        // Ação
        Egresso consulta = egressoService.consultarEgresso(salvo.getId());
        consulta.setNome("Joana");
        consulta.setEmail("joana.silva@testmail.com");
        consulta.setUrl_foto("https//foto.joana.com");
        consulta.setResumo("Egresso de teste editado");
        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação
            () -> egressoService.editar(consulta)
        );
    }
    @Test
    public void deveEditarRelacoesDeEgressoInserido() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso salvo = egressoService.inserir(egresso_a_salvar);
        
        Set<Contato> contatos = new HashSet<>(salvo.getContatos());
        contatos.add(Contato.builder().nome("Contato3Egresso")
                                        .url_logo("https:\\\\logo3.com").build());
        salvo.setContatos(contatos);

        Set<Depoimento> depoimentos = new HashSet<>(salvo.getDepoimentos());
        depoimentos.add(Depoimento.builder().texto("Massa3.")
                                                        .egresso(salvo)
                                                        .data(Date.valueOf(LocalDate.now())).build());
        salvo.setDepoimentos(depoimentos);

        Curso curso_a_salvar = Curso.builder().nome("Mestrado Egressado")
                                                .nivel("Mestrado").build();
        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(salvo)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now().plusDays(2))).build();
        HashSet<CursoEgresso> cursos = new HashSet<>(salvo.getCursos());
        cursos.add(curso_egresso_a_salvar);
        salvo.setCursos(cursos);

        Cargo cargo_a_salvar = Cargo.builder().nome("Coordenador Egresso")
                                                .descricao("Cargo 2 de teste para ProfEgresso").build();   
        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste 2 para ProfEgresso").build(); 
        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(salvo)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();
        HashSet<ProfEgresso> profissoes = new HashSet<>(salvo.getProfissoes());
        profissoes.add(prof_egresso_a_salvar);
        salvo.setProfissoes(profissoes);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);
        Assertions.assertNotNull(cargo_salvo);   
        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);    
        Assertions.assertNotNull(faixaSalario_ex_salvo);
        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);

        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação
            () -> egressoService.editar(salvo)
        );
    }
    @Test
    public void deveConsultarEgressoInserido() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso salvo = egressoService.inserir(egresso_a_salvar);
        Assertions.assertNotNull(salvo);   
        // Ação
        Egresso consulta = egressoService.consultarEgresso(salvo.getId());
        // Teste
        Assertions.assertNotNull(consulta);
        Assertions.assertEquals(salvo, consulta);
    }
    @Test
    public void deveDeletarEgressoInseridoUsandoEgresso() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso salvo = egressoService.inserir(egresso_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> egressoService.deletar(salvo)
        );
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class,
                                () -> egressoService.consultarEgresso(id), 
                                EgressoService.errorMessages.get(17));
    }
    @Test
    public void deveDeletarEgressoInseridoUsandoId() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso salvo = egressoService.inserir(egresso_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> egressoService.deletar(id)
        );
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class,
                                () -> egressoService.consultarEgresso(id), 
                                EgressoService.errorMessages.get(17));
    }
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//  INSERÇÃO: TESTES DE RESTRIÇÃO - COMEÇO                                                     //
//=============================================================================================//
    // Testes de verificarEgressoNovo
    @Test
    public void deveGerarErroAoTentarInserirEgressoNulo() {
        // Cenário
        Egresso e = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e), 
                                EgressoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComIdInexistente() {
        // Cenário
        Egresso e = getEgressoValido();
        e.setId(Long.valueOf(-1));
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e),
                                EgressoService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComIdExistente() {
        // Cenário
        Egresso e = getEgressoValido();
        Egresso salvo = egressoRepository.save(e);
        Assertions.assertNotNull(salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(salvo),
                                EgressoService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoSemEmail() {
        // Cenário
        Egresso e = Egresso.builder().nome("João")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e), 
                                EgressoService.errorMessages.get(3));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComEmailExistente() {
        // Cenário
        String email = "joao.silva@testmail.com";
        Egresso e = Egresso.builder().nome("João")
                                        .email(email)
                                        .cpf("11111111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        Egresso salvo = egressoRepository.save(e);
        Assertions.assertNotNull(salvo);

        Egresso e2 = Egresso.builder().nome("Joãozim")
                                        .email(email)
                                        .cpf("11121111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e2), 
                                EgressoService.errorMessages.get(4));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoSemCpf() {
        // Cenário
        Egresso e = Egresso.builder().nome("João")
                                        .email("joao.silva@testmail.com")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e), 
                                EgressoService.errorMessages.get(5));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCpfExistente() {
        // Cenário
        String cpf = "11111111111";
        Egresso e = Egresso.builder().nome("João")
                                        .email("joao.silva@testmail.com")
                                        .cpf(cpf)
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        Egresso salvo = egressoRepository.save(e);
        Assertions.assertNotNull(salvo);

        Egresso e2 = Egresso.builder().nome("Joãozim")
                                        .email("joao.silva2@testmail.com")
                                        .cpf(cpf)
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e2), 
                                EgressoService.errorMessages.get(6));
    }
    // Testes de verificarEgressoValido
    @Test
    public void deveGerarErroAoTentarInserirEgressoSemNome() {
        // Cenário
        Egresso e = Egresso.builder().email("joao.silva@testmail.com")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e),
                                EgressoService.errorMessages.get(7));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComNomeVazio() {
        // Cenário
        Egresso e = Egresso.builder().nome(" ")
                                        .email("joao.silva@testmail.com")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e),
                                EgressoService.errorMessages.get(8));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComEmailVazio() {
        // Cenário
        Egresso e = Egresso.builder().nome("João")
                                        .email(" ")
                                        .cpf("11111111111")
                                        .url_foto("https//foto.joao.com")
                                        .resumo("Egresso de teste")
                                        .contatos(Set.of())
                                        .build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(e),
                                EgressoService.errorMessages.get(9));
    }
    @Test
    public void deveGerarErrosAoTentarInserirEgressosCujosEmailsTemCaracteresEspeciais() {
        // Verifica todos os caracteres proibidos
        for (CharSequence caracterEspecial : EgressoService.caracteresProibidosEmail) {
            // Cenário
            Egresso e = Egresso.builder().nome("João")
                                            .email("joao" + caracterEspecial + "silva@testmail.com")
                                            .cpf("11111111111")
                                            .url_foto("https//foto.joao.com")
                                            .resumo("Egresso de teste")
                                            .contatos(Set.of())
                                            .build();
            // Teste
            Assertions.assertThrows(RegraNegocioRuntime.class, 
                                    // Ação
                                    () -> egressoService.inserir(e),
                                    EgressoService.errorMessages.get(10));
        }
    }
    @Test
    public void deveGerarErrosAoTentarInserirEgressosComCpfsDeTamanhosInvalidos() {
        // Verifica múltiplos cenários
        for (
                String cpf : List.of(
                    "",            // Cenário 1 - Vazio 
                    "1",           // Cenário 2 - Menos que onze
                    "111111111119" // Cenário 3 - Mais que onze
                )
            )
        {
            // Cenário
            Egresso e = Egresso.builder().nome("João")
                                            .email("joao.silva@testmail.com")
                                            .cpf(cpf)
                                            .url_foto("https//foto.joao.com")
                                            .resumo("Egresso de teste")
                                            .contatos(Set.of())
                                            .build();
            // Teste
            Assertions.assertThrows(RegraNegocioRuntime.class, 
                                    // Ação
                                    () -> egressoService.inserir(e),
                                    EgressoService.errorMessages.get(11));
        }
    }
    @Test
    public void deveGerarErrosAoTentarInserirEgressosComCpfsNaoNumericos() {
        // Verifica múltiplos cenários
        for (
                String cpf : List.of(
                    "111.111.111", // Cenário 1 - Pontos 
                    "11111111-11", // Cenário 2 - Traço
                    "1111111111a"  // Cenário 3 - Letra
                )
            )
        {
            // Cenário
            Egresso e = Egresso.builder().nome("João")
                                            .email("joao.silva@testmail.com")
                                            .cpf(cpf)
                                            .url_foto("https//foto.joao.com")
                                            .resumo("Egresso de teste")
                                            .contatos(Set.of())
                                            .build();
            // Teste
            Assertions.assertThrows(RegraNegocioRuntime.class, 
                                    // Ação
                                    () -> egressoService.inserir(e),
                                    EgressoService.errorMessages.get(12));
        }
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComDepoimentosDeOutro() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso egresso_dummy = Egresso.builder().nome("Dummy")
                                                    .email("dummy.silva@testmail.com")
                                                    .cpf("11111111211")
                                                    .url_foto("https//foto.dummy.com")
                                                    .resumo("Egresso de teste 2")
                                                    .contatos(Set.of())
                                                    .build();

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_dummy)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_dummy)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(20));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCursoEgressosDeOutro() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso egresso_dummy = Egresso.builder().nome("Dummy")
                                                    .email("dummy.silva@testmail.com")
                                                    .cpf("11111111211")
                                                    .url_foto("https//foto.dummy.com")
                                                    .resumo("Egresso de teste 2")
                                                    .contatos(Set.of())
                                                    .build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(egresso_a_salvar.getCursos().iterator().next().getCurso())
                                                                    .egresso(egresso_dummy)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(18));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComProfEgressosDeOutro() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Egresso egresso_dummy = Egresso.builder().nome("Dummy")
                                                    .email("dummy.silva@testmail.com")
                                                    .cpf("11111111211")
                                                    .url_foto("https//foto.dummy.com")
                                                    .resumo("Egresso de teste 2")
                                                    .contatos(Set.of())
                                                    .build();

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_dummy)
                                                                .cargo(egresso_a_salvar.getProfissoes().iterator().next().getCargo())
                                                                .faixaSalario(egresso_a_salvar.getProfissoes().iterator().next().getFaixaSalario()).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(19));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCursoEgressoSemCurso() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);   

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(13));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCursoSemID() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);   

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(13));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCursoInexistentes() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().id(Long.valueOf(-1))
                                                .nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);   

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(13));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComProfEgressoSemCargo() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(14));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCargoSemId() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(14));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComCargoInexistente() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso").id(Long.valueOf(-1))
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        FaixaSalario faixaSalario_ex_salvo = faixaSalarioRepository.save(faixaSalario_a_salvar);                                     
        Assertions.assertNotNull(faixaSalario_ex_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(14));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComProfEgressoSemFaixaSalario() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(15));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComFaixaSalarialSemId() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(15));
    }
    @Test
    public void deveGerarErroAoTentarInserirEgressoComFaixaSalarialInexistente() {
        // Cenário
        Contato ex1 = Contato.builder().nome("Contato1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Contato2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_a_salvar)
                                                    .build();

        Curso curso_a_salvar = Curso.builder().nome("Curso Egressado")
                                                .nivel("Grad").build();

        CursoEgresso curso_egresso_a_salvar = CursoEgresso.builder().curso(curso_a_salvar)
                                                                    .egresso(egresso_a_salvar)
                                                                    .data_inicio(Date.valueOf(LocalDate.now().plusDays(-1)))
                                                                    .data_conclusao(Date.valueOf(LocalDate.now())).build();

        egresso_a_salvar.setCursos(Set.of(curso_egresso_a_salvar));

        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para ProfEgresso").build();   

        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para ProfEgresso").id(Long.valueOf(-1)).build(); 

        ProfEgresso prof_egresso_a_salvar = ProfEgresso.builder().empresa("Ensino Exemplar")
                                                                .descricao("Exemplo")
                                                                .data_registro(Date.valueOf(LocalDate.now()))
                                                                .egresso(egresso_a_salvar)
                                                                .cargo(cargo_a_salvar)
                                                                .faixaSalario(faixaSalario_a_salvar).build();

        egresso_a_salvar.setProfissoes(Set.of(prof_egresso_a_salvar));

        Depoimento d1 = Depoimento.builder().texto("Massa.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Depoimento d2 = Depoimento.builder().texto("Massa2.")
                                            .egresso(egresso_a_salvar)
                                            .data(Date.valueOf(LocalDate.now())).build();
        Set<Depoimento> depoimentos_a_salvar = Set.of(d1, d2);

        egresso_a_salvar.setDepoimentos(depoimentos_a_salvar);

        Cargo cargo_salvo = cargoRepository.save(cargo_a_salvar);                                 
        Assertions.assertNotNull(cargo_salvo);

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);
        
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.inserir(egresso_a_salvar),
                                EgressoService.errorMessages.get(15));
    }
//=============================================================================================//
//  INSERÇÃO: TESTES DE RESTRIÇÃO - FIM                                                        //
//  EDIÇÃO: TESTES DE RESTRIÇÃO - COMEÇO                                                       //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarEditarEgressoSemID() {
        // Cenário
        Egresso e = getEgressoValido(); // Egresso vem válido mas não salvo (id == null)
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.editar(e),
                                EgressoService.errorMessages.get(16));
    }
    @Test
    public void deveGerarErroAoTentarEditarEgressoComIDInexistente() {
        // Cenário
        Egresso e = getEgressoValido(); // Egresso vem válido mas não salvo (id == null)
        e.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.editar(e),
                                EgressoService.errorMessages.get(17));
    }
    //      Os outros testes de restrição para edição coincidem com inserção (verificação de 
    // validez, encontrados em EgressoService.salvar), logo, foram omitidos para evitar redun-
    // dância.
//=============================================================================================//
//  EDIÇÃO: TESTES DE RESTRIÇÃO - FIM                                                          //
//  CONSULTA: TESTES DE RESTRIÇÃO - COMEÇO                                                     //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarConsultarComIdNulo() {
        // Cenário
        Long id = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.consultarEgresso(id),
                                EgressoService.errorMessages.get(16));
    }
    @Test
    public void deveGerarErroAoTentarConsultarComIdInexistente() {
        // Cenário
        Long id = Long.valueOf(-1);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.consultarEgresso(id),
                                EgressoService.errorMessages.get(17));
    }
//=============================================================================================//
//  CONSULTA: TESTES DE RESTRIÇÃO - FIM                                                        //
//  DELEÇÃO: TESTES DE RESTRIÇÃO - COMEÇO                                                      //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarDeletarUsandoEgressoComIdNulo() {
        // Cenário
        Egresso e = getEgressoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.deletar(e),
                                EgressoService.errorMessages.get(16));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoIdNulo() {
        // Cenário
        Long id = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.deletar(id),
                                EgressoService.errorMessages.get(16));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoEgressoComIdInexistente() {
        // Cenário
        Egresso e = getEgressoValido();
        e.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.deletar(e),
                                EgressoService.errorMessages.get(17));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoIdInexistente() {
        // Cenário
        Long id = Long.valueOf(-1);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> egressoService.deletar(id),
                                EgressoService.errorMessages.get(17));
    }
//=============================================================================================//
//  DELEÇÃO: TESTES DE RESTRIÇÃO - FIM                                                         //
//=============================================================================================//
}
