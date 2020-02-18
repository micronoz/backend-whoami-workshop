package com.example.whoami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;

@SpringBootApplication
public class WhoamiBackend {
	private static final Logger log = LoggerFactory.getLogger(WhoamiBackend.class);

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {

			Configuration val = mapper.readValue(new File("./keys.yml"), Configuration.class);
			log.info(String.format("'%s'", val.getAdminUser()));
			AdminController.adminId = val.getAdminUser();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		GameController.isGameOn = false;
		SpringApplication.run(WhoamiBackend.class, args);
	}

}
