package com.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan // 掃描servlet組件
@EnableTransactionManagement // Hibernate用
public class MainApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
