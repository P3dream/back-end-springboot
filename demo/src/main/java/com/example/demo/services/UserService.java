package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.DadosAlterarUserStatus;
import com.example.demo.model.DadosAutenticacao;
import com.example.demo.model.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;

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

	public Usuario cadastrarUsuario(DadosAutenticacao dados) {
		Usuario usuario = new Usuario();
		usuario.setLogin(dados.login());
		usuario.setSenha(passwordEncoder.encode(dados.senha()));
		usuario.setIsonline(false);
		return repositorio.save(usuario);
	}	
	
	public Usuario changeUserStatus(DadosAlterarUserStatus dto) {
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
	
	public List<Usuario> getUsuariosOnline() {
        return repositorio.findByIsonlineTrue();
    }
}