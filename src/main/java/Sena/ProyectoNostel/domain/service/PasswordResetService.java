package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.repository.PasswordResetTokenRepository;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.PasswordResetToken;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Generar un token de restablecimiento y enviar correo
    public void createPasswordResetToken(String email) {
        logger.info("Procesando solicitud de restablecimiento para email: {}", email);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(email);
        if (usuarioOpt.isEmpty()) {
            logger.warn("No se encontró usuario con el correo: {}", email);
            throw new RuntimeException("No se encontró un usuario con ese correo.");
        }

        tokenRepository.findByEmail(email).ifPresent(tokenRepository::delete);
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
        PasswordResetToken resetToken = new PasswordResetToken(token, email, expiryDate);
        logger.info("Token creado con email: {}, token: {}, expiryDate: {}", resetToken.getEmail(), resetToken.getToken(), resetToken.getExpiryDate());
        tokenRepository.save(resetToken);
        sendResetEmail(email, token);
    }

    // Enviar correo con enlace de restablecimiento
    private void sendResetEmail(String email, String token) {
        String resetUrl = frontendUrl + "/password-reset/reset?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Restablecimiento de Contraseña - Proyecto Nostel");
        message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace: \n" + resetUrl +
                "\nEste enlace expirará en 1 hora.");
        mailSender.send(message);
        logger.info("Correo de restablecimiento enviado a: {}", email);
    }

    // Validar token y cambiar contraseña
    public void resetPassword(String token, String newPassword) {
        logger.info("Validando token de restablecimiento: {}", token);
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            logger.warn("Token no encontrado: {}", token);
            throw new RuntimeException("Token inválido.");
        }

        PasswordResetToken resetToken = tokenOpt.get();
        logger.info("Token encontrado - Email: {}, ExpiryDate: {}, Used: {}", resetToken.getEmail(), resetToken.getExpiryDate(), resetToken.isUsed());
        if (resetToken.isUsed() || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.warn("Token inválido - Expirado o usado: {}", token);
            throw new RuntimeException("El token ha expirado o ya fue usado.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(resetToken.getEmail());
        if (usuarioOpt.isEmpty()) {
            logger.warn("Usuario no encontrado con email: {}", resetToken.getEmail());
            throw new RuntimeException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        logger.info("Contraseña restablecida para usuario con email: {}", resetToken.getEmail());
    }
}