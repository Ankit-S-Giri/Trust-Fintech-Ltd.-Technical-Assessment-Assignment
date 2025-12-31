package com.ankitgiri.server_service.controller;

import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankitgiri.server_service.dto.TransactionRequestXml;
import com.ankitgiri.server_service.dto.TransactionResponse;
import com.ankitgiri.server_service.service.TransactionService;

@RestController
@RequestMapping("/server/transaction")
public class ServerTransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/process", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionResponse processTransaction(@RequestBody TransactionRequestXml request)
			throws InterruptedException, ExecutionException {

		return transactionService.submit(request);
	}
}
