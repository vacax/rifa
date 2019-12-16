package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Usuario;
import edu.pucmm.rifa.encapsulaciones.ControlDepartamentos;
import edu.pucmm.rifa.encapsulaciones.Departamentos;
import edu.pucmm.rifa.main.Main;

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
        cantidad = (Long) em.createQuery("select count(*) from PoblacionRifa  where habilitado=true and noPresente = false").getSingleResult();
        System.out.println("La cantidad recuperada es: "+cantidad);
        return cantidad;
    }

    public long getCantidadPoblacionTotalPresentesNoGanadores(){
        long cantidad = 0;
        EntityManager em = getEntityManager();
        cantidad = (Long) em.createQuery("select count(*) from PoblacionRifa  where habilitado=true and noPresente = false and ganador=false").getSingleResult();
        System.out.println("La cantidad recuperada es: "+cantidad);
        return cantidad;
    }

    public long getIdPoblacionMinima(){
        long minimo = 0;
        EntityManager em = getEntityManager();
        minimo = (Long) em.createQuery("select min(id) from PoblacionRifa  where habilitado=true and noPresente = false").getSingleResult();
        System.out.println("La cantidad recuperada es: "+minimo);
        return minimo;
    }

    public long getIdPoblacionMaximo(){
        long maximo = 0;
        EntityManager em = getEntityManager();
        maximo = (Long) em.createQuery("select max(id) from PoblacionRifa  where habilitado=true and noPresente = false").getSingleResult();
        System.out.println("La cantidad recuperada es: "+maximo);
        return maximo;
    }

    public List<PoblacionRifa> getListaPoblacionHabilitado(){
        EntityManager em = getEntityManager();
        List<PoblacionRifa> lista = em.createQuery("select  p from PoblacionRifa p where p.habilitado=true").getResultList();
        return lista;
    }

    public List<PoblacionRifa> getListaPoblacionHabilitadoNoPresente(){
        EntityManager em = getEntityManager();
        List<PoblacionRifa> lista = em.createQuery("select  p from PoblacionRifa p where p.habilitado=true and noPresente = true").getResultList();
        return lista;
    }

    public List<PoblacionRifa> getListaPoblacionHabilitadoYPresenteNoGanador(){
        EntityManager em = getEntityManager();
        List<PoblacionRifa> lista = em.createQuery("select  p from PoblacionRifa p where p.habilitado=true and noPresente = false and ganador=false").getResultList();
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
            poblacionRifa = (PoblacionRifa) em.createQuery("select p from PoblacionRifa p where p.habilitado=true and noPresente = false and lower(p.cedula) like :cedula")
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
        //em.createNativeQuery("ALTER TABLE poblacionrifa AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();

    }

    /**
     *
     * @param poblacionRifa
     * @return
     */
    public boolean marcarParticipanteNoAsistio(PoblacionRifa poblacionRifa){
        System.out.println("Marcando Participante no se encuentra: "+poblacionRifa.toString());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        PoblacionRifa temp = em.find(PoblacionRifa.class, poblacionRifa.getId());
        temp.setNoPresente(true);
        em.getTransaction().commit();
        em.close();
        return temp.isNoPresente();
    }

    /**
     *
     * @return
     */
    public void cargarControlDepartamento(){
        ControlDepartamentos cd =  new ControlDepartamentos();
        List<PoblacionRifa> listaPoblacionHabilitado = getListaPoblacionHabilitado();
        for(PoblacionRifa p : listaPoblacionHabilitado){

            //Departamentos d = new Departamentos(p.getDepartamento().trim());
            Departamentos d = cd.getListaDepartamentos().get(p.getDepartamento().trim());
            if(d == null){
                d = new Departamentos(p.getDepartamento().trim());
                cd.getListaDepartamentos().put(d.getNombre(), d);
            }
            //
            d.setCantidad(d.getCantidad()+1);
        }
        //
        Main.controlDepartamentos = cd;
        Main.controlDepartamentos.listarDepartamentos();

    }

}
