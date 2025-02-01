package Sena.ProyectoNostel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"Sena.ProyectoNostel.domain.service", "Sena.ProyectoNostel.persistence", "Sena.ProyectoNostel.web.controller"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
