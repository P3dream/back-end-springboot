package com.example.demo.repositorio;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Mensagem;

public interface MensagemRepositorio extends JpaRepository<Mensagem, Long>{
	
	 @Query("SELECT m FROM Mensagem m WHERE m.emissor = :usuarioId OR m.destinatario = :usuarioId OR m.destinatario = -1")
	 List<Mensagem> findUserMessages(@Param("usuarioId") Long usuarioId);
	
}
