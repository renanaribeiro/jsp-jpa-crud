package br.com.renanribeiro;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.renanribeiro.model.Pessoa;
import br.com.renanribeiro.model.Projeto;
import br.com.renanribeiro.repository.PessoaRepository;
import br.com.renanribeiro.repository.ProjetoRepository;
import br.com.renanribeiro.util.RiscoEnum;
import br.com.renanribeiro.util.StatusEnum;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class ApplicationTests {
	    
	@Autowired
	PessoaRepository pessoaRepo;
	
    @Autowired
    ProjetoRepository projetoRepo;
    
    private static Pessoa pessoaTest = new Pessoa();
    private static Projeto projetoTest = new Projeto();
	
	@Test
	@Order(1)
    public void createPessoa() {
		pessoaTest.setNome("JUnit");
		pessoaTest.setCpf("12345");
		pessoaTest.setDataNascimento(LocalDate.now());
		pessoaTest.setFuncionario(false);
		
		pessoaTest = pessoaRepo.save(pessoaTest);
		
		assertTrue(pessoaTest.getId() > 0L);
    }
	
	@Test
	@Order(2)
	public void editPessoa() {
		pessoaTest.setNome("JUnit Test");
		pessoaTest = pessoaRepo.save(pessoaTest);
		
		Optional<Pessoa> updated = pessoaRepo.findById(pessoaTest.getId());
		
		assertAll("Pessoa editada", 
			() -> assertEquals("JUnit Test", updated.get().getNome()),
			() -> assertFalse(updated.get().getNome().equalsIgnoreCase("JUnit"))
		);
	}
	
	@Test
	@Order(6) // Executa depois que o CRUD de Projetos termina
	public void deletePessoa() {
		Long oldId = pessoaTest.getId();
		pessoaRepo.delete(pessoaTest);
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		List<Pessoa> pessoas = pessoaRepo.findAll();
		Pessoa shouldntExist = pessoas.stream().filter(p -> p.getId().equals(oldId)).findAny().orElse(null);
		Optional<Pessoa> byIdNull = pessoaRepo.findById(oldId);
		
		assertAll("Pessoa excluída com sucesso!", 
			() -> assertNull(shouldntExist),
			() -> assertFalse(byIdNull.isPresent())
		);
	}
	
	@Test
	@Order(3)
	public void createProjeto() {
		
		Pessoa gerente = new Pessoa();
		gerente.setNome("Gerente do Projeto");
		gerente.setCpf("123.456.789-00");
		gerente.setDataNascimento(LocalDate.now());
		gerente.setFuncionario(true);
		gerente = pessoaRepo.save(gerente);
		
		projetoTest.setNome("Teste JUnit");
		projetoTest.setDataInicio(LocalDate.of(2023, 4, 29));
		projetoTest.setDataPrevisaoFim(LocalDate.of(2023, 5, 2));
		projetoTest.setDataFim(LocalDate.now());
		projetoTest.setDescricao("Teste unitário usando JUnit");
		projetoTest.setStatus(StatusEnum.EmAnalise);
		projetoTest.setOrcamento(12.000);
		projetoTest.setRisco(RiscoEnum.Baixo);
		projetoTest.setGerente(gerente);
		
		projetoTest = projetoRepo.save(projetoTest);
		
		assertAll("Criado com sucesso!",
			() -> assertTrue(projetoTest.getId() > 0l),
			() -> assertEquals(projetoTest.getRisco(), RiscoEnum.Baixo)
		);
	}
	
	@Test
	@Order(4)
	public void shouldFailToEditProjeto() {
		Pessoa oldGerente = projetoTest.getGerente();
		
		projetoTest.setGerente(null);
		Throwable exception = assertThrows(Exception.class, () -> projetoRepo.save(projetoTest));
		assertTrue(exception.getMessage().startsWith("Could not commit JPA transaction"));
		
		projetoTest.setGerente(oldGerente); // Necessário para o teste a seguir...
	}
	
	@Test
	@Order(5)
	public void shouldDeleteProjetoAndPessoa() {
		Pessoa gerente = projetoTest.getGerente();
		Long oldGerenteId = gerente.getId();
		Long oldProjetoId = projetoTest.getId();
		
		projetoRepo.delete(projetoTest);
		pessoaRepo.delete(gerente);
		
		Optional<Pessoa> pessoaNull = pessoaRepo.findById(oldGerenteId);
		Optional<Projeto> projetoNull = projetoRepo.findById(oldProjetoId);
		
		assertFalse(pessoaNull.isPresent() && projetoNull.isPresent());
	}
}
