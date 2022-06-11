package com.egresso.egresso.model.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.egresso.egresso.model.entities.Cargo;
import com.egresso.egresso.model.entities.Contato;
import com.egresso.egresso.model.entities.Depoimento;
import com.egresso.egresso.model.entities.Curso;
import com.egresso.egresso.model.entities.CursoEgresso;
import com.egresso.egresso.model.entities.Egresso; // Interface
import com.egresso.egresso.model.entities.FaixaSalario;
import com.egresso.egresso.model.entities.ProfEgresso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EgressoRepositoryTest{
    @Autowired
    EgressoRepository repository;
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
    
    @Test
    public void deveVerificarSalvarEgressoSemRelacoes() {
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());
    }
    
    @Test
    public void deveVerificarSalvarEgressoComRelacoes() {
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

        Curso curso_salvo = cursoRepository.save(curso_a_salvar);
        Assertions.assertNotNull(curso_salvo);

        // Ação - operar no banco
        
        Egresso egresso_salvo = repository.save(egresso_a_salvar);

        // Verificação - A ação ocorreu?
        Assertions.assertNotNull(egresso_salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), egresso_salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), egresso_salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), egresso_salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), egresso_salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), egresso_salvo.getResumo());
        Assertions.assertEquals(egresso_a_salvar.getContatos(), egresso_salvo.getContatos());


        Egresso egresso_atualizado = repository.getById(egresso_salvo.getId());
        Assertions.assertNotNull(egresso_atualizado.getCursos());
        Assertions.assertFalse(egresso_atualizado.getCursos().isEmpty());
        Assertions.assertNotNull(egresso_atualizado.getProfissoes());
        Assertions.assertFalse(egresso_atualizado.getProfissoes().isEmpty());
    }
    
    @Test
    public void deveVerificarObterTodosEgressos(){
        // Ação - operar no banco
        List<Egresso> query = repository.findAll();

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
    }
    
    @Test
    public void deveVerificarObterEgressoSalvoById(){
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        // Ação - operar no banco

        Optional<Egresso> query = repository.findById(salvo.getId());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Egresso q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getEmail(), q.getEmail());
        Assertions.assertEquals(salvo.getCpf(), q.getCpf());
        Assertions.assertEquals(salvo.getUrl_foto(), q.getUrl_foto());
        Assertions.assertEquals(salvo.getResumo(), q.getResumo());
    }
    
    @Test
    public void deveVerificarObterEgressoSalvoByEmail(){
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        Egresso salvo = repository.save(egresso_a_salvar);
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        // Ação - operar no banco

        Optional<Egresso> query = repository.findByEmail(salvo.getEmail());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Egresso q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getEmail(), q.getEmail());
        Assertions.assertEquals(salvo.getCpf(), q.getCpf());
        Assertions.assertEquals(salvo.getUrl_foto(), q.getUrl_foto());
        Assertions.assertEquals(salvo.getResumo(), q.getResumo());
    }
    
    @Test
    public void deveVerificarEgressoExistsByEmail(){
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        // Ação - operar no banco

        boolean exists = repository.existsByEmail(salvo.getEmail());

        // Verificação - A ação ocorreu?
   
        Assertions.assertTrue(exists);
    }
    
    @Test
    public void deveVerificarEgressoNaoExistsByEmail(){

        // Ação - operar no banco
        boolean exists = repository.existsByEmail("email");

        // Verificação - A ação ocorreu?
   
        Assertions.assertFalse(exists);
    }
    
    @Test
    public void deveVerificarObterEgressoSalvoByCpf(){
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        // Ação - operar no banco

        Optional<Egresso> query = repository.findByCpf(salvo.getCpf());

        // Verificação - A ação ocorreu?
   
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.isEmpty());
        Egresso q = query.get();
        Assertions.assertEquals(salvo.getNome(), q.getNome());
        Assertions.assertEquals(salvo.getEmail(), q.getEmail());
        Assertions.assertEquals(salvo.getCpf(), q.getCpf());
        Assertions.assertEquals(salvo.getUrl_foto(), q.getUrl_foto());
        Assertions.assertEquals(salvo.getResumo(), q.getResumo());
    }
    
    @Test
    public void deveVerificarEgressoExistsByCpf(){
        // Cenário
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        // Ação - operar no banco
        boolean exists = repository.existsByCpf(salvo.getCpf());

        // Verificação - A ação ocorreu?
   
        Assertions.assertTrue(exists);
    }
    
    @Test
    public void deveVerificarEgressoNaoExistsByCpf(){

        // Ação - operar no banco
        boolean exists = repository.existsByCpf("cpf");

        // Verificação - A ação ocorreu?
   
        Assertions.assertFalse(exists);
    }
    
    @Test
    public void deveVerificarRemoverEgressoSalvoSemRelacoes(){
        // Ação - operar no banco
        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste").build();

        // Ação - operar no banco
        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());

        repository.deleteById(salvo.getId());
        // Verificação - A ação ocorreu?
   
        Optional<Egresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
    @Test
    public void deveVerificarRemoverEgressoSalvoComRelacoes(){
        // Cenário
        Contato ex1 = Contato.builder().nome("Empresa1Egresso").url_logo("https:\\\\logo1.com").build();
        Contato ex2 = Contato.builder().nome("Empresa2Egresso").url_logo("https:\\\\logo2.com").build();
        Set<Contato> contatos_a_salvar = Set.of(ex1, ex2);
        Set<Contato> contatos_salvos = Set.of(contatoRepository.save(ex1), contatoRepository.save(ex2));

        Egresso egresso_a_salvar = Egresso.builder().nome("João")
                                                    .email("joao.silva@testmail.com")
                                                    .cpf("11111111111")
                                                    .url_foto("https//foto.joao.com")
                                                    .resumo("Egresso de teste")
                                                    .contatos(contatos_salvos)
                                                    .build();

        Egresso salvo = repository.save(egresso_a_salvar);
   
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso_a_salvar.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso_a_salvar.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso_a_salvar.getCpf(), salvo.getCpf());
        Assertions.assertEquals(egresso_a_salvar.getUrl_foto(), salvo.getUrl_foto());
        Assertions.assertEquals(egresso_a_salvar.getResumo(), salvo.getResumo());
        Assertions.assertEquals(contatos_a_salvar, contatos_salvos);
        Assertions.assertEquals(egresso_a_salvar.getContatos(), salvo.getContatos());

        // Ação - operar no banco
        repository.deleteById(salvo.getId());

        // Verificação - A ação ocorreu?
        Optional<Egresso> query = repository.findById(salvo.getId());
        Assertions.assertTrue(query.isEmpty());
    }
    // @Test
    // public void test2(){
    //     Egresso e = repository.findById(Long.valueOf(88)).get();
    //     repository.delete(e);
    //     Assertions.assertTrue(depoimentoRepository.findAllByEgresso(e).isEmpty());
    // }
}