package com.example.demo.repositorio;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Mensagem;

public interface MensagemRepositorio extends JpaRepository<Mensagem, Long>{

	List<Mensagem> findByEmissorOrDestinatario(Long emissorId, Long destinatarioId);
	
}
