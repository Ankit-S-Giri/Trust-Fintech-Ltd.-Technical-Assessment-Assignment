package com.ankitgiri.bank_service_b.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestXml {

	@JacksonXmlProperty(localName = "TrxId")
	private String trxId;

	@JacksonXmlProperty(localName = "BankId")
	private String bankId;

	@JacksonXmlProperty(localName = "CustomerId")
	private Long customerId;

	@JacksonXmlProperty(localName = "FromAccount")
	private String fromAccount;

	@JacksonXmlProperty(localName = "ToAccount")
	private String toAccount;

	@JacksonXmlProperty(localName = "Amount")
	private Double amount;

	@JacksonXmlProperty(localName = "Currency")
	private String currency;

	@JacksonXmlProperty(localName = "Timestamp")
	private String timestamp;

}
