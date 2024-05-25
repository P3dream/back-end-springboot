package com.example.demo.model;
import jakarta.validation.constraints.NotNull;

public record CadastroMensagem(
    Long emissorId,
    Long destinatarioId,
    String dataEnvio,
    @NotNull String conteudo
) {}