package gdghufs.jandies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JandiesBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JandiesBeApplication.class, args);
    }

}
