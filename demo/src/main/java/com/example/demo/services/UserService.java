package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.DadosAlterarUserStatus;
import com.example.demo.model.DadosCadastro;
import com.example.demo.model.DadosMudarSenha;
import com.example.demo.model.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repositorio.findByLogin(username);
	}
	
	public Optional<Usuario> findUserById(Long id) {
        return repositorio.findById(id);
    }

	public Usuario cadastrarUsuario(DadosCadastro dados) {
		Usuario usuarioEncontrado = repositorio.findByLogin(dados.login());
        if (usuarioEncontrado != null) {
        	throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de usuário já está em uso.");
        }
		if (!dados.senha().equals(dados.confirmarSenha())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"As senhas fornecidas não coincidem");
        }
		Usuario usuario = new Usuario();
		usuario.setLogin(dados.login());
		usuario.setSenha(passwordEncoder.encode(dados.senha()));
		usuario.setSenha(passwordEncoder.encode(dados.confirmarSenha()));
		usuario.setIsonline(false);
		return repositorio.save(usuario);
	}	
	
	public Usuario mudarUserStatus(DadosAlterarUserStatus dto) {
        Usuario usuario = repositorio.findById(dto.id())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        usuario.setIsonline(dto.isonline());
        return repositorio.save(usuario);
    }
	
	public void setUserOnline(String username) {
        Usuario usuario = repositorio.findByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }
        usuario.setIsonline(true);
        repositorio.save(usuario);
    }
	
	public Usuario mudarSenha(DadosMudarSenha dto) {
		UserDetails userDetails = loadUserByUsername(dto.login());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, dto.senha(), userDetails.getAuthorities());
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }

        Usuario usuario = repositorio.findByLogin(dto.login());
        System.out.println(usuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        return repositorio.save(usuario);
    }
	
	public boolean excluirUsuario(String login, String senha) {
		UserDetails userDetails = loadUserByUsername(login);
		Usuario usuario = repositorio.findByLogin(senha);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, senha, userDetails.getAuthorities());
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }

        repositorio.delete(usuario);
        return true;
        
	}
	
	public List<Usuario> getUsuariosOnline() {
        return repositorio.findByIsonlineTrue();
    }
}