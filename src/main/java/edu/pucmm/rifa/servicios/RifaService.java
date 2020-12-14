package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.Parametro;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.encapsulaciones.Campus;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.utilidades.SinParticipantesException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vacax on 23/10/16.
 */
public class RifaService extends GestionDb<Rifa> {

    private static RifaService instance;

    private PoblacionRifaService poblacionRifaService = PoblacionRifaService.getInstance();
    private GanadoresService ganadoresService = GanadoresService.getInstance();

    private RifaService(){
        super(Rifa.class);
    }

    /**
     *
     * @return
     */
    public static RifaService getInstance(){
        if(instance== null){
            instance = new RifaService();
        }
        return instance;
    }

    public Ganadores getGanadoresRamdon() throws SinParticipantesException {
        Ganadores ganador = null;
        //recuperando la cantidad de elementos:
        List<PoblacionRifa> listaParticipantes = poblacionRifaService.getListaPoblacionHabilitadoYPresenteNoGanador(Main.controlDepartamentos.getProximoCampus());
        System.out.println("La cantidad de participantes habiles: "+listaParticipantes.size());
        if(listaParticipantes.isEmpty()){
            throw new SinParticipantesException("No existen participantes disponibles");
        }
        System.out.println("Antes de ordenar: "+listaParticipantes.get(0).getNombre());
        Collections.shuffle(listaParticipantes);
        System.out.println("Despues de ordenar: "+listaParticipantes.get(0).getNombre());
        //
        Random rand = new Random();
        PoblacionRifa seleccionado = listaParticipantes.get(rand.nextInt(listaParticipantes.size()));
        //
        ganador = new Ganadores();
        ganador.setFechaGeneracion(new Date());
        ganador.setGeneradoPor(Main.usuarioMovil);
        ganador.setPoblacionRifa(seleccionado);
        ganador.setPremio("Validando el premio....");
        //
        return ganador;
    }

    /**
     * Metodo para realizar la consulta de los ganadores.
     * @return
     */
    public Ganadores getGanadoresRamdon2(){
        Ganadores ganador = null;
        long cantidad = poblacionRifaService.getCantidadPoblacionTotal();
        System.out.println("El total: "+cantidad);
        long minimo = poblacionRifaService.getIdPoblacionMinima();
        long maxima = poblacionRifaService.getIdPoblacionMaximo();

        int contador = 1;
        while(ganador == null) {
            long id = ThreadLocalRandom.current().nextLong(minimo, maxima + 1);
            System.out.printf("La iteración #%d -- Valor recuperado: %d", contador++, id);
            PoblacionRifa poblacionRifa = poblacionRifaService.find(id);
            Ganadores ganadorConPremio = ganadoresService.getPoblacionConPremio(poblacionRifa);
            if(ganadorConPremio==null){
                ganador = new Ganadores();
                ganador.setFechaGeneracion(new Date());
                ganador.setGeneradoPor(Main.usuarioMovil);
                ganador.setPoblacionRifa(poblacionRifa);
                ganador.setPremio("Validando el premio....");
            }
        }

        return ganador;
    }

    /**
     * TODO: Resolver el caso..
     * @return
     */
    public Rifa getRifaDisponible(){
        return findAll().get(0);
    }


}
