package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")

public class HelloWorld {
	@GetMapping
	public String olaMundo() {
		return "Ola mundo";
	}	
}
