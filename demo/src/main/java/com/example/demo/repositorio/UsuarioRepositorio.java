package com.example.demo.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);
	List<Usuario> findByIsonlineTrue();
}
