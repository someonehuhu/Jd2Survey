package by.yatsukovich;


import by.yatsukovich.configuration.HibernateConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "by.yatsukovich")
@Import(
        {HibernateConfiguration.class}
)
public class SpringBootAppStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppStarter.class, args);
    }
}