package com.example.EncryptDecrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class EncryptDecryptApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncryptDecryptApplication.class, args);
	}

}
