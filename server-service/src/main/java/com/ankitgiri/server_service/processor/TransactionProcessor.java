package com.ankitgiri.server_service.processor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankitgiri.server_service.dto.TransactionRequestXml;
import com.ankitgiri.server_service.dto.TransactionResponse;

@Service
public class TransactionProcessor {

	private final Set<String> processedTrxIds = ConcurrentHashMap.newKeySet();

	@Autowired
	LogTransaction log;

	public TransactionResponse process(TransactionRequestXml request) {

		long startTime = System.currentTimeMillis();

		if (!processedTrxIds.add(request.getTrxId())) {
			return new TransactionResponse(request.getTrxId(), "FAILED", "Duplicate Transaction", 0);
		}

		if (request.getAmount() == null || request.getAmount() <= 0) {
			return new TransactionResponse(request.getTrxId(), "FAILED", "Invalid Amount", 0);
		}

		log.logTransaction(request);

		long processingTime = System.currentTimeMillis() - startTime;

		return new TransactionResponse(request.getTrxId(), "SUCCESS", "Completed", processingTime);
	}
}
