package com.ankitgiri.bank_service_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankitgiri.bank_service_a.dto.TransactionRequestDto;
import com.ankitgiri.bank_service_a.dto.TransactionResponseDto;
import com.ankitgiri.bank_service_a.service.BankTransactionService;

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
