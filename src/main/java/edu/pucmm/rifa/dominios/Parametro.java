package edu.pucmm.rifa.dominios;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Parametro implements Serializable {

    public enum TipoParametro{
        ULTIMO_CAMPUS
    }

    @Id
    String codigo;
    String nombre;
    String valor;

    public Parametro(){

    }

    public Parametro(String codigo, String nombre, String valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
