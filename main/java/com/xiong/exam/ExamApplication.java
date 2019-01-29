package com.xiong.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xiong.exam.mapper")
public class ExamApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(ExamApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成!");
		System.out.printf("服务启动完成");
	}
}

