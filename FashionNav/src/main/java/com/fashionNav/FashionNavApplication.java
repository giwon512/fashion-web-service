package com.fashionNav;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.fashionNav.repository")
@SpringBootApplication
public class FashionNavApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionNavApplication.class, args);
	}

}
