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

import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Egresso;
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;
import com.egresso.egresso.model.repositories.CargoRepository;
import com.egresso.egresso.model.repositories.EgressoRepository;
import com.egresso.egresso.model.repositories.FaixaSalarioRepository;
import com.egresso.egresso.model.repositories.ProfEgressoRepository;
import com.egresso.egresso.service.exceptions.RegraNegocioRuntime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CargoServiceTest {
    @Autowired
    CargoService service;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    ProfEgressoRepository profEgressoRepository;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FaixaSalarioRepository faixaSalarioRepository;
    
    private Egresso getEgressoValido() {
        return Egresso.builder().nome("João")
                                .email("joao.silva@testmail.com")
                                .cpf("11111111111")
                                .url_foto("https//foto.joao.com")
                                .resumo("Egresso de teste")
                                .build();
    }
    private Cargo getCargoValido() {
        Cargo dep = Cargo.builder().nome("Cargo de teste")
                                   .descricao("Cargo genérico criado para testes de unidade.").build();

        return dep;
    }
    private FaixaSalario getFaixaSalarioValido() {
        return FaixaSalario.builder().descricao("Faixa de Salario de teste para FaixaSalarioService").build();
    }
    private FaixaSalario getFaixaSalarioValidoESalvo() {
        FaixaSalario fs_salvo = faixaSalarioRepository.save(getFaixaSalarioValido());
        Assertions.assertNotNull(fs_salvo);
        return fs_salvo;
    }
    private ProfEgresso getProfEgressoValido(Cargo c, Egresso e, FaixaSalario fs) {
        return ProfEgresso.builder().empresa("Ensino Exemplar")
                                    .descricao("Exemplo")
                                    .data_registro(Date.valueOf(LocalDate.now()))
                                    .egresso(e)
                                    .cargo(c)
                                    .faixaSalario(fs).build();
    }
    private List<Cargo> getMultiplosCargosValidos() {
        ArrayList<Cargo> cargos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Cargo c = Cargo.builder().nome("Cargo de teste " + i)
                                     .descricao("Cargo genérico criado para testes de unidade.").build();
            Cargo c_salvo = cargoRepository.save(c);
            Assertions.assertNotNull(c_salvo);
            cargos.add(c);
        }
        return cargos;
    }
    private List<Cargo> getMultiplosCargosSalvos() {
        List<Cargo> cargos = getMultiplosCargosValidos();
        for (int i = 0; i < cargos.size(); i++) {
            Cargo c_salvo = cargoRepository.save(cargos.get(i));
            Assertions.assertNotNull(c_salvo);
            cargos.set(i, c_salvo);
        }
        return cargos;
    }
    private List<ProfEgresso> getMultiplosProfEgressosValidos(Egresso egresso_a_salvar, List<Cargo> cargos_salvos, FaixaSalario fs_salvo) {
        ArrayList<ProfEgresso> profs = new ArrayList<>();
        for (Cargo c : cargos_salvos) {
            profs.add(getProfEgressoValido(c, egresso_a_salvar, fs_salvo));
        }
        Egresso egresso_salvo = egressoRepository.save(egresso_a_salvar);
        Assertions.assertNotNull(egresso_salvo);

        return profs;
    }
    private List<ProfEgresso> getMultiplosProfEgressosSalvos(Egresso egresso_a_salvar, List<Cargo> cargos_salvos, FaixaSalario fs_salvo) {
        List<ProfEgresso> profs = getMultiplosProfEgressosValidos(egresso_a_salvar, cargos_salvos, fs_salvo);
        for (int i = 0; i < profs.size(); i++) {
            ProfEgresso pe_salvo = profEgressoRepository.save(profs.get(i));
            Assertions.assertNotNull(pe_salvo);
            profs.set(i, pe_salvo);
        }
        return profs;
    }
    
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - COMEÇO                                                          //
//=============================================================================================//
    @Test
    public void deveInserirCargoValido() {
        // Cenário
        Cargo cargoValido = getCargoValido();
        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação
            () -> service.inserir(cargoValido)
        );
    }
    @Test
    public void deveEditarCamposDeCargoInserido() {
        // Cenário
        Cargo cargoValido = getCargoValido();
        Cargo salvo = service.inserir(cargoValido);

        // Ação - parte 1
        Cargo consulta = cargoRepository.getById(salvo.getId());
        consulta.setNome("Cargo alterado");
        consulta.setDescricao("Cargo genérico editado para testes de unidade.");

        // Teste
        Assertions.assertDoesNotThrow( 
            // Ação - parte 2
            () -> service.editar(consulta)
        );
    }
    @Test
    public void deveDeletarCargoInseridoUsandoCargo() {
        // Cenário
        Cargo cargo_a_salvar = getCargoValido();
        Cargo salvo = service.inserir(cargo_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> service.deletar(salvo)
        );
        // Teste
        Assertions.assertTrue(cargoRepository.findById(id).isEmpty());
    }
    @Test
    public void deveDeletarCargoInseridoUsandoId() {
        // Cenário
        Cargo cargo_a_salvar = getCargoValido();
        Cargo salvo = service.inserir(cargo_a_salvar);
        Assertions.assertNotNull(salvo);   
        Long id = salvo.getId();
        // Ação
        Assertions.assertDoesNotThrow( 
            () -> service.deletar(id)
        );
        // Teste
        Assertions.assertTrue(cargoRepository.findById(id).isEmpty());
    }
    @Test
    public void deveConsultarCargosPorEgresso() {
        // Cenário
        List<Cargo> cargos = getMultiplosCargosSalvos();
        FaixaSalario fs = getFaixaSalarioValidoESalvo();
        List<ProfEgresso> profs1 = getMultiplosProfEgressosSalvos(getEgressoValido(), cargos.subList(0, 3), fs);
        getMultiplosProfEgressosSalvos(Egresso.builder().nome("Maria")
                                                        .email("maria.silva@testmail.com")
                                                        .cpf("11111111112")
                                                        .url_foto("https//foto.maria.com")
                                                        .resumo("Egresso de teste 2")
                                                        .build(), 
                                        cargos.subList(3, 5),
                                        fs);
        // Ação
        List<Cargo> lista = service.consultarCargosPorEgresso(profs1.get(0).getEgresso());
        // Teste
        Assertions.assertEquals(lista.size(), profs1.size());
        Assertions.assertEquals(Set.copyOf(lista), Set.copyOf(cargos.subList(0, 3)));
    }
    @Test
    public void deveConsultarEgressosPorCargo() {
        // Cenário
        List<Cargo> cargos = getMultiplosCargosSalvos();
        FaixaSalario fs = getFaixaSalarioValidoESalvo();
        List<ProfEgresso> profs1 = getMultiplosProfEgressosSalvos(getEgressoValido(), cargos.subList(0, 3), fs);
        List<ProfEgresso> profs2 = getMultiplosProfEgressosSalvos(Egresso.builder().nome("Maria")
                                                        .email("maria.silva@testmail.com")
                                                        .cpf("11111111112")
                                                        .url_foto("https//foto.maria.com")
                                                        .resumo("Egresso de teste 2")
                                                        .build(), 
                                        cargos.subList(2, 5),
                                        fs);
        Set<Egresso> egressos = Set.of(profs1.get(0).getEgresso(), profs2.get(0).getEgresso());
        // Ação
        List<Egresso> lista = service.consultarEgressosPorCargo(cargos.get(2));
        // Teste
        Assertions.assertEquals(lista.size(), 2);
        Assertions.assertEquals(Set.copyOf(lista), egressos);
    }
    @Test
    public void deveConsultarQuantitativoEgressosPorCargo() {
        // Cenário
        List<Cargo> cargos = getMultiplosCargosSalvos();
        FaixaSalario fs = getFaixaSalarioValidoESalvo();
        getMultiplosProfEgressosSalvos(getEgressoValido(), cargos.subList(0, 3), fs);
        getMultiplosProfEgressosSalvos(Egresso.builder().nome("Maria")
                                                        .email("maria.silva@testmail.com")
                                                        .cpf("11111111112")
                                                        .url_foto("https//foto.maria.com")
                                                        .resumo("Egresso de teste 2")
                                                        .build(), 
                                        cargos.subList(2, 5),
                                        fs);
        // Ação
        int qtv = service.quantitativoEgressosPorCargo(cargos.get(2));
        // Teste
        Assertions.assertEquals(qtv, 2);
    }
