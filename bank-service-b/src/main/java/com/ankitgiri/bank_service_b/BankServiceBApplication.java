package com.ankitgiri.bank_service_b;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
public class BankServiceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServiceBApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Executor clientTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(50);
		executor.setMaxPoolSize(200);
		executor.setQueueCapacity(100000);
		executor.setThreadNamePrefix("ClientExec-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	@Bean
	public RestTemplate pooledRestTemplate() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(2000);
		connectionManager.setDefaultMaxPerRoute(2000);

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
	}

}
