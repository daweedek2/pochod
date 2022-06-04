package cz.kostka.pochod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PochodApplication {

    public static void main(String[] args) {
        SpringApplication.run(PochodApplication.class, args);
    }

}
