package com.example.demo.model;

import java.time.LocalDateTime;

public record MensagemComUsuariosDTO(
	    Long id,
	    Long emissorId,
	    String emissorLogin,
	    Long destinatarioId,
	    String destinatarioLogin,
	    LocalDateTime dataEnvio,
	    String conteudo
	) {}