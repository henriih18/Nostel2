/*
package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;

import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextHolder;



import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);
    private final WebClient webClient;
    private final AprendizService aprendizService;
    private final InstructorRepository instructorRepository;


    public GeminiService(WebClient geminiClient, AprendizService aprendizService, InstructorRepository instructorRepository , JwtService jwtService) {
        this.webClient = geminiClient;
        this.aprendizService = aprendizService;
        this.instructorRepository = instructorRepository;

    }

    public Map<String, Object> generarActa(String prompt, double temperature, int maxTokens,
                                                int candidateCount, Integer idFicha, Integer idAprendiz) {
        // Extraer el número de documento del prompt usando una expresión regular
        Integer documento = null;
        Pattern pattern = Pattern.compile("documento\\s+(\\d+)");
        Matcher matcher = pattern.matcher(prompt);
        if (matcher.find()) {
            String documentoStr = matcher.group(1);
            if (!documentoStr.matches("\\d{6,10}")) {
                throw new IllegalArgumentException("El número de documento debe tener entre 6 y 10 dígitos.");
            }
            try {
                documento = Integer.parseInt(documentoStr);
                log.info("Número de documento extraído del prompt: {}", documento);
            } catch (NumberFormatException e) {
                log.error("Error al convertir el documento: {}", documentoStr, e);
                throw new IllegalArgumentException("El número de documento debe ser un entero válido.");
            }
        }

        // Si no se proporciona el número de documento, solicitarlo
        if (documento == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "incomplete");
            response.put("message", "Por favor, proporciona el número de documento del aprendiz.");
            return response;
        }

        // Buscar al aprendiz por número de documento
        Integer finalDocumento = documento;
        AprendizDTO aprendiz = aprendizService.findByDocumento(documento)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un aprendiz con el número de documento: " + finalDocumento));
        log.info("Aprendiz encontrado: id={}, nombres={}, numeroFicha={}",
                aprendiz.getIdAprendiz(), aprendiz.getNombres(), aprendiz.getNumeroFicha());

        // recupera el correo del usuario autenticado
        String correoAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        if (correoAutenticado == null || correoAutenticado.isBlank()) {
            throw new IllegalArgumentException("No se pudo determinar el correo del usuario autenticado.");
        }
        log.info("Correo de usuario autenticado: {}", correoAutenticado);

        // ——————————————————————————————————————————————————————
        // 3) Buscar Instructor por correo
        // ——————————————————————————————————————————————————————
        Instructor instructor = instructorRepository.findByCorreo(correoAutenticado)
                .orElseThrow(() -> new RuntimeException(
                        "Instructor no encontrado para el usuario autenticado (" + correoAutenticado + ")"));
        log.info("Instructor encontrado: idInstructor={}, correo={}, área={}",
                instructor.getIdInstructor(), instructor.getCorreo(), instructor.getArea());


        // Obtener el área del instructor
        String areaInstructor = instructor.getArea();
        if (areaInstructor == null || areaInstructor.trim().isEmpty()) {
            log.warn("El área del instructor está vacía o nula, usando 'General' como fallback.");
            areaInstructor = "General";
        }
        log.info("Área del instructor: {}", areaInstructor);

        // Personalizar el prompt para generar contenido dinámico
        String contexto = "Eres un asistente especializado en el sistema Nostel del SENA Colombia. " +
                "Genera una actividad complementaria formal y profesional para un instructor en el área de " + areaInstructor + ". " +
                "La respuesta debe estar estructurada en líneas separadas con los siguientes encabezados exactos: " +
                "'Nombre del Comité:', 'Agenda o Puntos para Desarrollar:', 'Objetivos de la Reunión:', 'Desarrollo de la Reunión:', 'Conclusiones:'. " +
                "Proporciona contenido único, detallado y relevante para cada campo, basado únicamente en el área de " + areaInstructor + " y el aprendiz (" + aprendiz.getNombres() + ", Ficha " + aprendiz.getNumeroFicha() + "). " +
                "No uses ejemplos predefinidos ni estructuras fijas; genera contenido original según el contexto.";

        String promptFinal = contexto + " " + prompt + " El aprendiz tiene el nombre " + aprendiz.getNombres() +
                " y pertenece a la ficha " + aprendiz.getNumeroFicha() + ".";
        if (idFicha != null) {
            promptFinal += " Considera que la ficha con ID " + idFicha + " está asociada.";
        }
        log.info("Prompt final enviado a Gemini: {}", promptFinal);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", promptFinal))
                )
        ));
        requestBody.put("generationConfig", Map.of(
                "temperature", temperature,
                "maxOutputTokens", maxTokens,
                "candidateCount", candidateCount
        ));

        JsonNode resp = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(),
                        cr -> cr.bodyToMono(String.class)
                                .flatMap(err -> Mono.error(new RuntimeException("Error de Gemini: " + err))))
                .bodyToMono(JsonNode.class)
                .block();

        // Extraer el texto generado y estructurarlo
        String rawResponse = resp
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
        log.info("Respuesta de Gemini: {}", rawResponse);

        Map<String, Object> actividad = new HashMap<>();
        try {
            String[] lines = rawResponse.split("\n");
            StringBuilder currentSection = new StringBuilder();
            String currentHeader = null;

            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("Nombre del Comité:") || line.startsWith("Agenda o Puntos para Desarrollar:") ||
                        line.startsWith("Objetivos de la Reunión:") || line.startsWith("Desarrollo de la Reunión:") ||
                        line.startsWith("Conclusiones:")) {
                    if (currentHeader != null && !currentSection.toString().trim().isEmpty()) {
                        actividad.put(currentHeader, currentSection.toString().trim());
                    }
                    currentHeader = line.substring(0, line.indexOf(":")).trim();
                    currentSection = new StringBuilder(line.substring(line.indexOf(":") + 1).trim());
                } else if (currentHeader != null) {
                    currentSection.append("\n").append(line);
                }
            }
            // Añadir la última sección
            if (currentHeader != null && !currentSection.toString().trim().isEmpty()) {
                actividad.put(currentHeader, currentSection.toString().trim());
            }

            // Validar campos mínimos con mensaje detallado
            if (!actividad.containsKey("Nombre del Comité") || !actividad.containsKey("Agenda o Puntos para Desarrollar") ||
                    !actividad.containsKey("Objetivos de la Reunión") || !actividad.containsKey("Desarrollo de la Reunión") ||
                    !actividad.containsKey("Conclusiones")) {
                throw new RuntimeException("La respuesta de Gemini no contiene todos los campos esperados. Respuesta recibida: " + rawResponse);
            }

            // Renombrar claves para consistencia con el frontend
            Map<String, Object> formattedActividad = new HashMap<>();
            formattedActividad.put("nombreComite", actividad.get("Nombre del Comité"));
            formattedActividad.put("agenda", actividad.get("Agenda o Puntos para Desarrollar"));
            formattedActividad.put("objetivos", actividad.get("Objetivos de la Reunión"));
            formattedActividad.put("desarrollo", actividad.get("Desarrollo de la Reunión"));
            formattedActividad.put("conclusiones", actividad.get("Conclusiones"));
            formattedActividad.put("idAprendiz", aprendiz.getIdAprendiz());
            formattedActividad.put("status", "success");
            log.info("Actividad generada: {}", formattedActividad);
            return formattedActividad;
        } catch (Exception e) {
            log.error("Error al parsear la respuesta de Gemini: {}", e.getMessage());
            throw new RuntimeException("Error al parsear la respuesta de Gemini: " + e.getMessage());
        }
    }
}*/

