package org.example.aiinterviewbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.example.aiinterviewbackend", "common", "modules"})
@EntityScan(basePackages = "modules")
@EnableJpaRepositories(basePackages = "modules")
public class AiInterviewBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiInterviewBackendApplication.class, args);
    }

}
