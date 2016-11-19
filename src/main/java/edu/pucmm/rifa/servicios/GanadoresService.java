package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.main.Main;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

/**
 * Created by vacax on 23/10/16.
 */
public class GanadoresService extends GestionDb<Ganadores> {

    private static GanadoresService instance;

    private GanadoresService(){
        super(Ganadores.class);
    }

    /**
     *
     * @return
     */
    public static GanadoresService getInstance(){
        if(instance== null){
            instance = new GanadoresService();
        }
        return instance;
    }

    public Ganadores getPoblacionConPremio(PoblacionRifa poblacionRifa){
        Ganadores ganadores = null;
        EntityManager em = getEntityManager();
        try {
            ganadores = (Ganadores) em.createQuery("select g from Ganadores g where g.poblacionRifa = :poblacionRifa and g.aprobado=true")
                    .setParameter("poblacionRifa", poblacionRifa)
                    .getSingleResult();
        }catch (NoResultException ex){
            System.out.println("no existen ganadores...");
        }
        return ganadores;
    }

    /**
     * Procesando los ganadores para no volver al listado.
     * @param rifa
     * @param ganadores
     * @return
     */
    public Ganadores procesarGanador(Rifa rifa, Ganadores ganadores){

        ganadores.setRifa(rifa);
        ganadores.setGeneradoPor(Main.usuarioMovil);
        ganadores.setAprobado(true);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(ganadores);
        em.getTransaction().commit();

        return ganadores;
    }

    /**
     *
     * @return
     */
    public List<Ganadores> getListaGanadores(){
        EntityManager em = getEntityManager();
        List<Ganadores> lista = em.createQuery("select  p from Ganadores p where p.aprobado=true").getResultList();
        return lista;
    }
}
