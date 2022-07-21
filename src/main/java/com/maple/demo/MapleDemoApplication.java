package com.maple.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 笑小枫
 */
@ServletComponentScan
@SpringBootApplication
public class MapleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapleDemoApplication.class, args);
	}

}
