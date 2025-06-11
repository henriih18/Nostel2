package Sena.ProyectoNostel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class Application {

	public static void main(String[] args) {

		System.out.println(">> SPRING_DATASOURCE_HOST=" + System.getenv("SPRING_MYSQL_HOST"));
		System.out.println(">> SPRING_DATASOURCE_PORT=" + System.getenv("SPRING_MYSQL_PORT"));
		System.out.println(">> SPRING_DATASOURCE_DATABASE=" + System.getenv("SPRING_MYSQL_DATABASE"));
		System.out.println(">> SPRING_DATASOURCE_USERNAME=" + System.getenv("SPRING_MYSQL_USERNAME"));
		System.out.println(">> SPRING_DATASOURCE_PASSWORD=" + System.getenv("SPRING_MYSQL_PASSWORD"));

		// JWT
		System.out.println(">> JWT_SECRET=" + System.getenv("JWT_SECRET"));

		// SMTP Gmail
		System.out.println(">> SPRING_MAIL_USERNAME=" + System.getenv("SPRING_MAIL_USERNAME"));
		System.out.println(">> SPRING_MAIL_PASSWORD=" + System.getenv("SPRING_MAIL_PASSWORD"));

		// Gemini
		System.out.println(">> GEMINI_ENDPOINT=" + System.getenv("GEMINI_ENDPOINT"));
		System.out.println(">> GEMINI_HOST=" + System.getenv("GEMINI_HOST"));
		System.out.println(">> GEMINI_API_KEY=" + System.getenv("GEMINI_API_KEY"));

		// Frontend URL y CORS
		System.out.println(">> FRONTEND_URL=" + System.getenv("FRONTEND_URL"));
		System.out.println(">> ALLOWED_ORIGINS=" + System.getenv("ALLOWED_ORIGINS"));

		// Puerto asignado por Railway (opcional)
		System.out.println(">> PORT=" + System.getenv("PORT"));
		SpringApplication.run(Application.class, args);

		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("jwt.secret=" + base64Key);*/

	}
	}




