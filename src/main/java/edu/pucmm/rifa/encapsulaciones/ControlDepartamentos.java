package edu.pucmm.rifa.encapsulaciones;

import edu.pucmm.rifa.dominios.Ganadores;

import java.util.*;

public class ControlDepartamentos {

    Map<String,Departamentos> listaDepartamentos=new HashMap<>();
    Ganadores ultimoGanador;
    Map<String,Campus> listaCampus=new HashMap<>();
    Stack<Campus> pilaCampus = new Stack<>();


    /**
     * 
     */
    public void imprimirListarDepartamentos(){
        System.out.println("Listando la información de los departamentos....");
        System.out.println("==================================================");
        Collection<Departamentos> values = listaDepartamentos.values();
        for(Departamentos d : values){
            System.out.println(String.format("Departamento: %s, Cantidad: %d, Ganadores: %d, NoPresentes: %d", d.nombre, d.cantidad, d.getGanadores(), d.getNoPresente()));
        }
        System.out.println("==================================================");
    }
    
    public Map<String,Departamentos> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(Map<String,Departamentos> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Ganadores getUltimoGanador() {
        return ultimoGanador;
    }

    public void setUltimoGanador(Ganadores ultimoGanador) {
        this.ultimoGanador = ultimoGanador;
    }

    public void imprimirListaCampus(){
        System.out.println("Listando la información de los Campus");
        System.out.println("==================================================");
        Collection<Campus> values = listaCampus.values();
        for(Campus d : values){
            System.out.println(d.toString());
        }
        System.out.println("==================================================");
    }

    public void setListaCampus(Map<String, Campus> listaCampus) {
        this.listaCampus = listaCampus;
    }

    public Map<String, Campus> getListaCampus() {
        return listaCampus;
    }

    public String getProximoCampus(){
        String campus = "";
        if(pilaCampus.isEmpty()){
            System.out.println("Llenando la lista de campus");
            Collection<Campus> values = listaCampus.values();
            for(Campus c :  values){
                pilaCampus.push(c);
            }
        }
        campus = pilaCampus.pop().getNombre();
        System.out.println("Retornando el proximo campus: "+campus);
        return campus;
    }
}


