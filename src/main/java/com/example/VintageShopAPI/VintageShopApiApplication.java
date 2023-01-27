package com.example.VintageShopAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class VintageShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VintageShopApiApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}


}
