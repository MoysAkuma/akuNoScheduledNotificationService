package com.akumasoft.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akumasoft.model.queque;

public interface quequeRepository extends JpaRepository<queque, UUID> {
    List<queque> findByStatus(String status);

    void updateStatusByIds(List<UUID> ids, String status);
}
