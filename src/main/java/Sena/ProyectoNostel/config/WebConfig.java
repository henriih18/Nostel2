package Sena.ProyectoNostel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173", // Frontend (sin / al final)
                        "http://localhost:8080"  // Backend (si es necesario)
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Incluir OPTIONS
                .allowedHeaders("Authorization", "Content-Type", "Accept")
                .allowCredentials(true) // Permitir cookies/autenticación
                .maxAge(3600); // Cachear config CORS por 1 hora
    }
}

