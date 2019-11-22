/* package com.roj.eztalk.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {
  @Bean
  CommandLineRunner initDatabase(MaterialRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new Material("French I", "an introduction to French", "English", "ROJ", "NONE", "false", 5)));
      log.info("Preloading " + repository.save(new Material("French II","an introduction to French", "English", "ROJ", "NONE", "false", 27)));
    };
  }
} */