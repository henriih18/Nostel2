package Sena.ProyectoNostel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class Application {

	public static void main(String[] args) {
		System.out.println(">> SPRING_DATASOURCE_URL=" + System.getenv("SPRING_DATASOURCE_URL"));
		System.out.println(">> SPRING_DATASOURCE_USERNAME=" + System.getenv("SPRING_DATASOURCE_USERNAME"));
		System.out.println(">> SPRING_DATASOURCE_PASSWORD=" + System.getenv("SPRING_DATASOURCE_PASSWORD"));
		SpringApplication.run(Application.class, args);

		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("jwt.secret=" + base64Key);*/
	}
	}




