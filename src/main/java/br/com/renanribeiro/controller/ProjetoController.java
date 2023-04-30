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

import br.com.renanribeiro.model.Projeto;
import br.com.renanribeiro.repository.ProjetoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/projetos")
public class ProjetoController {
	
	@Autowired
	ProjetoRepository projetoRepository;
	
	@GetMapping("/listar")
	public List<Projeto> getAllProjetos() {

		return projetoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {

		ResponseEntity<Projeto> response;
		Optional<Projeto> found = projetoRepository.findById(id);

		if (found.isPresent()) {
			response = new ResponseEntity<Projeto>(found.get(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PostMapping("/criar")
	public ResponseEntity<Projeto> createProjeto(@RequestBody Projeto projeto) {

		ResponseEntity<Projeto> response;

		try {
			projeto = projetoRepository.save(projeto);
			log.info("Projeto criada com sucesso: {}", projeto);
			response = new ResponseEntity<Projeto>(projeto, HttpStatus.CREATED);
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para criar Projeto: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao criar Projeto: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@PutMapping("/editar")
	public ResponseEntity<Projeto> editProjeto(@RequestBody Projeto projeto) {

		ResponseEntity<Projeto> response;

		try {
			Optional<Projeto> found = projetoRepository.findById(projeto.getId());
			if (found.isPresent()) {
				Projeto toUpdate = found.get();
				toUpdate = projetoRepository.save(toUpdate);
				response = new ResponseEntity<Projeto>(toUpdate, HttpStatus.OK);
			} else {
				log.warn("Projeto com ID = {} não encontrada para editar!", projeto.getId());
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para editar Projeto: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao editar Projeto: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Boolean> deleteProjeto(@PathVariable Long id) {

		ResponseEntity<Boolean> response;

		try {
			Optional<Projeto> found = projetoRepository.findById(id);
			if (found.isPresent()) {
				projetoRepository.deleteById(id);
				log.info("Projeto com ID = {} excluída com sucesso!", id);
				response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {
				log.warn("Projeto com ID = {} não encontrada para excluir!", id);
				response = new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Falha ao excluir Projeto: {}", e.getMessage());
			response = new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
}
