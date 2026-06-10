package com.akumasoft.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akumasoft.model.queque;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<queque, UUID> {
    List<queque> findByStatus(String status);
}
