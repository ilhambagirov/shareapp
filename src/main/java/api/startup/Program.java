package api.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories("api.repositories")
@ComponentScan("api.*")
@EntityScan("api.entities")
@EnableAsync(proxyTargetClass = true) // Enable asynchronous execution
public class Program {
    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }
}
