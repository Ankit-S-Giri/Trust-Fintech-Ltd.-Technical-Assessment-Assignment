package com.ankitgiri.bank_service_a.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ankitgiri.bank_service_a.dto.TransactionRequestXml;

@Component
public class ServerClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${server.url}")
	private String serverUrl;

	@Async("clientTaskExecutor")
	public void forwardToServer(TransactionRequestXml xmlRequest) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/xml");

			HttpEntity<TransactionRequestXml> entity = new HttpEntity<>(xmlRequest, headers);

			restTemplate.postForObject(serverUrl, entity, String.class);

		} catch (Exception e) {
			System.err.println("ERROR Sending Trx " + xmlRequest.getTrxId() + ": " + e.getMessage());
		}
	}
}
