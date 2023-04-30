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

import br.com.renanribeiro.model.Membros;
import br.com.renanribeiro.repository.MembrosRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/membros")
public class MembrosController {
	
	@Autowired
	MembrosRepository membrosRepository;
	
	@GetMapping("/listar")
	public List<Membros> getAllMembross() {

		return membrosRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Membros> getMembrosById(@PathVariable Long id) {

		ResponseEntity<Membros> response;
		Optional<Membros> found = membrosRepository.findById(id);

		if (found.isPresent()) {
			response = new ResponseEntity<Membros>(found.get(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PostMapping("/criar")
	public ResponseEntity<Membros> createMembros(@RequestBody Membros membros) {

		ResponseEntity<Membros> response;

		try {
			membros = membrosRepository.save(membros);
			log.info("Membros criada com sucesso: {}", membros);
			response = new ResponseEntity<Membros>(membros, HttpStatus.CREATED);
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para criar Membros: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao criar Membros: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@PutMapping("/editar")
	public ResponseEntity<Membros> editMembros(@RequestBody Membros membros) {

		ResponseEntity<Membros> response;

		try {
			Optional<Membros> found = membrosRepository.findById(membros.getId());
			if (found.isPresent()) {
				Membros toUpdate = found.get();
				toUpdate = membrosRepository.save(toUpdate);
				response = new ResponseEntity<Membros>(toUpdate, HttpStatus.OK);
			} else {
				log.warn("Membros com ID = {} não encontrada para editar!", membros.getId());
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ConstraintViolationException ec) {
			log.error("Objeto inválido para editar Membros: {}", ec.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Falha ao editar Membros: {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Boolean> deleteMembros(@PathVariable Long id) {

		ResponseEntity<Boolean> response;

		try {
			Optional<Membros> found = membrosRepository.findById(id);
			if (found.isPresent()) {
				membrosRepository.deleteById(id);
				log.info("Membros com ID = {} excluída com sucesso!", id);
				response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {
				log.warn("Membros com ID = {} não encontrada para excluir!", id);
				response = new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Falha ao excluir Membros: {}", e.getMessage());
			response = new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
}
