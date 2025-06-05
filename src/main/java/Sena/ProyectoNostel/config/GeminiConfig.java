package Sena.ProyectoNostel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeminiConfig {

    @Value("${gemini.endpoint}")
    private String geminiEndpoint;

    @Value("${gemini.host}")
    private String geminiHost;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Bean
    public WebClient geminiClient() {
        return WebClient.builder()
                .baseUrl(geminiEndpoint)
                .defaultHeader("x-goog-api-key", apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}