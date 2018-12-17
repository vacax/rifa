package edu.pucmm.rifa.dominios;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vacax on 22/10/16.
 */
@Entity
public class PoblacionRifa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(unique = true)
    private String cedula;
    private String nombre;
    private String departamento;
    private boolean habilitado = true;
    private boolean noPresente = false;
    private boolean ganador = false;

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public boolean isNoPresente() {
        return noPresente;
    }

    public void setNoPresente(boolean noPresente) {
        this.noPresente = noPresente;
    }

    @Override
    public String toString() {
        return "PoblacionRifa{" +
                "id=" + id +
                ", cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", habilitado=" + habilitado +
                ", noPresente=" + noPresente +
                '}';
    }
}
