package com.akumasoft.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akumasoft.model.programados;

public interface programadosRepository extends JpaRepository<programados, UUID> {
    List<programados> findByProgramado_dateLessThanEqualAndProcesadaFalse(LocalDateTime fecha);
}
