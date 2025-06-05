package Sena.ProyectoNostel;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
//@ComponentScan(basePackages = {"Sena.ProyectoNostel.domain.service", "Sena.ProyectoNostel.persistence", "Sena.ProyectoNostel.web.controller"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("jwt.secret=" + base64Key);*/
	}
	}



