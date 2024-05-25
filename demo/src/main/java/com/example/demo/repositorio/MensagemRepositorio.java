package com.example.demo.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Mensagem;

public interface MensagemRepositorio extends JpaRepository<Mensagem, Long>{

	//List<Mensagem> findAllByisOnlineTrue();
	
}
