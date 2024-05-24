package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emissor", nullable = false)
    private Usuario emissor;

    @ManyToOne
    @JoinColumn(name = "destinatario", nullable = false)
    private Usuario destinatario;

    @Column(name = "dataenvio",nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false, length = 255)
    private String conteudo;
}