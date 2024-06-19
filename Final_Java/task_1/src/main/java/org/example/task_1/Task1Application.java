package org.example.task_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// написать тесты

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "org.example.task_1.repository")
@EntityScan("org.example.task_1.model")
public class Task1Application {
	public static void main(String[] args) {
		SpringApplication.run(Task1Application.class, args);
	}
}
