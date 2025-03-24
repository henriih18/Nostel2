/*



package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {




        String path = request.getServletPath();
        // Skip JWT validation for permitted endpoints
        if (path.equals("/fichas") || path.equals("/api/fichas") || request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userCorreo;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userCorreo = jwtService.extractCorreo(jwt);

        if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userCorreo);


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request, response);
    }
}


*/


/*
package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Skip JWT validation for permitted endpoints
        if (path.equals("/fichas") || path.equals("/api/fichas") ||
                path.startsWith("/auth/") || path.equals("/auth") ||
                request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userCorreo;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userCorreo = jwtService.extractCorreo(jwt);

        if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userCorreo);

            // Verificar si el token es válido
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Extraer claims para verificar si es admin
                Map<String, Object> claims = jwtService.extractAllClaims(jwt);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                // Verificar si es admin y agregar todos los roles necesarios
                String rol = (String) claims.get("rol");
                if (rol != null) {
                    authorities.add(new SimpleGrantedAuthority(rol));

                    // Si es admin, agregar todos los roles posibles
                    if (rol.equals("ROLE_ADMIN")) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
                        authorities.add(new SimpleGrantedAuthority("ROLE_APRENDIZ"));
                    }
                }

                // Verificar si hay authorities adicionales en el token
                if (claims.containsKey("authorities")) {
                    String authoritiesStr = (String) claims.get("authorities");
                    if (authoritiesStr != null) {
                        String[] authArray = authoritiesStr.split(",");
                        for (String auth : authArray) {
                            if (!auth.startsWith("ROLE_")) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_" + auth));
                            } else {
                                authorities.add(new SimpleGrantedAuthority(auth));
                            }
                        }
                    }
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}*/

/*
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
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Skip JWT validation for permitted endpoints
        if (path.equals("/fichas") || path.equals("/api/fichas") ||
                path.startsWith("/auth/") || path.equals("/auth") ||
                path.startsWith("/api/auth/") || path.equals("/api/auth") ||
                request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);

            // Imprimir el token para depuración
            System.out.println("Token recibido: " + jwt);

            final String userCorreo = jwtService.extractCorreo(jwt);

            if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userCorreo);

                // Verificar si el token es válido
                if (jwtService.isTokenValid(jwt, String.valueOf(userDetails))) {
                    // Extraer claims para verificar si es admin
                    Map<String, Object> claims = jwtService.extractAllClaims(jwt);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    // Verificar si es admin y agregar todos los roles necesarios
                    String rol = (String) claims.get("rol");
                    if (rol != null) {
                        authorities.add(new SimpleGrantedAuthority(rol));

                        // Si es admin, agregar todos los roles posibles
                        if (rol.equals("ROLE_ADMIN")) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
                            authorities.add(new SimpleGrantedAuthority("ROLE_APRENDIZ"));
                        }
                    }

                    // Verificar si hay authorities adicionales en el token
                    if (claims.containsKey("authorities")) {
                        String authoritiesStr = (String) claims.get("authorities");
                        if (authoritiesStr != null) {
                            String[] authArray = authoritiesStr.split(",");
                            for (String auth : authArray) {
                                if (!auth.startsWith("ROLE_")) {
                                    authorities.add(new SimpleGrantedAuthority("ROLE_" + auth));
                                } else {
                                    authorities.add(new SimpleGrantedAuthority(auth));
                                }
                            }
                        }
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Autenticación exitosa para: " + userCorreo);
                }
            }
        } catch (SignatureException e) {
            System.err.println("Error de firma JWT: " + e.getMessage());
            System.err.println("El token no fue firmado con la clave secreta correcta");
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT malformado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando token JWT: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}*/
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Skip JWT validation for permitted endpoints
        if (path.equals("/fichas") || path.equals("/api/fichas") ||
                path.startsWith("/auth/") || path.equals("/auth") ||
                path.startsWith("/api/auth/") || path.equals("/api/auth") ||
                request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);

            // Imprimir el token para depuración
            System.out.println("Token recibido: " + jwt);

            final String userCorreo = jwtService.extractCorreo(jwt);

            if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;

                try {
                    // Intentar cargar el usuario desde el servicio
                    userDetails = this.userDetailsService.loadUserByUsername(userCorreo);
                } catch (UsernameNotFoundException e) {
                    // Si el usuario es "admin" y no se encuentra, crear un UserDetails manualmente
                    if ("admin".equals(userCorreo) && jwtService.isAdmin(jwt)) {
                        log.info("Creando usuario admin manualmente ya que no se encontró en la base de datos");

                        Collection<SimpleGrantedAuthority> adminAuthorities = new ArrayList<>();
                        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
                        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_APRENDIZ"));

                        userDetails = User.builder()
                                .username("admin")
                                .password("") // No se necesita contraseña para validación JWT
                                .authorities(adminAuthorities)
                                .build();
                    } else {
                        // Si no es admin, propagar la excepción
                        throw e;
                    }
                }

                // Verificar si el token es válido
                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                    // Extraer claims para verificar si es admin
                    Map<String, Object> claims = jwtService.extractAllClaims(jwt);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    // Verificar si es admin y agregar todos los roles necesarios
                    String rol = (String) claims.get("rol");
                    if (rol != null) {
                        authorities.add(new SimpleGrantedAuthority(rol));

                        // Si es admin, agregar todos los roles posibles
                        if (rol.equals("ROLE_ADMIN")) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
                            authorities.add(new SimpleGrantedAuthority("ROLE_APRENDIZ"));
                        }
                    }

                    // Verificar si hay authorities adicionales en el token
                    if (claims.containsKey("authorities")) {
                        String authoritiesStr = (String) claims.get("authorities");
                        if (authoritiesStr != null) {
                            String[] authArray = authoritiesStr.split(",");
                            for (String auth : authArray) {
                                if (!auth.startsWith("ROLE_")) {
                                    authorities.add(new SimpleGrantedAuthority("ROLE_" + auth));
                                } else {
                                    authorities.add(new SimpleGrantedAuthority(auth));
                                }
                            }
                        }
                    }

                    // Si no hay autoridades extraídas del token, usar las del userDetails
                    if (authorities.isEmpty()) {
                        userDetails.getAuthorities().forEach(authority -> {
                            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
                        });
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Autenticación exitosa para: " + userCorreo);
                }
            }
        } catch (SignatureException e) {
            System.err.println("Error de firma JWT: " + e.getMessage());
            System.err.println("El token no fue firmado con la clave secreta correcta");
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT malformado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando token JWT: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}