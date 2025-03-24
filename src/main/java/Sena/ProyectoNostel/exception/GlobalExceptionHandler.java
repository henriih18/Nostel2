package Sena.ProyectoNostel.exception;/*
package Sena.ProyectoNostel.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidacionExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        for (ConstraintViolation<?> violacion : ex.getConstraintViolations()) {
            String campo = violacion.getPropertyPath().toString();
            String mensaje = violacion.getMessage();
            errores.put(campo, mensaje);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
*/

/*
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getConstraintViolations().forEach(violacion -> {
            String campo = violacion.getPropertyPath().toString();
            String mensaje = violacion.getMessage();
            errores.put(campo, mensaje);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    //  Maneja errores de integridad de datos (por ejemplo, email duplicado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(IllegalArgumentException ex) {
        Map<String, String> errores = new HashMap<>();

        String mensajeError = ex.getMessage();

        if (mensajeError.contains("correo")) {
            errores.put("correo", "El correo ya está registrado.");
        } else if (mensajeError.contains("documento")) {
            errores.put("documento", "El documento ya está registrado.");
        } else {
            errores.put("general", "mensajeError");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Manejador genérico para otras excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Error interno del servidor: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}*/

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de errores de validación
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getConstraintViolations().forEach(violacion -> {
            String campo = violacion.getPropertyPath().toString();
            String mensaje = violacion.getMessage();
            errores.put(campo, mensaje);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Manejador correcto para errores de clave duplicada
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateKeyException(DataIntegrityViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        String mensajeError = ex.getRootCause().getMessage(); // Obtiene el mensaje de MySQL

        if (mensajeError.contains("correo")) {
            errores.put("correo", "El correo ya está registrado.");
        } else if (mensajeError.contains("documento")) {
            errores.put("documento", "El documento ya está registrado.");
        } else {
            errores.put("general", "Error de integridad en la base de datos.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Manejador genérico para otras excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Error interno del servidor: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}

