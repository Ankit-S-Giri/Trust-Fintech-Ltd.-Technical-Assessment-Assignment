package com.ankitgiri.server_service;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class ServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerServiceApplication.class, args);
	}

	@Bean
	public Executor serverTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(100000);
		executor.setThreadNamePrefix("SeverExec--");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

}
