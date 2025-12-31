package com.ankitgiri.server_service.processor;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ankitgiri.server_service.dto.TransactionRequestXml;
import com.ankitgiri.server_service.entity.TransactionLog;
import com.ankitgiri.server_service.repository.TransactionLogRepository;

@Service
public class LogTransaction {
	
	@Autowired
	private TransactionLogRepository repository;
	
	@Async("serverTaskExecutor")
	public void logTransaction(TransactionRequestXml request)
	{
		TransactionLog log = new TransactionLog();
		log.setTrxId(request.getTrxId());
		log.setBankId(request.getBankId());
		log.setAmount(request.getAmount());
		log.setStatus("SUCCESS");
		log.setCreatedAt(LocalDateTime.now());

		repository.save(log);
	}

}
