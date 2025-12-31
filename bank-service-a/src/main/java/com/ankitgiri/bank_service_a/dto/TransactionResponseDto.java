package com.ankitgiri.bank_service_a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

	private String trxId;
	private String status;
	private String message;

}