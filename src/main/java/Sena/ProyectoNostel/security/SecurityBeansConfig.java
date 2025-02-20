package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeansConfig {
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var aprendiz = aprendizRepository.findByCorreo(username);
            if (aprendiz.isPresent()) {
                return new UserDetailsImpl(aprendiz.get());
            }

            var instructor = instructorRepository.findByCorreo(username);
            if (instructor.isPresent()) {
                return new UserDetailsImpl(instructor.get());
            }

            throw new RuntimeException("Usuario no encontrado");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        //authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
