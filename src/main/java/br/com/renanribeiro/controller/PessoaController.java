package br.com.renanribeiro.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanribeiro.model.Pessoa;
import br.com.renanribeiro.repository.PessoaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@GetMapping("/listar")
	public List<Pessoa> getAllPessoas() {

		return pessoaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {

		ResponseEntity<Pessoa> response;
		Optional<Pessoa> found = pessoaRepository.findById(id);

		if (found.isPresent()) {
			response = new ResponseEntity<Pessoa>(found.get(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PostMapping("/criar")
	public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {

		ResponseEntity<Pessoa> response;

		try {
			pessoa = pessoaRepository.save(pessoa);
			log.info("Pessoa criada com sucesso: {}", pessoa);
			response = new ResponseEntity<Pessoa>(pessoa, HttpStatus.CREATED);
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para criar Pessoa: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao criar Pessoa: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@PutMapping("/editar")
	public ResponseEntity<Pessoa> editPessoa(@RequestBody Pessoa pessoa) {

		ResponseEntity<Pessoa> response;

		try {
			Optional<Pessoa> found = pessoaRepository.findById(pessoa.getId());
			if (found.isPresent()) {
				Pessoa toUpdate = found.get();
				toUpdate = pessoaRepository.save(pessoa);
				response = new ResponseEntity<Pessoa>(toUpdate, HttpStatus.OK);
			} else {
				log.warn("Pessoa com ID = {} não encontrada para editar!", pessoa.getId());
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para editar Pessoa: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao editar Pessoa: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Boolean> deletePessoa(@PathVariable Long id) {

		ResponseEntity<Boolean> response;

		try {
			Optional<Pessoa> found = pessoaRepository.findById(id);
			if (found.isPresent()) {
				pessoaRepository.deleteById(id);
				log.info("Pessoa com ID = {} excluída com sucesso!", id);
				response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {
				log.warn("Pessoa com ID = {} não encontrada para excluir!", id);
				response = new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Falha ao excluir Pessoa: {}", e.getMessage());
			response = new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
}
