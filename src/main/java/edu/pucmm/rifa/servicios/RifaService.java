package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.main.Main;

import java.util.Date;
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

    public Ganadores getGanadoresRamdon(){
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
                ganador.setPremio("Valiando el premio....");
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
