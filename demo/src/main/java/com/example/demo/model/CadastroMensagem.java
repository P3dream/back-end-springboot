package com.example.demo.model;

import jakarta.validation.constraints.NotNull;

public record CadastroMensagem(
    Long emissorId,
    Long destinatarioId,
    @NotNull String conteudo
) {}