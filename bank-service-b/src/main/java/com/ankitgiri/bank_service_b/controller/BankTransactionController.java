package com.ankitgiri.bank_service_b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankitgiri.bank_service_b.dto.TransactionRequestDto;
import com.ankitgiri.bank_service_b.dto.TransactionResponseDto;
import com.ankitgiri.bank_service_b.service.BankTransactionService;

@RestController
@RequestMapping("/bank")
public class BankTransactionController {

	@Autowired
	private BankTransactionService service;

	@PostMapping("/transaction")
	public TransactionResponseDto processTransaction(@RequestBody TransactionRequestDto request) {
		return service.handleTransaction(request);
	}
}
