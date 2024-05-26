package com.example.demo.services;

import com.example.demo.model.CadastroMensagem;
import com.example.demo.model.Mensagem;
import com.example.demo.model.Usuario;
import com.example.demo.repositorio.MensagemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepositorio mensagemRepositorio;
    
    @Autowired
    private UserService userService;

    public List<Mensagem> getAllMensagens() {
        return mensagemRepositorio.findAll();
    }    
    
    public Mensagem createMensagem(CadastroMensagem mensagem) {
        Usuario emissor = userService.findUserById(mensagem.emissorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emissor não encontrado"));

        Usuario destinatario = userService.findUserById(mensagem.destinatarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destinatário não encontrado"));

        Mensagem dbMensagem = new Mensagem();
        dbMensagem.setEmissor(emissor.getId());
        dbMensagem.setDestinatario(destinatario.getId());
        dbMensagem.setConteudo(mensagem.conteudo());
        dbMensagem.setDataEnvio(LocalDateTime.now());
        return mensagemRepositorio.save(dbMensagem);
    }
    
    public List<Mensagem> getMensagensByUsuarioId(Long usuarioId) {
        return mensagemRepositorio.findByEmissorOrDestinatario(usuarioId, usuarioId);
    }
    
}
