package br.com.renanribeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanribeiro.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

}
