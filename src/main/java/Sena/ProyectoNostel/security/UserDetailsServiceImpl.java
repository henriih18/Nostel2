/*
package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // 🔍 Buscar el usuario ADMIN en la base de datos
        */
/*String sql = "SELECT correo, contrasena FROM usuarios WHERE correo = :correo";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("correo", correo);
        List<Object[]> result = query.getResultList();

        if (!result.isEmpty()) {
            Object[] user = result.get(0);
            return new UserDetailsImpl(user[0].toString(), user[1].toString(), "ROLE_ADMIN");
        }*//*


        String sql = "SELECT correo, contrasena FROM usuarios WHERE correo = :correo";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("correo", correo);
        List<Object[]> result = query.getResultList();

// 🛑 Depuración: Imprimir los resultados obtenidos
        System.out.println("Resultados encontrados: " + result.size());

        if (!result.isEmpty()) {
            Object[] user = result.get(0);
            System.out.println("Correo encontrado: " + user[0]); // Verificar si se encuentra el correo
            return new UserDetailsImpl(user[0].toString(), user[1].toString(), "ROLE_ADMIN");
        }

        // 🔍 Buscar en Aprendiz
        Aprendiz aprendiz = aprendizRepository.findByCorreo(correo).orElse(null);
        if (aprendiz != null) {
            return new UserDetailsImpl(aprendiz);
        }

        // 🔍 Buscar en Instructor
        Instructor instructor = instructorRepository.findByCorreo(correo).orElse(null);
        if (instructor != null) {
            return new UserDetailsImpl(instructor);
        }

        // 🚨 Si no se encuentra en ninguna tabla, lanzar excepción
        throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
    }

}
*/

package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación de UserDetailsService que carga datos específicos del usuario
 * desde múltiples fuentes: tabla de usuarios admin, aprendices e instructores.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /*private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final EntityManager entityManager;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    public UserDetailsServiceImpl(
            EntityManager entityManager,
            AprendizRepository aprendizRepository,
            InstructorRepository instructorRepository) {
        this.entityManager = entityManager;
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        logger.debug("Intentando cargar usuario con correo: {}", correo);

        // Buscar el usuario ADMIN en la base de datos
        UserDetails adminUser = buscarUsuarioAdmin(correo);
        if (adminUser != null) {
            logger.debug("Usuario encontrado como ADMIN: {}", correo);
            return adminUser;
        }

        // Buscar en Aprendiz
        Aprendiz aprendiz = aprendizRepository.findByCorreo(correo).orElse(null);
        if (aprendiz != null) {
            logger.debug("Usuario encontrado como APRENDIZ: {}", correo);
            return new UserDetailsImpl(aprendiz);
        }

        // Buscar en Instructor
        Instructor instructor = instructorRepository.findByCorreo(correo).orElse(null);
        if (instructor != null) {
            logger.debug("Usuario encontrado como INSTRUCTOR: {}", correo);
            return new UserDetailsImpl(instructor);
        }

        // Si no se encuentra en ninguna tabla, lanzar excepción
        logger.warn("Usuario no encontrado en ningún repositorio: {}", correo);
        throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo +
                " (verificado en tablas: usuarios, aprendices, instructores)");
    }


    private UserDetails buscarUsuarioAdmin(String correo) {
        String sql = "SELECT correo, contrasena FROM usuarios WHERE correo = :correo";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("correo", correo);

        try {
            List<Object[]> result = query.getResultList();

            logger.debug("Resultados encontrados en tabla usuarios: {}", result.size());

            if (!result.isEmpty()) {
                Object[] user = result.get(0);
                return new UserDetailsImpl(
                        user[0].toString(),
                        user[1].toString(),
                        "ROLE_ADMIN"
                );
            }
        } catch (Exception e) {
            logger.error("Error al consultar la tabla de usuarios admin", e);
        }

        return null;
    }*/
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired

    public UserDetailsServiceImpl(AprendizRepository aprendizRepository, InstructorRepository instructorRepository, JdbcTemplate jdbcTemplate) {
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Primero, intentar cargar desde la tabla usuarios usando JDBC
        try {
            String sql = "SELECT correo, contrasena, rol FROM usuarios WHERE correo = ?";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, username);

            if (result != null && !result.isEmpty()) {
                String correo = (String) result.get("correo");
                String contrasena = (String) result.get("contrasena");
                String rol = (String) result.get("rol");

                return User.builder()
                        .username(correo)
                        .password(contrasena)
                        .authorities(new SimpleGrantedAuthority(rol))
                        .build();
            }
        } catch (Exception e) {
            // Si hay un error o no se encuentra en la tabla usuarios, continuar con las otras tablas
        }

        // Si no se encuentra en usuarios, buscar en aprendices
        Optional<Aprendiz> aprendiz = aprendizRepository.findByCorreo(username);
        if (aprendiz.isPresent()) {
            return buildUserDetailsFromAprendiz(aprendiz.get());
        }

        // Si no se encuentra en aprendices, buscar en instructores
        Optional<Instructor> instructor = instructorRepository.findByCorreo(username);
        if (instructor.isPresent()) {
            return buildUserDetailsFromInstructor(instructor.get());
        }

        // Si no se encuentra en ninguna tabla, lanzar excepción
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }

    private UserDetails buildUserDetailsFromAprendiz(Aprendiz aprendiz) {
        return User.builder()
                .username(aprendiz.getCorreo())
                .password(aprendiz.getContrasena())
                .authorities(new SimpleGrantedAuthority("ROLE_APRENDIZ"))
                .build();
    }

    private UserDetails buildUserDetailsFromInstructor(Instructor instructor) {
        return User.builder()
                .username(instructor.getCorreo())
                .password(instructor.getContrasena())
                .authorities(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"))
                .build();
    }
}
