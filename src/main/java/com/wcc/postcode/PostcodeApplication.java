package com.wcc.postcode;

import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class PostcodeApplication {
	private static final Logger log = LoggerFactory.getLogger(PostcodeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PostcodeApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner setupUKPostCodes(UKPostCodeRepository repository) {
//		return args -> {
//			log.info("Load database");
//			try (BufferedReader reader = Files.newBufferedReader(Paths.get("ukpostcodes.csv"))) {
//				reader.lines().skip(1).forEach(line -> {
//					var values = line.split(",");
//					if (values.length == 4) {
//						repository.save(new UKPostCodeEntity(values));
//					}
//				});
//				log.info("Database loaded");
//			}
//		};
//	}
}
