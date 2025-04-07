package br.com.acervofacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AcervoFacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcervoFacilApplication.class, args);
	}
}
