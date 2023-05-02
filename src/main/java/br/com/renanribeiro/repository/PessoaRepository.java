package br.com.renanribeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanribeiro.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findAllByFuncionarioTrue();

}
