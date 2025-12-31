package com.ankitgiri.bank_service_b.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

	private Long customerId;
	private String fromAccount;
	private String toAccount;
	private BigDecimal amount;
	private String currency;
}
