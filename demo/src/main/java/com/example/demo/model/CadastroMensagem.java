package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record CadastroMensagem(
    @NotBlank String conteudo
    
) {}
