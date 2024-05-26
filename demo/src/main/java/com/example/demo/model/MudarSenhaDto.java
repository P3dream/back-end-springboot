package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record MudarSenhaDto(
	@NotBlank String login,
	@NotBlank String senha,
	@NotBlank String novaSenha
) {}
