package com.akumasoft.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "queque")
@Data

public class queque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private UUID solicitudId;
    private String asunto;
    private String contentHTML;
    
    private String status;
    private String correo_destino;
    private String correo_cc;
    private String correo_bcc;

    private String error_message;
    private int retry_count;

    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaCreado;
    private LocalDateTime  fechaEnviado;
    private boolean procesada;
}
