package kimsungsu.finalToBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinalToBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalToBootApplication.class, args);
	}

}
