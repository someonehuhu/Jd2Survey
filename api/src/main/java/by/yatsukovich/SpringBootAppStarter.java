package by.yatsukovich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "by.yatsukovich")
public class SpringBootAppStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppStarter.class, args);
    }
}