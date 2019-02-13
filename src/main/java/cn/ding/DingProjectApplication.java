package cn.ding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingProjectApplication.class, args);
    }
}
