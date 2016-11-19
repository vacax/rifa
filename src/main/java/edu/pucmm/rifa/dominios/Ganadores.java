package edu.pucmm.rifa.dominios;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacax on 22/10/16.
 */
@Entity
public class Ganadores implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Integer id;
    @ManyToOne()
    private PoblacionRifa poblacionRifa;
    @ManyToOne()
    private Usuario generadoPor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;
    private String premio;
    private boolean aprobado;
    @ManyToOne()
    private Rifa rifa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PoblacionRifa getPoblacionRifa() {
        return poblacionRifa;
    }

    public void setPoblacionRifa(PoblacionRifa poblacionRifa) {
        this.poblacionRifa = poblacionRifa;
    }

    public Usuario getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(Usuario generadoPor) {
        this.generadoPor = generadoPor;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Rifa getRifa() {
        return rifa;
    }

    public void setRifa(Rifa rifa) {
        this.rifa = rifa;
    }
}
