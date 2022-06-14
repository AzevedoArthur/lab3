package com.egresso.egresso.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Egresso; 

import com.egresso.egresso.model.repositories.DepoimentoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DepoimentoServiceTest {
    @Autowired
    DepoimentoService service;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    DepoimentoRepository depoimentoRepository;
    
    private Egresso getEgressoValido() {
        return Egresso.builder().nome("João")
                                .email("joao.silva@testmail.com")
                                .cpf("11111111111")
                                .url_foto("https//foto.joao.com")
                                .resumo("Egresso de teste")
                                .build();
    }
    private Depoimento getDepoimentoValido(Egresso egresso_a_salvar) {
        Depoimento dep = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                             .egresso(egresso_a_salvar)
                                             .data(Date.valueOf(LocalDate.now())).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);

        return dep;
    }
    private List<Depoimento> getMultiplosDepoimentosValidos(Egresso egresso_a_salvar) {
        ArrayList<Depoimento> depoimentos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            depoimentos.add(Depoimento.builder().texto("Depoimento de teste de unidade " + i +".")
                                                 .egresso(egresso_a_salvar)
                                                 .data(Date.valueOf(LocalDate.now().minusDays(i))).build());
        }
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);

        return depoimentos;
    }
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveInserirDepoimentoValido() {
        // Cenário
        Depoimento depValido = getDepoimentoValido(getEgressoValido());
        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação
            () -> service.inserir(depValido)
        );
    }
    @Test
    public void deveEditarCamposDeDepoimentoInserido() {
        // Cenário
        Depoimento depValido = getDepoimentoValido(getEgressoValido());
        Depoimento salvo = service.inserir(depValido);

        // Ação - parte 1
        Depoimento consulta = depoimentoRepository.getById(salvo.getId());
        consulta.setTexto("Depoimento de teste de unidade (alterado).");
        consulta.setData(Date.valueOf(LocalDate.now().minusDays(1)));

        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação - parte 2
            () -> service.editar(consulta)
        );
    }
    @Test
    public void deveDeletarDepoimentoInseridoUsandoDepoimento() {
        // Cenário
        Depoimento depoimento_a_salvar = getDepoimentoValido(getEgressoValido());
        Depoimento salvo = service.inserir(depoimento_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> service.deletar(salvo)
        );
        // Teste
        Assertions.assertTrue(depoimentoRepository.findById(id).isEmpty());
    }
    @Test
    public void deveDeletarDepoimentoInseridoUsandoId() {
        // Cenário
        Depoimento depoimento_a_salvar = getDepoimentoValido(getEgressoValido());
        Depoimento salvo = service.inserir(depoimento_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> service.deletar(id)
        );
        // Teste
        Assertions.assertTrue(depoimentoRepository.findById(id).isEmpty());
    }
    @Test
    public void deveConsultarDepoimentosOrdenadosPorData() {
        // Cenário
        List<Depoimento> depoimentos = getMultiplosDepoimentosValidos(getEgressoValido());
        List<Depoimento> depoimentos_salvos = new ArrayList<>();
        for (Depoimento depoimento : depoimentos) {
            Depoimento salvo = depoimentoRepository.save(depoimento);
            Assertions.assertNotNull(salvo);
            depoimentos_salvos.add(salvo);
        }
        // Ação
        List<Depoimento> listaOrdenada = service.consultarDepoimentosOrdenadosPorRecente();
        // Teste
        Assertions.assertEquals(depoimentoRepository.findAll().size(), listaOrdenada.size());
        for (int i = 0; i < listaOrdenada.size() - 1; i++) {
            Depoimento o1 = listaOrdenada.get(i);
            Depoimento o2 = listaOrdenada.get(i + 1);
            Assertions.assertTrue(LocalDate.parse(o1.getData().toString())
                                  .isBefore(
                                  LocalDate.parse(o2.getData().toString())
            ));
        }
    }
    @Test
    public void deveConsultarDepoimentosPorEgresso() {
        // Cenário
        List<Depoimento> depoimentos1 = getMultiplosDepoimentosValidos(getEgressoValido());
        List<Depoimento> depoimentos2 = getMultiplosDepoimentosValidos(Egresso.builder().nome("Maria")
                                                                                        .email("maria.silva@testmail.com")
                                                                                        .cpf("11111111112")
                                                                                        .url_foto("https//foto.maria.com")
                                                                                        .resumo("Egresso de teste 2")
                                                                                        .build());
        List<Depoimento> depoimentos1_salvos = new ArrayList<>();
        for (Depoimento depoimento : depoimentos1) {
            Depoimento salvo = depoimentoRepository.save(depoimento);
            Assertions.assertNotNull(salvo);
            depoimentos1_salvos.add(salvo);
        }
        List<Depoimento> depoimentos2_salvos = new ArrayList<>();
        for (Depoimento depoimento : depoimentos2) {
            Depoimento salvo = depoimentoRepository.save(depoimento);
            Assertions.assertNotNull(salvo);
            depoimentos2_salvos.add(salvo);
        }
        // Ação
        List<Depoimento> lista = service.consultarDepoimentosPorEgresso(depoimentos1.get(0).getEgresso());
        // Teste
        Assertions.assertEquals(lista.size(), depoimentos1_salvos.size());
        Assertions.assertEquals(Set.copyOf(lista), Set.copyOf(depoimentos1_salvos));
    }
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//  TESTES DE RESTRIÇÃO - COMEÇO                                                               //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoNulo() {
        // Cenário
        Depoimento d = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d), 
                                EgressoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComIdInexistente() {
        // Cenário
        Depoimento d = getDepoimentoValido(getEgressoValido());
        d.setId(Long.valueOf(-1));
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(9));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComIdExistente() {
        // Cenário
        Depoimento d = getDepoimentoValido(getEgressoValido());
        Depoimento salvo = depoimentoRepository.save(d);
        Assertions.assertNotNull(salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(salvo),
                                EgressoService.errorMessages.get(8));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoSemEgresso() {
        // Cenário
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .data(Date.valueOf(LocalDate.now())).build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComEgressoSemId() {
        // Cenário
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .egresso(getEgressoValido())
                                           .data(Date.valueOf(LocalDate.now())).build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComEgressoComIdInexistente() {
        // Cenário
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .egresso(getEgressoValido())
                                           .data(Date.valueOf(LocalDate.now())).build();
        d.getEgresso().setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoSemTexto() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().egresso(egresso_a_salvar)
                                           .data(Date.valueOf(LocalDate.now())).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(3));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComTextoVazio() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().texto(" ")
                                           .egresso(egresso_a_salvar)
                                           .data(Date.valueOf(LocalDate.now())).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(4));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoSemData() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .egresso(egresso_a_salvar).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(5));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComDataFutura() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .egresso(egresso_a_salvar)
                                           .data(Date.valueOf(LocalDate.now().plusDays(1))).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarInserirDepoimentoComDataAntiga() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().texto("Depoimento de teste de unidade.")
                                           .egresso(egresso_a_salvar)
                                           .data(Date.valueOf("2000-01-01")).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(d),
                                EgressoService.errorMessages.get(7));
    }
    @Test
    public void deveGerarErroAoTentarEditarDepoimentoNulo() {
        // Cenário
        Depoimento d = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.editar(d), 
                                EgressoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarEditarDepoimentoNaoCadastrado() {
        // Cenário
        Depoimento d = getDepoimentoValido(getEgressoValido());
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.editar(d), 
                                EgressoService.errorMessages.get(10));
    }
    @Test
    public void deveGerarErroAoTentarEditarDepoimentoComIdInexistente() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().id(Long.valueOf(-1))
                                           .texto("Depoimento de teste de unidade.")
                                           .egresso(egresso_a_salvar)
                                           .data(Date.valueOf(LocalDate.now())).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.editar(d), 
                                EgressoService.errorMessages.get(11));
    }
    @Test
    public void deveGerarErroAoTentarDeletarDepoimentoNulo() {
        // Cenário
        Depoimento d = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(d), 
                                EgressoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarDeletarDepoimentoNaoCadastradoPorDepoimento() {
        // Cenário
        Depoimento d = getDepoimentoValido(getEgressoValido());
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(d), 
                                EgressoService.errorMessages.get(10));
    }
    @Test
    public void deveGerarErroAoTentarDeletarDepoimentoComIdInexistentePorDepoimento() {
        // Cenário
        Egresso egresso_a_salvar = getEgressoValido();
        Depoimento d = Depoimento.builder().id(Long.valueOf(-1))
                                           .texto("Depoimento de teste de unidade.")
                                           .egresso(egresso_a_salvar)
                                           .data(Date.valueOf(LocalDate.now())).build();
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(d), 
                                EgressoService.errorMessages.get(11));
    }
    @Test
    public void deveGerarErroAoTentarDeletarDepoimentoPorIdNulo() {
        // Cenário
        Long id = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(id), 
                                EgressoService.errorMessages.get(10));
    }
    @Test
    public void deveGerarErroAoTentarDeletarDepoimentoComIdInexistentePorId() {
        // Cenário
        Long id = Long.valueOf(-1);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(id), 
                                EgressoService.errorMessages.get(11));
    }
    @Test
    public void deveGerarErroAoTentarConsultarDepoimentosComEgressoNulo() {
        // Cenário
        Egresso e = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarDepoimentosPorEgresso(e), 
                                EgressoService.errorMessages.get(12));
    }
    @Test
    public void deveGerarErroAoTentarConsultarDepoimentosComEgressoNaoCadastrado() {
        // Cenário
        Egresso e = getEgressoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarDepoimentosPorEgresso(e), 
                                EgressoService.errorMessages.get(13));
    }
    @Test
    public void deveGerarErroAoTentarConsultarDepoimentosComEgressoComIdInexistente() {
        // Cenário
        Egresso e = getEgressoValido();
        e.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarDepoimentosPorEgresso(e), 
                                EgressoService.errorMessages.get(14));
    }
//=============================================================================================//
//  TESTES DE RESTRIÇÃO - FIM                                                                  //
//=============================================================================================//
}
