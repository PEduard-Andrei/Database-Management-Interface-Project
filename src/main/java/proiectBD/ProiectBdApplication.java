/** Clasa care ruleaza aplicatia Spring Boot si activeaza securitatea web.
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */
package proiectBD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ProiectBdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProiectBdApplication.class, args);
	}
}