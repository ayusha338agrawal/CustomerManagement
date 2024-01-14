package com.inn.customerMgmt;

import static com.inn.customerMgmt.security.user.Role.ADMIN;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.inn.customerMgmt.security.auth.AuthenticationService;
import com.inn.customerMgmt.security.auth.RegisterRequest;

import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableOpenApi
@SpringBootApplication
public class CustomerMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMgmtApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());
		};
	}

}
