package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record DadosTokenJWT(
	@NotBlank String token,
	@NotBlank long id,
	@NotBlank String login
) {}
