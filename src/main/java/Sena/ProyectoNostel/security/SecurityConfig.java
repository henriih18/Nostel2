/*

package Sena.ProyectoNostel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas que no requieren autenticación
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/programas", "/api/programas", "/api/fichas/disponibles", "/fichas/disponibles").permitAll()
                        // Rutas específicas para administradores - ADMIN tiene acceso a todo
                        .requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")

                        // Acceso específico para ADMIN a todas las rutas
                        .requestMatchers("/**").hasRole("ADMIN")

                        // Rutas para instructores
                        .requestMatchers("/api/instructores/**", "/instructores/**").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Rutas para aprendices
                        .requestMatchers(HttpMethod.GET, "/api/aprendices/**", "/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR", "APRENDIZ")
                        .requestMatchers(HttpMethod.POST, "/api/aprendices/**", "/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/aprendices/**", "/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/aprendices/**", "/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Rutas para fichas
                        .requestMatchers(HttpMethod.POST, "/api/fichas", "/fichas").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Rutas públicas que no requieren autenticación
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/fichas", "/api/fichas", "/programas", "/api/programas").permitAll()
                        .requestMatchers(HttpMethod.POST, "/aprendices", "/fichas", "/api/fichas").permitAll()

                        // Rutas para aprendices
                        .requestMatchers(HttpMethod.GET, "/api/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR", "APRENDIZ")

                        // Rutas para instructores y administradores
                        .requestMatchers(HttpMethod.PUT, "/api/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Rutas específicas para instructores
                        .requestMatchers("/api/instructores/**").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Rutas específicas para administradores
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Asegurarse de que el administrador tenga acceso a todo
                        .requestMatchers("/aprendices/**").hasAnyRole("ADMIN", "INSTRUCTOR", "APRENDIZ")
                        .requestMatchers("/instructores/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", "http://localhost:8080")); // Ajustar para tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

*/


package Sena.ProyectoNostel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1) Rutas públicas
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/programas", "/api/programas",
                                "/fichas/disponibles", "/api/fichas/disponibles"
                        ).permitAll()

                        // 2) Rutas de fichas restringidas
                        .requestMatchers(HttpMethod.GET, "/fichas", "/api/fichas")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/fichas", "/api/fichas")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")

                        // 3) Rutas de aprendices
                        .requestMatchers(HttpMethod.GET, "/api/aprendices/**", "/aprendices/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR", "APRENDIZ")
                        .requestMatchers(HttpMethod.POST, "/api/aprendices/**", "/aprendices/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/aprendices/**", "/aprendices/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/aprendices/**", "/aprendices/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")

                        // 4) Rutas de instructores
                        .requestMatchers("/api/instructores/**", "/instructores/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")

                        // 5) Rutas de administración
                        .requestMatchers("/api/admin/**", "/admin/**")
                        .hasRole("ADMIN")

                        // 6) Cualquier otra requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", "http://localhost:8080")); // Ajustado para tu frontend y backend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // Permitir cookies si las usas

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}