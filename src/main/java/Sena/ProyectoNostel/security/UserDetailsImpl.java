package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private String correo;
    private String contrasena;
    private String rol; // Nuevo campo para el rol

    // Constructor para Aprendiz
    public UserDetailsImpl(Aprendiz aprendiz) {
        this.correo = aprendiz.getCorreo();
        this.contrasena = aprendiz.getContrasena();
        this.rol = "ROLE_APRENDIZ"; // Asignar rol directamente
    }

    // Constructor para Instructor
    public UserDetailsImpl(Instructor instructor) {
        this.correo = instructor.getCorreo();
        this.contrasena = instructor.getContrasena();
        this.rol = "ROLE_INSTRUCTOR"; // Asignar rol directamente
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna el rol como una autoridad
        return Collections.singletonList(new SimpleGrantedAuthority(rol));
    }

    // Resto de los métodos se mantienen igual...
    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}