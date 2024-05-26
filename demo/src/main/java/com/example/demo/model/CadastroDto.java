package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record CadastroDto(
	@NotBlank String login,
	@NotBlank String senha,
	@NotBlank String confirmarSenha
) {}
