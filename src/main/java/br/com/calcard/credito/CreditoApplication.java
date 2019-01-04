package br.com.calcard.credito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditoApplication.class, args);
	}

}

