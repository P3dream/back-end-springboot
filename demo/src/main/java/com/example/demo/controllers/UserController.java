package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.AlterarUserStatusDto;
import com.example.demo.model.CadastroDto;
import com.example.demo.model.DadosTokenJWT;
import com.example.demo.model.LoginDto;
import com.example.demo.model.MudarSenhaDto;
import com.example.demo.model.Usuario;
import com.example.demo.services.TokenService;
import com.example.demo.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuario e Autenticação", description = "Endpoints relacionadas à autenticação e usuário.")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Efetua login e retorna o token JWT")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid LoginDto dados) {
        try {
            var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var autenticacao = manager.authenticate(token);

            var usuario = (Usuario) autenticacao.getPrincipal();
            var tokenJWT = tokenService.gerarToken(usuario);	

            userService.setUserOnline(dados.login());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuario.getId(), usuario.getLogin()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastra um novo usuário")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid CadastroDto dados) {
        Usuario novoUsuario = userService.cadastrarUsuario(dados);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping("/update")
    @Operation(summary = "Muda a senha de um usuário")
    public ResponseEntity<String> mudarSenha(@RequestBody MudarSenhaDto dados) {
        try {
            userService.mudarSenha(dados);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar senha");
        }
    }

    @PutMapping("/status")
    @Operation(summary = "Muda o status do usuário")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody AlterarUserStatusDto dto) {
        Usuario novoUsuario = userService.mudarUserStatus(dto);
        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping("/online")
    @Operation(summary = "Retorna a lista de usuários online")
    public ResponseEntity<List<Usuario>> getUsuariosOnline() {
        List<Usuario> usuariosOnline = userService.getUsuariosOnline();
        return ResponseEntity.ok(usuariosOnline);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Exclui um usuário")
    public ResponseEntity<String> deleteUser(@RequestParam String login, @RequestParam String senha) {
        boolean isDeleted = userService.excluirUsuario(login,senha);
        if (isDeleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid login or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
