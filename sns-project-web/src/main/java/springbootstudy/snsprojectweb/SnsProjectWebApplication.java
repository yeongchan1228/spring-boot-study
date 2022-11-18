package springbootstudy.snsprojectweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SnsProjectWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsProjectWebApplication.class, args);
	}

}
