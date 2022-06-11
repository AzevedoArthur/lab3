package com.egresso.egresso.service;

import java.util.ArrayList;
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
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;

import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FaixaSalarioServiceTest {
    @Autowired
    FaixaSalarioService service;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    ProfEgressoRepository profEgressoRepository;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;


    private List<FaixaSalario> getFaixasSalarioTeste() {
        FaixaSalario faixaSalario_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build(); 
        FaixaSalario faixaSalario = faixaSalarioRepository.save(faixaSalario_a_salvar);    
        Assertions.assertNotNull(faixaSalario);

        FaixaSalario faixaSalario2_a_salvar = FaixaSalario.builder().descricao("Faixa de Salario 2 de teste para FaixaSalarioService").build(); 
        FaixaSalario faixaSalario2 = faixaSalarioRepository.save(faixaSalario2_a_salvar);    
        Assertions.assertNotNull(faixaSalario2);

        return List.of(faixaSalario, faixaSalario2);
    }

    private List<Egresso> getEgressosTeste(FaixaSalario faixaSalarioTeste1, FaixaSalario faixaSalarioTeste2) {
        Cargo cargo_a_salvar = Cargo.builder().nome("Professor Egresso")
                                                .descricao("Cargo de teste para FaixaSalarioService").build();   
        Cargo cargo = cargoRepository.save(cargo_a_salvar);
        Assertions.assertNotNull(cargo);   

        Egresso egresso1 = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .build();
        ProfEgresso prof_egresso1_1 = ProfEgresso.builder().empresa("Ensino Exemplar")
                        .descricao("Exemplo")
                        .data_registro(Date.valueOf(LocalDate.now()))
                        .egresso(egresso1)
                        .cargo(cargo)
                        .faixaSalario(faixaSalarioTeste1).build();
        egresso1.setProfissoes(new HashSet<>(Set.of(prof_egresso1_1)));

        Egresso egresso2 = Egresso.builder().nome("Maria")
                                                    .email("maria.pereira@testmail.com")
                                                    .cpf("22222222222")
                                                    .url_foto("https//foto.maria.com")
                                                    .resumo("Egresso de teste 2")
                                                    .build();
        ProfEgresso prof_egresso2_1 = ProfEgresso.builder().empresa("Ensino Exemplar 2")
                        .descricao("Exemplo 2")
                        .data_registro(Date.valueOf(LocalDate.now()))
                        .egresso(egresso2)
                        .cargo(cargo)
                        .faixaSalario(faixaSalarioTeste2).build();
        egresso2.setProfissoes(new HashSet<>(Set.of(prof_egresso2_1)));

        Egresso egresso3 = Egresso.builder().nome("Jeremias")
                                                    .email("jeremias.pereira@testmail.com")
                                                    .cpf("33333333333")
                                                    .url_foto("https//foto.jeremias.com")
                                                    .resumo("Egresso de teste 3")
                                                    .build();
        ProfEgresso prof_egresso3_1 = ProfEgresso.builder().empresa("Ensino Exemplar 3")
                        .descricao("Exemplo 3")
                        .data_registro(Date.valueOf(LocalDate.now()))
                        .egresso(egresso3)
                        .cargo(cargo)
                        .faixaSalario(faixaSalarioTeste1).build();
        ProfEgresso prof_egresso3_2 = ProfEgresso.builder().empresa("Ensino Exemplar 32")
                        .descricao("Exemplo 3-2")
                        .data_registro(Date.valueOf(LocalDate.now()))
                        .egresso(egresso3)
                        .cargo(cargo)
                        .faixaSalario(faixaSalarioTeste2).build();
        egresso3.setProfissoes(new HashSet<>(Set.of(prof_egresso3_1, prof_egresso3_2)));

        Egresso egresso4 = Egresso.builder().nome("Pedro")
                                                    .email("pedro.silva@testmail.com")
                                                    .cpf("44444444444")
                                                    .url_foto("https//foto.pedro.com")
                                                    .resumo("Egresso de teste 4")
                                                    .build();
        ProfEgresso prof_egresso4_1 = ProfEgresso.builder().empresa("Ensino Exemplar 4")
                        .descricao("Exemplo 4")
                        .data_registro(Date.valueOf(LocalDate.now()))
                        .egresso(egresso4)
                        .cargo(cargo)
                        .faixaSalario(faixaSalarioTeste1).build();
        egresso4.setProfissoes(new HashSet<>(Set.of(prof_egresso4_1)));

        
        List<Egresso> egressos = new ArrayList<>();
        for (Egresso e : List.of(egresso1, egresso2, egresso3, egresso4)) {
            Egresso salvo = egressoRepository.save(e);
            Assertions.assertNotNull(salvo);
            egressos.add(salvo);
        }

        return egressos;
    }
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveObterNenhumEgressoPorUmaFaixa() {
        // Cenário
        List<FaixaSalario> faixasTeste = getFaixasSalarioTeste();
        // Ação
        Set<Egresso> consulta = service.consultarEgressosPorFaixaSalario(faixasTeste.get(0));
        // Teste
        Assertions.assertNotNull(consulta);
        Assertions.assertTrue(consulta.isEmpty());
    }
    @Test
    public void deveObterEgressosInseridosPorUmaFaixa() {
        // Cenário
        List<FaixaSalario> faixasTeste = getFaixasSalarioTeste();
        List<Egresso>    egressosTeste = getEgressosTeste(faixasTeste.get(0), faixasTeste.get(1));
        // Ação
        Set<Egresso> consulta1 = service.consultarEgressosPorFaixaSalario(faixasTeste.get(0));
        Set<Egresso> consulta2 = service.consultarEgressosPorFaixaSalario(faixasTeste.get(1));
        // Teste
        Set<Egresso> resultadoEsperado1 = Set.of(egressosTeste.get(0), egressosTeste.get(2), egressosTeste.get(3));
        Set<Egresso> resultadoEsperado2 = Set.of(egressosTeste.get(1), egressosTeste.get(2));
        
        Assertions.assertEquals(resultadoEsperado1.size(), consulta1.size());
        Assertions.assertEquals(resultadoEsperado1, consulta1);
        Assertions.assertEquals(resultadoEsperado2.size(), consulta2.size());
        Assertions.assertEquals(resultadoEsperado2, consulta2);
    }
    @Test
    public void deveObterEgressosInseridosPorMultiplasFaixas() {
        // Cenário
        List<FaixaSalario> faixasTeste = getFaixasSalarioTeste();
        List<Egresso>    egressosTeste = getEgressosTeste(faixasTeste.get(0), faixasTeste.get(1));
        // Ação
        Set<Egresso> consulta = service.consultarEgressosPorFaixaSalario(faixasTeste);
        // Teste        
        Assertions.assertEquals(egressosTeste.size(), consulta.size());
        Assertions.assertEquals(Set.copyOf(egressosTeste), consulta);
    }
    @Test
    public void deveObterQuantitativoDeEgressosInseridosPorUmaFaixa() {
        // Cenário
        List<FaixaSalario> faixasTeste = getFaixasSalarioTeste();
        getEgressosTeste(faixasTeste.get(0), faixasTeste.get(1));
        // Ação
        int consulta1 = service.quantitativoEgressosPorFaixaSalario(faixasTeste.get(0));
        int consulta2 = service.quantitativoEgressosPorFaixaSalario(faixasTeste.get(1));
        // Teste
        Assertions.assertEquals(3, consulta1);
        Assertions.assertEquals(2, consulta2);
    }
    @Test
    public void deveObterQuantitativoDeEgressosInseridosPorMultiplasFaixas() {
        // Cenário
        List<FaixaSalario> faixasTeste = getFaixasSalarioTeste();
        getEgressosTeste(faixasTeste.get(0), faixasTeste.get(1));
        // Ação
        int consulta = service.quantitativoEgressosPorFaixaSalario(faixasTeste);
        // Teste        
        Assertions.assertEquals(4, consulta);
    }
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//  TESTES DE RESTRIÇÃO - COMEÇO                                                               //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarObterEgressosComFaixaNula() {
        // Cenário
        FaixaSalario faixaTeste = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComFaixaNaoRegistrada() {
        // Cenário
        FaixaSalario faixaTeste = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComFaixaDeIdInexistente() {
        // Cenário
        FaixaSalario faixaTeste = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").id(Long.valueOf(-1)).build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComColecaoDeFaixasNula() {
        // Cenário
        List<FaixaSalario> faixas = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(faixas), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComPeloMenosUmaFaixaNula() {
        // Cenário
        FaixaSalario faixaInvalida = null;
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        
        List<FaixaSalario> faixasTeste = new ArrayList<>();
        faixasTeste.add(faixasValidas.get(0));
        faixasTeste.add(faixaInvalida);

        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(faixasTeste), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComPeloMenosUmaFaixaNaoRegistrada() {
        // Cenário
        FaixaSalario faixaInvalida = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build();
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(List.of(faixasValidas.get(0), faixaInvalida)), 
                                FaixaSalarioService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComPeloMenosUmaFaixaDeIdInexistente() {
        // Cenário
        FaixaSalario faixaInvalida = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").id(Long.valueOf(-1)).build();
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorFaixaSalario(List.of(faixasValidas.get(0), faixaInvalida)), 
                                FaixaSalarioService.errorMessages.get(2));
    }

    @Test
    public void deveGerarErroAoTentarObterQuantitativoComColecaoDeFaixasNula() {
        // Cenário
        List<FaixaSalario> faixas = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(faixas), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComFaixaNula() {
        // Cenário
        FaixaSalario faixaTeste = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComFaixaNaoRegistrada() {
        // Cenário
        FaixaSalario faixaTeste = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComFaixaDeIdInexistente() {
        // Cenário
        FaixaSalario faixaTeste = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").id(Long.valueOf(-1)).build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(faixaTeste), 
                                FaixaSalarioService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComPeloMenosUmaFaixaNula() {
        // Cenário
        FaixaSalario faixaInvalida = null;
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        
        List<FaixaSalario> faixasTeste = new ArrayList<>();
        faixasTeste.add(faixasValidas.get(0));
        faixasTeste.add(faixaInvalida);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(faixasTeste), 
                                FaixaSalarioService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComPeloMenosUmaFaixaNaoRegistrada() {
        // Cenário
        FaixaSalario faixaInvalida = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build();
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(List.of(faixasValidas.get(0), faixaInvalida)), 
                                FaixaSalarioService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoComPeloMenosUmaFaixaDeIdInexistente() {
        // Cenário
        FaixaSalario faixaInvalida = FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").id(Long.valueOf(-1)).build();
        List<FaixaSalario> faixasValidas = getFaixasSalarioTeste();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorFaixaSalario(List.of(faixasValidas.get(0), faixaInvalida)), 
                                FaixaSalarioService.errorMessages.get(2));
    }
//=============================================================================================//
//  TESTES DE RESTRIÇÃO - FIM                                                                  //
//=============================================================================================//
}
