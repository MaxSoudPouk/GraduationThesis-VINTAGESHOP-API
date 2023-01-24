package com.example.VintageShopAPI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

@SpringBootApplication
public class VintageShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VintageShopApiApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {
			//	System.out.println("In Startup main");
//			threadRead threadRead = new threadRead();
//			threadRead.contextInitialized(null);
		};
	}

}
