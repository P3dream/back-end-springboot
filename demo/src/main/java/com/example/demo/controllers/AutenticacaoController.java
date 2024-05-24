package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DadosAlterarUserStatus;
import com.example.demo.model.DadosAutenticacao;
import com.example.demo.model.DadosTokenJWT;
import com.example.demo.model.Usuario;
import com.example.demo.services.TokenService;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
	    try {
	        System.out.println("Tentativa de login com usuário: " + dados.login());
	        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
	        var autenticacao = manager.authenticate(token);
	        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
	        userService.setUserOnline(dados.login());
	        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	    } catch (Exception e) {
	        System.out.println("Erro de autenticação: " + e.getMessage());
	        return ResponseEntity.status(401).body("Credenciais inválidas");
	    }
	}

	@PostMapping("/register")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid DadosAutenticacao dados) {
		Usuario novoUsuario = userService.cadastrarUsuario(dados);
		return ResponseEntity.ok(novoUsuario);
	}
	
	@PatchMapping("/status")
	public ResponseEntity<?> cadastrarUsuario(DadosAlterarUserStatus dto){
		Usuario novoUsuario = userService.changeUserStatus(dto);
		return ResponseEntity.ok(novoUsuario);
	}
	
	@GetMapping("/online")
    public ResponseEntity<List<Usuario>> getUsuariosOnline() {
        List<Usuario> usuariosOnline = userService.getUsuariosOnline();
        return ResponseEntity.ok(usuariosOnline);
    }
	
	
}
