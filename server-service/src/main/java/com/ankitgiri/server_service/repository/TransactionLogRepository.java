package com.ankitgiri.server_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ankitgiri.server_service.entity.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
