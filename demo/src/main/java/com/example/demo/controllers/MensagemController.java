package com.example.demo.controllers;

import com.example.demo.model.CriarMensagemDto;
import com.example.demo.model.Mensagem;
import com.example.demo.services.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @GetMapping
    @Operation(summary = "Obter todas as mensagens")
    public List<Mensagem> getAllMensagens() {
        return mensagemService.getAllMensagens();
    }

    @PostMapping
    @Operation(summary = "Criar uma nova mensagem")
    @ApiResponse(responseCode = "200", description = "Mensagem criada com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Mensagem.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = Mensagem.class))
    })
    public Mensagem createMensagem(@RequestBody CriarMensagemDto dto) {
        return mensagemService.createMensagem(dto);
    }
    
    @GetMapping("/{usuarioId}")
    @Operation(summary = "Buscar todas as mensagens enviadas ou recebidas por um usuário")
    @ApiResponse(responseCode = "200", description = "Mensagens encontradas com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Mensagem.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = Mensagem.class))
    })
    public List<Mensagem> getMensagensByUsuarioId(@PathVariable Long usuarioId) {
        return mensagemService.getMensagensByUsuarioId(usuarioId);
    }
}