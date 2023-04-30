package br.com.renanribeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanribeiro.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
