package com.AuthService.AuthService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class AuthServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthServiceApplication.class, args);
		String secret = Encoders.BASE64.encode(
				Jwts.SIG.HS256.key().build().getEncoded()
		);
		System.out.println(secret);
	}

	@GetMapping("/auth/hi")
	public String sayHi() {
		return "Hi from Auth Service";
	}

}
