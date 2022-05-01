package com.mustar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mustar.mapper")
public class pupuApplication {

	public static void main(String[] args) {
		// 注意这里要指向原先用main方法执行的Application启动类
		SpringApplication.run(pupuApplication.class, args);
	}

}
