package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Remedio;
import com.example.demo.repositorio.RemedioRepositorio;

@Service
public class RemedioService {
	@Autowired
    private final RemedioRepositorio remedioRepositorio;

    public RemedioService(RemedioRepositorio remedioRepositorio) {
        this.remedioRepositorio = remedioRepositorio;
    }

    public void insertRemedio(Remedio remedio) {
    	remedioRepositorio.save(remedio);
    }
}