package com.lansoft.fs.esl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lansoft.fs.esl.mapper")
public class EslApplication {

	public static void main(String[] args) {
		SpringApplication.run(EslApplication.class, args);
	}
}
