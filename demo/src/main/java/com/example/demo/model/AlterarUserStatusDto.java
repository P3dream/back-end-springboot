package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public record AlterarUserStatusDto (
	@NotBlank Long id,
	@NotBlank boolean isonline
) {}
