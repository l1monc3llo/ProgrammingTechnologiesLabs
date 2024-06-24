package com.gmx.newCatDeadInsideProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NewCatDeadInsideProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewCatDeadInsideProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println(new BCryptPasswordEncoder().encode("password"));
		};
	}
}