package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);
    private final WebClient webClient;
    private final AprendizService aprendizService;
    private final InstructorRepository instructorRepository;

    public GeminiService(WebClient geminiClient, AprendizService aprendizService, InstructorRepository instructorRepository, JwtService jwtService) {
        this.webClient = geminiClient;
        this.aprendizService = aprendizService;
        this.instructorRepository = instructorRepository;
    }

    public Map<String, Object> generarActa(String prompt, double temperature, int maxTokens,
                                           int candidateCount, Integer idFicha, Integer idAprendiz) {
        // Extraer el número de documento del prompt usando una expresión regular
        Integer documento = null;
        Pattern pattern = Pattern.compile("documento\\s+(\\d+)");
        Matcher matcher = pattern.matcher(prompt);
        if (matcher.find()) {
            String documentoStr = matcher.group(1);
            if (!documentoStr.matches("\\d{6,10}")) {
                throw new IllegalArgumentException("El número de documento debe tener entre 6 y 10 dígitos.");
            }
            try {
                documento = Integer.parseInt(documentoStr);
                log.info("Número de documento extraído del prompt: {}", documento);
            } catch (NumberFormatException e) {
                log.error("Error al convertir el documento: {}", documentoStr, e);
                throw new IllegalArgumentException("El número de documento debe ser un entero válido.");
            }
        }

        // Si no se proporciona el número de documento, solicitarlo
        if (documento == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "incomplete");
            response.put("message", "Por favor, proporciona el número de documento del aprendiz.");
            return response;
        }

        // Buscar al aprendiz por número de documento
        Integer finalDocumento = documento;
        AprendizDTO aprendiz = aprendizService.findByDocumento(documento)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un aprendiz con el número de documento: " + finalDocumento));
        log.info("Aprendiz encontrado: id={}, nombres={}, numeroFicha={}",
                aprendiz.getIdAprendiz(), aprendiz.getNombres(), aprendiz.getNumeroFicha());

        // Recupera el correo del usuario autenticado
        String correoAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        if (correoAutenticado == null || correoAutenticado.isBlank()) {
            throw new IllegalArgumentException("No se pudo determinar el correo del usuario autenticado.");
        }
        log.info("Correo de usuario autenticado: {}", correoAutenticado);

        // Buscar Instructor por correo
        Instructor instructor = instructorRepository.findByCorreo(correoAutenticado)
                .orElseThrow(() -> new RuntimeException(
                        "Instructor no encontrado para el usuario autenticado (" + correoAutenticado + ")"));
        log.info("Instructor encontrado: idInstructor={}, correo={}, área={}",
                instructor.getIdInstructor(), instructor.getCorreo(), instructor.getArea());

        // Obtener el área del instructor
        String areaInstructor = instructor.getArea();
        if (areaInstructor == null || areaInstructor.trim().isEmpty()) {
            log.warn("El área del instructor está vacía o nula, usando 'General' como fallback.");
            areaInstructor = "General";
        }
        log.info("Área del instructor: {}", areaInstructor);

        // Determinar si es una actividad complementaria o un plan de mejoramiento
        boolean esActividadComplementaria = prompt.toLowerCase().contains("actividad") ||
                prompt.toLowerCase().contains("actividad complementaria");
        boolean esPlanMejoramiento = prompt.toLowerCase().contains("plan") ||
                prompt.toLowerCase().contains("plan de mejoramiento");

        // Si no se detecta ninguna palabra clave, solicitar especificación
        String tipoDocumento;
        if (!esActividadComplementaria && !esPlanMejoramiento) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "incomplete");
            response.put("message", "Por favor, especifica si deseas generar una actividad complementaria o un plan de mejoramiento (usa 'actividad' o 'plan' en el prompt).");
            return response;
        }
        tipoDocumento = esActividadComplementaria ? "actividad complementaria" : "plan de mejoramiento";
        log.info("Tipo de documento detectado: {}", tipoDocumento);

        // Personalizar el contexto según el tipo de documento
        String contexto;
        if (tipoDocumento.equals("actividad complementaria")) {
            contexto = "Eres un asistente especializado en el sistema Nostel del SENA Colombia. " +
                    "Genera una actividad complementaria formal y profesional para un instructor en el área de " + areaInstructor + ". " +
                    "La respuesta debe estar estructurada en líneas separadas con los siguientes encabezados exactos: " +
                    "'Nombre del Comité:', 'Agenda o Puntos para Desarrollar:', 'Objetivos de la Reunión:', 'Desarrollo de la Reunión:', 'Conclusiones:'. " +
                    "Proporciona contenido único, detallado y relevante para cada campo, basado únicamente en el área de " + areaInstructor + " y el aprendiz (" + aprendiz.getNombres() + ", Ficha " + aprendiz.getNumeroFicha() + "). " +
                    "No uses ejemplos predefinidos ni estructuras fijas; genera contenido original según el contexto.";
        } else {
            contexto = "Eres un asistente especializado en el sistema Nostel del SENA Colombia. " +
                    "Genera un plan de mejoramiento formal y profesional para un instructor en el área de " + areaInstructor + ". " +
                    "La respuesta debe estar estructurada en líneas separadas con los siguientes encabezados exactos: " +
                    "'Nombre del Comité:', 'Agenda o Puntos para Desarrollar:', 'Objetivos de la Reunión:', 'Desarrollo de la Reunión:', 'Conclusiones:'. " +
                    "Proporciona contenido único, detallado y relevante para cada campo, basado únicamente en el área de " + areaInstructor + " y el aprendiz (" + aprendiz.getNombres() + ", Ficha " + aprendiz.getNumeroFicha() + "), enfocado en mejorar el desempeño del aprendiz. " +
                    "No uses ejemplos predefinidos ni estructuras fijas; genera contenido original según el contexto.";
        }

        String promptFinal = contexto + " " + prompt + " El aprendiz tiene el nombre " + aprendiz.getNombres() +
                " y pertenece a la ficha " + aprendiz.getNumeroFicha() + ".";
        if (idFicha != null) {
            promptFinal += " Considera que la ficha con ID " + idFicha + " está asociada.";
        }
        log.info("Prompt final enviado a Gemini: {}", promptFinal);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", promptFinal))
                )
        ));
        requestBody.put("generationConfig", Map.of(
                "temperature", temperature,
                "maxOutputTokens", maxTokens,
                "candidateCount", candidateCount
        ));

        JsonNode resp = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(),
                        cr -> cr.bodyToMono(String.class)
                                .flatMap(err -> Mono.error(new RuntimeException("Error de Gemini: " + err))))
                .bodyToMono(JsonNode.class)
                .block();

        // Extraer el texto generado y estructurarlo
        String rawResponse = resp
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
        log.info("Respuesta de Gemini: {}", rawResponse);

        Map<String, Object> documentoGenerado = new HashMap<>();
        try {
            String[] lines = rawResponse.split("\n");
            StringBuilder currentSection = new StringBuilder();
            String currentHeader = null;

            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("Nombre del Comité:") || line.startsWith("Agenda o Puntos para Desarrollar:") ||
                        line.startsWith("Objetivos de la Reunión:") || line.startsWith("Desarrollo de la Reunión:") ||
                        line.startsWith("Conclusiones:")) {
                    if (currentHeader != null && !currentSection.toString().trim().isEmpty()) {
                        documentoGenerado.put(currentHeader, currentSection.toString().trim());
                    }
                    currentHeader = line.substring(0, line.indexOf(":")).trim();
                    currentSection = new StringBuilder(line.substring(line.indexOf(":") + 1).trim());
                } else if (currentHeader != null) {
                    currentSection.append("\n").append(line);
                }
            }
            // Añadir la última sección
            if (currentHeader != null && !currentSection.toString().trim().isEmpty()) {
                documentoGenerado.put(currentHeader, currentSection.toString().trim());
            }

            // Validar campos mínimos con mensaje detallado
            if (!documentoGenerado.containsKey("Nombre del Comité") || !documentoGenerado.containsKey("Agenda o Puntos para Desarrollar") ||
                    !documentoGenerado.containsKey("Objetivos de la Reunión") || !documentoGenerado.containsKey("Desarrollo de la Reunión") ||
                    !documentoGenerado.containsKey("Conclusiones")) {
                throw new RuntimeException("La respuesta de Gemini no contiene todos los campos esperados. Respuesta recibida: " + rawResponse);
            }

            // Renombrar claves para consistencia con el frontend
            Map<String, Object> formattedDocumento = new HashMap<>();
            formattedDocumento.put("nombreComite", documentoGenerado.get("Nombre del Comité"));
            formattedDocumento.put("agenda", documentoGenerado.get("Agenda o Puntos para Desarrollar"));
            formattedDocumento.put("objetivos", documentoGenerado.get("Objetivos de la Reunión"));
            formattedDocumento.put("desarrollo", documentoGenerado.get("Desarrollo de la Reunión"));
            formattedDocumento.put("conclusiones", documentoGenerado.get("Conclusiones"));
            formattedDocumento.put("idAprendiz", aprendiz.getIdAprendiz());
            formattedDocumento.put("tipoDocumento", tipoDocumento); // Agregar tipo para que el frontend lo use
            formattedDocumento.put("status", "success");
            log.info("Documento generado: {}", formattedDocumento);
            return formattedDocumento;
        } catch (Exception e) {
            log.error("Error al parsear la respuesta de Gemini: {}", e.getMessage());
            throw new RuntimeException("Error al parsear la respuesta de Gemini: " + e.getMessage());
        }
    }
}


