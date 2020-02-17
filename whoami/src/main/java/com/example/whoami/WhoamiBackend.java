package com.example.whoami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WhoamiBackend {
	private static final Logger log = LoggerFactory.getLogger(WhoamiBackend.class);

	public static void main(String[] args) {
		SpringApplication.run(WhoamiBackend.class, args);
	}

}
