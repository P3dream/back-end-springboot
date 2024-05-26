package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record CriarMensagemDto(
    @NotBlank Long emissorId,
    @NotBlank Long destinatarioId,
    @NotBlank String conteudo
) {}