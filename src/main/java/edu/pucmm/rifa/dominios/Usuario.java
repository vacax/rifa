package edu.pucmm.rifa.dominios;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by vacax on 22/10/16.
 */
@Entity
public class Usuario implements Serializable {

    @Id
    private String usuario;
    private String password;
    private String nombre;
    private String email;

    public Usuario() {
    }

    public Usuario(String usuario, String nombre) {
        this.setUsuario(usuario);
        this.setNombre(nombre);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
