package org.demo.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ResourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceDemoApplication.class, args);
	}
	
	@Component
	class Tester implements CommandLineRunner {

		private static final Logger log = LoggerFactory.getLogger(Tester.class);
		
		private final RestTemplate restTemplate;

		Tester(RestTemplateBuilder restTemplateBuilder, ServerProperties serverProperties) {
			this.restTemplate = restTemplateBuilder
					.rootUri("http://localhost:" + serverProperties.getPort())
					.build();
		}

		@Override
		public void run(String... args) throws Exception {
			log.info("Without JSESSIONID: {}", this.restTemplate.getForObject("/file.txt", String.class));
			log.info("With JSESSIONID: {}", this.restTemplate.getForObject("/file.txt;JSESSIONID=D1FC547DA2B9C51DA0154B550A2D28C3", String.class));
		}
	}

}
