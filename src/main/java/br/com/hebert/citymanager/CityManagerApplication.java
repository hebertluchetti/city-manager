package br.com.hebert.citymanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "City Mangager API", version = "2.0", description = "City Manager Information"))
public class CityManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityManagerApplication.class, args);
	}

}
