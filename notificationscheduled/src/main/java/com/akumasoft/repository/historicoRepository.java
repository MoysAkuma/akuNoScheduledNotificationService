package com.akumasoft.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akumasoft.model.historico;

public interface historicoRepository extends JpaRepository<historico, UUID> {
    List<historico> findBySolicitudId(UUID solicitudId);
}
