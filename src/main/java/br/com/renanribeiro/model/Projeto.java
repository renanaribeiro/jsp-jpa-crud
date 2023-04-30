package br.com.renanribeiro.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;
	
	@Column(name = "data_inicio")
	private LocalDate dataInicio;
	
	@Column(name = "data_previsao_fim")
	private LocalDate dataPrevisaoFim;
	
	@Column(name = "data_fim")
	private LocalDate dataFim;
	
	private String descricao;
	
	private String status;
	
	private double orcamento;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String risco;
	
	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "idgerente")
	private Pessoa gerente;
}
