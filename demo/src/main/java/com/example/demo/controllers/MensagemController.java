package com.example.demo.controllers;

import com.example.demo.model.Mensagem;
import com.example.demo.repositorio.MensagemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepositorio mensagemRepositorio;

    @GetMapping
    public List<Mensagem> getAllMensagens() {
        return mensagemRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> getMensagemById(@PathVariable Long id) {
        Optional<Mensagem> mensagem = mensagemRepositorio.findById(id);
        return mensagem.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mensagem createMensagem(@RequestBody Mensagem mensagem) {
        return mensagemRepositorio.save(mensagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> updateMensagem(@PathVariable Long id, @RequestBody Mensagem mensagemDetails) {
        Optional<Mensagem> mensagemOptional = mensagemRepositorio.findById(id);

        if (mensagemOptional.isPresent()) {
            Mensagem mensagem = mensagemOptional.get();
            mensagem.setEmissor(mensagemDetails.getEmissor());
            mensagem.setDestinatario(mensagemDetails.getDestinatario());
            mensagem.setDataEnvio(mensagemDetails.getDataEnvio());
            mensagem.setConteudo(mensagemDetails.getConteudo());
            return ResponseEntity.ok(mensagemRepositorio.save(mensagem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensagem(@PathVariable Long id) {
        Optional<Mensagem> mensagemOptional = mensagemRepositorio.findById(id);

        if (mensagemOptional.isPresent()) {
            mensagemRepositorio.delete(mensagemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
