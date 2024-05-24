package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.CadastroRemedio;
import com.example.demo.model.DadosAtualizarRemedio;
import com.example.demo.model.DadosDetalhamentoRemedio;
import com.example.demo.model.DadosListagemRemedio;
import com.example.demo.model.Remedio;
import com.example.demo.repositorio.RemedioRepositorio;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")

public class remedioController {
	
	@Autowired
	private RemedioRepositorio repositorio;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid CadastroRemedio dados, UriComponentsBuilder uriBuilder) {
		Remedio remedio = new Remedio(dados);
		repositorio.save(remedio);
		
		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}
	
	@GetMapping("/listarAtivos")
	public ResponseEntity<List<DadosListagemRemedio>> listarAtivos(){
		var lista = repositorio.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/listarTodos")
	public ResponseEntity<List<DadosListagemRemedio>> listarTodos(){
		var lista = repositorio.findAll().stream().map(DadosListagemRemedio::new).toList();
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizarRemedio dados) {
		var remedio = repositorio.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable long id) {
		repositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable long id) {
		repositorio.getReferenceById(id).setAtivo(false);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar(@PathVariable long id) {
		repositorio.getReferenceById(id).setAtivo(true);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> detalhar(@PathVariable long id) {
		var remedio = repositorio.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	
	
}
