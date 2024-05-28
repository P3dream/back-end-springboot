package com.example.demo.repositorio;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Mensagem;
import com.example.demo.model.MensagemComUsuariosDTO;

public interface MensagemRepositorio extends JpaRepository<Mensagem, Long> {

    @Query("SELECT new com.example.demo.model.MensagemComUsuariosDTO(m.id, emissorUsuario.id, emissorUsuario.login, destinatarioUsuario.id, destinatarioUsuario.login, m.dataEnvio, m.conteudo) " +
           "FROM Mensagem m " +
           "JOIN usuarios emissorUsuario ON m.emissor = emissorUsuario.id " +
           "LEFT JOIN usuarios destinatarioUsuario ON m.destinatario = destinatarioUsuario.id " +
           "WHERE m.emissor = :usuarioId OR m.destinatario = :usuarioId OR m.destinatario = -1")
    List<MensagemComUsuariosDTO> findUserMessages(@Param("usuarioId") Long usuarioId);
}