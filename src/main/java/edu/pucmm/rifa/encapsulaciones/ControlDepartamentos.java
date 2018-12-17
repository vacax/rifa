package edu.pucmm.rifa.encapsulaciones;

import edu.pucmm.rifa.dominios.Ganadores;

import java.util.*;

public class ControlDepartamentos {

    Map<String,Departamentos> listaDepartamentos=new HashMap<>();
    Ganadores ultimoGanador;

    /**
     * 
     */
    public void listarDepartamentos(){
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

}


