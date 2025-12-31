package com.ankitgiri.server_service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@JacksonXmlRootElement
@Data
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
