package br.com.renanribeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanribeiro.model.Membros;

public interface MembrosRepository extends JpaRepository<Membros, Long> {

}
