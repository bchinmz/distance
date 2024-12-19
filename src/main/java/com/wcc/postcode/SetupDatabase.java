package com.wcc.postcode;

import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SetupDatabase implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SetupDatabase.class);

    @Autowired
    private UKPostCodeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Load database");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("ukpostcodes.csv"))) {
            reader.lines().skip(1).forEach(line -> {
                var values = line.split(",");
                if (values.length == 4) {
                    repository.save(new UKPostCodeEntity(values));
                }
            });
            log.info("Database loaded");
        }
    }
}
