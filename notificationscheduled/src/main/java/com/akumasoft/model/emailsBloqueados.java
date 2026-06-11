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
@Table(name = "emails_bloqueados")
@Data
public class emailsBloqueados {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String email;
    private LocalDateTime bloqueado_date;
    private boolean activo;
}
