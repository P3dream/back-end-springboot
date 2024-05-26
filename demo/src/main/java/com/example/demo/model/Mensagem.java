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

    @Column(name = "emissor", nullable = false)
    private long emissor;

    @Column(name = "destinatario", nullable = false)
    private long destinatario;

    @Column(name = "dataenvio", nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false, length = 255)
    private String conteudo;
}