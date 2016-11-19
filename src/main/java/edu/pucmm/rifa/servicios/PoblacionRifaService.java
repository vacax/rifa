package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Usuario;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vacax on 22/10/16.
 */
public class PoblacionRifaService extends GestionDb<PoblacionRifa> {

    private static PoblacionRifaService instance;

    private PoblacionRifaService(){
        super(PoblacionRifa.class);
    }

    /**
     *
     * @return
     */
    public static PoblacionRifaService getInstance(){
        if(instance== null){
            instance = new PoblacionRifaService();
        }
        return instance;
    }


    public long getCantidadPoblacionTotal(){
        long cantidad = 0;
        EntityManager em = getEntityManager();
        cantidad = (Long) em.createQuery("select count(*) from PoblacionRifa  where habilitado=true").getSingleResult();
        System.out.println("La cantidad recuperada es: "+cantidad);
        return cantidad;
    }

    public long getIdPoblacionMinima(){
        long minimo = 0;
        EntityManager em = getEntityManager();
        minimo = (Long) em.createQuery("select min(id) from PoblacionRifa  where habilitado=true").getSingleResult();
        System.out.println("La cantidad recuperada es: "+minimo);
        return minimo;
    }

    public long getIdPoblacionMaximo(){
        long maximo = 0;
        EntityManager em = getEntityManager();
        maximo = (Long) em.createQuery("select max(id) from PoblacionRifa  where habilitado=true").getSingleResult();
        System.out.println("La cantidad recuperada es: "+maximo);
        return maximo;
    }

    public List<PoblacionRifa> getListaPoblacionHabilitado(){
        EntityManager em = getEntityManager();
        List<PoblacionRifa> lista = em.createQuery("select  p from PoblacionRifa p where p.habilitado=true").getResultList();
        return lista;
    }

    /**
     *
     * @param cedula
     * @return
     */
    public PoblacionRifa getPoblacionRifaPorCedula(String cedula){
        EntityManager em = getEntityManager();
        PoblacionRifa poblacionRifa =null;
        try {
            poblacionRifa = (PoblacionRifa) em.createQuery("select p from PoblacionRifa p where p.habilitado=true and lower(p.cedula) like :cedula")
                    .setParameter("cedula", "" + cedula + "").getSingleResult();
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return poblacionRifa;
    }

    /**
     *
     * @param lista
     */
    public int cargarListaPoblacionRifa(List<PoblacionRifa> lista){
       int cantidad = 0;

        for(PoblacionRifa p : lista){
           p.setHabilitado(true);
           if(getPoblacionRifaPorCedula(p.getCedula()) == null){
             crear(p);
             cantidad++;
           } else{
               System.out.println("Cedula existe: "+p.getCedula());
           }
        }
        System.out.println("cantidad: "+cantidad);
        return cantidad;
    }

    /**
     *
     */
    public void limpiarPoblacionRifa(){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from Ganadores ").executeUpdate();
        em.createQuery("delete from PoblacionRifa").executeUpdate();
        em.getTransaction().commit();
    }

}
