package com.PriceMonitoring.AlertService;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
@EnableFeignClients
public class AlertServiceApplication {
	@Value("${RESEND_API_KEY}")
	private String apiKey;

	public static void main(String[] args) {
		SpringApplication.run(AlertServiceApplication.class, args);
	}

	@Bean
	public Resend resend() {
		return new Resend(apiKey);
	}

}
