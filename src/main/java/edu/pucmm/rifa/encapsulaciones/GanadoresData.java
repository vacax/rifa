package edu.pucmm.rifa.encapsulaciones;

import edu.pucmm.rifa.dominios.Ganadores;
import javafx.beans.property.*;

/**
 * Created by vacax on 18/11/16.
 */
public class GanadoresData {

    private Ganadores ganadores;
    private LongProperty id;
    private StringProperty cedula;
    private StringProperty nombre;
    private StringProperty departamento;
    private BooleanProperty habilitado;

    public GanadoresData( Ganadores ganadores){
        this.ganadores  = ganadores;
        setId(new SimpleLongProperty(ganadores.getId()));
        setCedula(new SimpleStringProperty(ganadores.getPoblacionRifa().getCedula()));
        setNombre(new SimpleStringProperty(ganadores.getPoblacionRifa().getNombre()));
        setDepartamento(new SimpleStringProperty(ganadores.getPoblacionRifa().getDepartamento()));
        setHabilitado(new SimpleBooleanProperty(ganadores.isAprobado()));
    }

    public Ganadores getGanadores() {
        return ganadores;
    }

    public void setGanadores(Ganadores ganadores) {
        this.ganadores = ganadores;
    }


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
