package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "remedios")
@Table(name = "Remedio")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

@Getter
@Setter
public class Remedio {
	public Remedio(CadastroRemedio dados) {
		this.nome = dados.nome();
		this.ativo = true;
		this.via = dados.via();
		this.lote = dados.lote();
		this.quantidade = dados.quantidade();
		this.validade = dados.validade();
		this.laboratorio = dados.laboratorio();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Via via;
	private String lote;
	private int quantidade;
	private LocalDate validade;
	
	@Enumerated(EnumType.STRING)
	private Laboratorio laboratorio;

	private Boolean ativo;
	
	public void atualizarInformacoes(@Valid DadosAtualizarRemedio dados) {
		if(dados.nome()!=null) {
			this.nome = dados.nome();
		}
		if(dados.via()!=null) {
			this.via = dados.via();
		}
		if(dados.laboratorio()!=null) {
			this.laboratorio = dados.laboratorio();
		}
		
	}
}
