/*
package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.persistence.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private String correo;
    private String contrasena;
    private String rol;

    // Constructor único basado en la entidad Usuario
    public UserDetailsImpl(Usuario usuario) {
        this.correo = usuario.getCorreo();
        this.contrasena = usuario.getContrasena();
        // Aseguramos que el rol tenga el prefijo ROLE_
        this.rol = usuario.getRol().startsWith("ROLE_") ? usuario.getRol() : "ROLE_" + usuario.getRol().toUpperCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(rol));
    }

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
}*/

package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.persistence.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Integer idUsuario; // Añadido para almacenar idUsuario
    private String correo;
    private String contrasena;
    private String rol;

    public UserDetailsImpl(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario(); // Asumiendo que Usuario tiene getIdUsuario()
        this.correo = usuario.getCorreo();
        this.contrasena = usuario.getContrasena();
        this.rol = usuario.getRol().startsWith("ROLE_") ? usuario.getRol() : "ROLE_" + usuario.getRol().toUpperCase();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(rol));
    }

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
