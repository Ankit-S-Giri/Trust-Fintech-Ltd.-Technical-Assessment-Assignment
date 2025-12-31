package com.ankitgiri.server_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankitgiri.server_service.dto.TransactionRequestXml;
import com.ankitgiri.server_service.dto.TransactionResponse;
import com.ankitgiri.server_service.processor.TransactionProcessor;

@Service
public class TransactionService {

	@Autowired
	private TransactionProcessor processor;

	public TransactionResponse submit(TransactionRequestXml request) {
		
		return processor.process(request);
	}
}
