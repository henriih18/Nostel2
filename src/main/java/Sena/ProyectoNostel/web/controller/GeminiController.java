package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.service.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<?> generarActa(@RequestBody JsonNode body) {
        try {
            // Extraer el contenido del prompt
            String contenido;
            if (body.has("messages")) {
                JsonNode messages = body.get("messages");
                if (messages.isArray() && messages.size() > 0 && messages.get(0).has("content")) {
                    contenido = messages.get(0).get("content").asText();
                } else {
                    return ResponseEntity.badRequest().body("El campo 'messages' debe ser un array no vacío con un campo 'content'");
                }
            } else if (body.has("prompt")) {
                JsonNode promptNode = body.get("prompt");
                if (promptNode.isObject() && promptNode.has("messages")) {
                    JsonNode messages = promptNode.get("messages");
                    if (messages.isArray() && messages.size() > 0 && messages.get(0).has("content")) {
                        contenido = messages.get(0).get("content").asText();
                    } else {
                        return ResponseEntity.badRequest().body("El campo 'prompt.messages' debe ser un array no vacío con un campo 'content'");
                    }
                } else if (promptNode.isTextual()) {
                    contenido = promptNode.asText();
                } else {
                    return ResponseEntity.badRequest().body("El campo 'prompt' debe ser un objeto con 'messages' o un string");
                }
            } else {
                return ResponseEntity.badRequest().body("Se requiere el campo 'prompt' o 'messages'");
            }

            // Extraer parámetros contextuales
            Integer idFicha = body.has("idFicha") ? body.get("idFicha").asInt() : null;
            Integer idAprendiz = body.has("idAprendiz") ? body.get("idAprendiz").asInt() : null;

            // Extraer parámetros de generación
            double temp = body.has("temperature") ? body.get("temperature").asDouble(0.2) : 0.2;
            int maxTok = body.has("max_tokens") ? body.get("max_tokens").asInt(1024) :
                    (body.has("maxOutputTokens") ? body.get("maxOutputTokens").asInt(1024) : 1024);
            int cand = body.has("n") ? body.get("n").asInt(1) :
                    (body.has("candidateCount") ? body.get("candidateCount").asInt(1) : 1);

            // Llamar al servicio
            Map<String, Object> resultado = geminiService.generarActa(contenido, temp, maxTok, cand, idFicha, idAprendiz);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}