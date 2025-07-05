package Sena.ProyectoNostel.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
public class CodigoRegistroIns {

    private String codigoInstructor;
    private Instant ultimaGeneracion;

    public CodigoRegistroIns() {
        this.codigoInstructor = generarCodigo();
        this.ultimaGeneracion = Instant.now();
    }

    public String getCodigoInstructor() {
        return codigoInstructor;
    }

    public boolean validar(String codigoIngresado) {
        return codigoInstructor.equals(codigoIngresado);
    }

    /*private String generarCodigo() {
        return "INSTR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }*/

    private String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder("INSTR-");
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }


    @Scheduled(fixedRate = 86400000) // cada 24h  86400000
    public void actualizarCodigo() {
        this.codigoInstructor = generarCodigo();
        this.ultimaGeneracion = Instant.now();
    }

    public long getSegundosRestantes() {
        long totalSegundos = 86400; // 24h  86400
        long pasados = Duration.between(ultimaGeneracion, Instant.now()).getSeconds();
        return Math.max(0, totalSegundos - pasados);
    }
}
