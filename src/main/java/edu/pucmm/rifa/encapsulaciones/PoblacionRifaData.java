package edu.pucmm.rifa.encapsulaciones;

import edu.pucmm.rifa.dominios.PoblacionRifa;
import javafx.beans.property.*;

/**
 * Created by vacax on 06/11/16.
 */
public class PoblacionRifaData {

    private PoblacionRifa poblacionRifa;
    private LongProperty id;
    private StringProperty cedula;
    private StringProperty nombre;
    private StringProperty departamento;
    private BooleanProperty habilitado;

    public PoblacionRifaData(PoblacionRifa poblacionRifa){
        this.poblacionRifa = poblacionRifa;
        setId(new SimpleLongProperty(poblacionRifa.getId()));
        setCedula(new SimpleStringProperty(poblacionRifa.getCedula()));
        setNombre(new SimpleStringProperty(poblacionRifa.getNombre()));
        setDepartamento(new SimpleStringProperty(poblacionRifa.getDepartamento()));
        setHabilitado(new SimpleBooleanProperty(poblacionRifa.isHabilitado()));
    }


    public PoblacionRifa getPoblacionRifa() {
        return poblacionRifa;
    }

    /*public void setPoblacionRifa(PoblacionRifa poblacionRifa) {
        this.poblacionRifa = poblacionRifa;
    }*/

    public LongProperty getId() {
        return id;
    }

    public void setId(LongProperty id) {
        this.id = id;
    }

    public StringProperty getCedula() {
        return cedula;
    }

    public void setCedula(StringProperty cedula) {
        this.cedula = cedula;
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getDepartamento() {
        return departamento;
    }

    public void setDepartamento(StringProperty departamento) {
        this.departamento = departamento;
    }

    public BooleanProperty getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(BooleanProperty habilitado) {
        this.habilitado = habilitado;
    }
}
