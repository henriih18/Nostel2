package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
//@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();


        // Omitir validación JWT para endpoints públicos
        if (path.startsWith("/auth/") || path.equals("/auth") ||
                path.startsWith("/actuator") ||
                path.equals("/fichas/disponibles") || path.equals("/programas") ||
                path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs/") ||
                request.getMethod().equals("OPTIONS")) {
            log.debug("Omitiendo validación JWT para la ruta: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Encabezado de autorización no válido o ausente: {}", authHeader);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            log.debug("Token recibido: {}", jwt);

            final String userCorreo = jwtService.extractCorreo(jwt);
            log.debug("Correo extraído del token: {}", userCorreo);

            // Extraer idUsuario desde el token
            Integer idUsuario = jwtService.extractIdUsuario(jwt);
            if (idUsuario == null) {
                log.error("El token no contiene el claim 'idUsuario'");
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Token JWT no contiene idUsuario");
                return;
            }

            if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userCorreo);
                log.debug("Usuario cargado: {}", userDetails.getUsername());

                // Verificar si el token es válido
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Manejar roles y permisos
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    String rol = jwtService.extractRole(jwt);
                    if (rol != null) {
                        authorities.add(new SimpleGrantedAuthority(rol));
                        log.debug("Rol extraído del token: {}", rol);
                        if (rol.equals("ROLE_ADMIN")) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
                            authorities.add(new SimpleGrantedAuthority("ROLE_APRENDIZ"));
                            log.debug("Usuario es ROLE_ADMIN, añadiendo roles ROLE_INSTRUCTOR y ROLE_APRENDIZ");
                        }
                    }

                    // Verificar authorities adicionales en el token
                    Map<String, Object> claims = jwtService.extractAllClaims(jwt);
                    if (claims.containsKey("authorities")) {
                        String authoritiesStr = (String) claims.get("authorities");
                        if (authoritiesStr != null) {
                            String[] authArray = authoritiesStr.split(",");
                            for (String auth : authArray) {
                                String authority = auth.trim();
                                if (!authority.startsWith("ROLE_")) {
                                    authority = "ROLE_" + authority;
                                }
                                authorities.add(new SimpleGrantedAuthority(authority));
                                log.debug("Autoridad adicional añadida: {}", authority);
                            }
                        }
                    }

                    if (authorities.isEmpty()) {
                        userDetails.getAuthorities().forEach(authority -> {
                            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
                            log.debug("Usando autoridad de userDetails: {}", authority.getAuthority());
                        });
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Autenticación exitosa para: {}, idUsuario: {}", userCorreo, idUsuario);
                } else {
                    log.warn("Token no válido para el usuario: {}", userCorreo);
                }
            }
        } catch (SignatureException e) {
            log.error("Error de firma JWT: {}", e.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token JWT inválido: Firma incorrecta");
            return;
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token JWT expirado");
            return;
        } catch (MalformedJwtException e) {
            log.error("Token JWT malformado: {}", e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Token JWT malformado");
            return;
        } catch (Exception e) {
            log.error("Error procesando token JWT: {}", e.getMessage(), e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error procesando token JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
