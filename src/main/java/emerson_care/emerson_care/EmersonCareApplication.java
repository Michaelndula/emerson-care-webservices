package emerson_care.emerson_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"emerson_care.emerson_care"}, exclude = {ErrorMvcAutoConfiguration.class})
public class EmersonCareApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmersonCareApplication.class, args);
	}
}