//=============================================================================================//
//  TESTES DE FUNCIONALIDADE - FIM                                                             //
//  TESTES DE RESTRIÇÃO - COMEÇO                                                               //
//=============================================================================================//
    @Test
    public void deveGerarErroAoTentarInserirCargoNulo() {
        // Cenário
        Cargo c = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(c), 
                                CargoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarInserirCargoComIdInexistente() {
        // Cenário
        Cargo c = getCargoValido();
        c.setId(Long.valueOf(-1));
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(c),
                                CargoService.errorMessages.get(4));
    }
    @Test
    public void deveGerarErroAoTentarInserirCargoComIdExistente() {
        // Cenário
        Cargo c = getCargoValido();
        Cargo salvo = cargoRepository.save(c);
        Assertions.assertNotNull(salvo);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(salvo),
                                CargoService.errorMessages.get(3));
    }
    @Test
    public void deveGerarErroAoTentarInserirCargoSemNome() {
        // Cenário
        Cargo c = Cargo.builder().descricao("Cargo sem nome.").build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(c), 
                                CargoService.errorMessages.get(1));
    }
    @Test
    public void deveGerarErroAoTentarInserirCargoComNomeVazio() {
        // Cenário
        Cargo c = Cargo.builder().nome("").descricao("Cargo sem nome.").build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(c), 
                                CargoService.errorMessages.get(2));
    }
    @Test
    public void deveGerarErroAoTentarInserirCargoComNomeExistente() {
        // Cenário
        Cargo c = getCargoValido();
        Cargo salvo = cargoRepository.save(c);
        Assertions.assertNotNull(salvo);

        Cargo e2 = Cargo.builder().nome(salvo.getNome())
                                .descricao("Cargo de nome repetido").build();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.inserir(e2), 
                                CargoService.errorMessages.get(5));
    }
    @Test
    public void deveGerarErroAoTentarEditarCargoSemID() {
        // Cenário
        Cargo c = getCargoValido(); // Cargo vem válido mas não salvo (id == null)
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.editar(c),
                                CargoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarEditarCargoComIDInexistente() {
        // Cenário
        Cargo c = getCargoValido(); // Cargo vem válido mas não salvo (id == null)
        c.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.editar(c),
                                CargoService.errorMessages.get(7));
    } 
    @Test
    public void deveGerarErroAoTentarDeletarUsandoCargoNulo() {
        // Cenário
        Cargo c = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(c),
                                CargoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoCargoComIdNulo() {
        // Cenário
        Cargo c = getCargoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(c),
                                CargoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoIdNulo() {
        // Cenário
        Long id = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(id),
                                CargoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoCargoComIdInexistente() {
        // Cenário
        Cargo e = getCargoValido();
        e.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(e),
                                CargoService.errorMessages.get(7));
    }
    @Test
    public void deveGerarErroAoTentarDeletarUsandoIdInexistente() {
        // Cenário
        Long id = Long.valueOf(-1);
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.deletar(id),
                                CargoService.errorMessages.get(7));
    }
    @Test
    public void deveGerarErroAoTentarConsultarCargosComEgressoNulo() {
        // Cenário
        Egresso e = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarCargosPorEgresso(e), 
                                EgressoService.errorMessages.get(8));
    }
    @Test
    public void deveGerarErroAoTentarConsultarCargosComEgressoNaoCadastrado() {
        // Cenário
        Egresso e = getEgressoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarCargosPorEgresso(e), 
                                EgressoService.errorMessages.get(9));
    }
    @Test
    public void deveGerarErroAoTentarConsultarCargosComEgressoComIdInexistente() {
        // Cenário
        Egresso e = getEgressoValido();
        e.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarCargosPorEgresso(e), 
                                CargoService.errorMessages.get(10));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComCargoNulo() {
        // Cenário
        Cargo cargoTeste = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComCargoNaoRegistrado() {
        // Cenário
        Cargo cargoTeste = getCargoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarObterEgressosComCargoDeIdInexistente() {
        // Cenário
        Cargo cargoTeste = getCargoValido();
        cargoTeste.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.consultarEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(7));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoEgressosComCargoNulo() {
        // Cenário
        Cargo cargoTeste = null;
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(0));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoEgressosComCargoNaoRegistrado() {
        // Cenário
        Cargo cargoTeste = getCargoValido();
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(6));
    }
    @Test
    public void deveGerarErroAoTentarObterQuantitativoEgressosComCargoDeIdInexistente() {
        // Cenário
        Cargo cargoTeste = getCargoValido();
        cargoTeste.setId(Long.valueOf(-1));
        // Teste
        Assertions.assertThrows(RegraNegocioRuntime.class, 
                                // Ação
                                () -> service.quantitativoEgressosPorCargo(cargoTeste), 
                                CargoService.errorMessages.get(7));
    }
//=============================================================================================//
//  TESTES DE RESTRIÇÃO - FIM                                                                  //
//=============================================================================================//
}
