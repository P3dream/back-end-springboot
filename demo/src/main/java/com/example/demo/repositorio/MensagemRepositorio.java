package com.example.demo.repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Mensagem;
import com.example.demo.model.Remedio;

public interface MensagemRepositorio extends JpaRepository<Mensagem, Long>{

	//List<Mensagem> findAllByisOnlineTrue();
	
}
