package edu.pucmm.rifa.encapsulaciones;

public class Departamentos {

    String nombre;
    int cantidad;
    int ganadores;
    int noPresente;

    public Departamentos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getGanadores() {
        return ganadores;
    }

    public void setGanadores(int ganadores) {
        this.ganadores = ganadores;
    }

    public int getNoPresente() {
        return noPresente;
    }

    public void setNoPresente(int noPresente) {
        this.noPresente = noPresente;
    }
}
