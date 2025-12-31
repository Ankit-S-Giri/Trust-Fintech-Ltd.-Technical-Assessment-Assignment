package com.ankitgiri.bank_service_a.service;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ankitgiri.bank_service_a.client.ServerClient;
import com.ankitgiri.bank_service_a.dto.TransactionRequestDto;
import com.ankitgiri.bank_service_a.dto.TransactionRequestXml;
import com.ankitgiri.bank_service_a.dto.TransactionResponseDto;
import com.ankitgiri.bank_service_a.util.TrxIdGenerator;

@Service
public class BankTransactionService {

	@Autowired
	private ServerClient serverClient;
	@Autowired
	private ModelMapper modelMapper;

	@Value("${bank.id}")
	private String bankId;

	public TransactionResponseDto handleTransaction(TransactionRequestDto request) {

		String trxId = TrxIdGenerator.generate();
		TransactionRequestXml xmlRequest = modelMapper.map(request, TransactionRequestXml.class);
		xmlRequest.setTrxId(trxId);
		xmlRequest.setBankId(bankId);
		xmlRequest.setTimestamp(LocalDateTime.now().toString());

		serverClient.forwardToServer(xmlRequest);

		return new TransactionResponseDto(trxId, "FORWARDED", "Transaction forwarded to server");
	}
}
