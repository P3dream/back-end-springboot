package com.example.demo.controllers;

import com.example.demo.model.Mensagem;
import com.example.demo.repositorio.MensagemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepositorio mensagemRepositorio;

    @GetMapping
    @Operation(summary = "Obter todas as mensagens")
    public List<Mensagem> getAllMensagens() {
        return mensagemRepositorio.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma mensagem pelo ID")
    @ApiResponse(responseCode = "200", description = "Mensagem encontrada", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Mensagem.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = Mensagem.class))
    })
    @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    public ResponseEntity<Mensagem> getMensagemById(@Parameter(description = "ID da mensagem") @PathVariable Long id) {
        Optional<Mensagem> mensagem = mensagemRepositorio.findById(id);
        return mensagem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar uma nova mensagem")
    @ApiResponse(responseCode = "200", description = "Mensagem criada com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Mensagem.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = Mensagem.class))
    })
    public Mensagem createMensagem(@RequestBody Mensagem mensagem) {
        return mensagemRepositorio.save(mensagem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma mensagem pelo ID")
    @ApiResponse(responseCode = "200", description = "Mensagem atualizada com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Mensagem.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = Mensagem.class))
    })
    @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    public ResponseEntity<Mensagem> updateMensagem(@Parameter(description = "ID da mensagem") @PathVariable Long id,
                                                    @RequestBody Mensagem mensagemDetails) {
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
    @Operation(summary = "Excluir uma mensagem pelo ID")
    @ApiResponse(responseCode = "204", description = "Mensagem excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    public ResponseEntity<Void> deleteMensagem(@Parameter(description = "ID da mensagem") @PathVariable Long id) {
        Optional<Mensagem> mensagemOptional = mensagemRepositorio.findById(id);

        if (mensagemOptional.isPresent()) {
            mensagemRepositorio.delete(mensagemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
