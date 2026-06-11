package com.akumasoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akumasoft.model.emailsBloqueados;

public interface emailsBloqueadosRepository extends JpaRepository<emailsBloqueados, Long> {
    boolean existsByEmail(String email);
    List<emailsBloqueados> findAll();
}
