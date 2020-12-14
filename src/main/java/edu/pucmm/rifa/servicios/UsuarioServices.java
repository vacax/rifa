package edu.pucmm.rifa.servicios;

import edu.pucmm.rifa.dominios.Parametro;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.dominios.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vacax on 03/07/16.
 */
public class UsuarioServices extends GestionDb<Usuario> {

    private static UsuarioServices instance;
    //private String URL = ConfiguracionController.getUrlKepler()+"/rest/keplermovil";

    private UsuarioServices(){
        super(Usuario.class);
    }

    /**
     *
     * @return
     */
    public static UsuarioServices getInstance(){
        if(instance== null){
            instance = new UsuarioServices();
            instance.bootStrap();
        }
        return instance;
    }

    /**
     *
     */
    private void bootStrap(){
        List<Usuario> lista = findAll();
        if(lista.size()==0){
            System.out.println("Ejecutando el BootStrap...");
            //usuario por defecto.
            Usuario admin = new Usuario("admin", "Administrador");
            admin.setEmail("admin@admin.com");
            admin.setPassword("admin");
            crear(admin);

            EntityManager em = getEntityManager();
            //creando la base de datos de prueba.
            for(int i=0;i<0;i++){
                PoblacionRifa p = new PoblacionRifa();
                p.setCedula(""+i);
                p.setNombre("Usuario "+i);
                p.setDepartamento("Departamento #"+i);


                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            }

            Rifa rifa = new Rifa("Premio RD$5,000.00", 5);
            em.getTransaction().begin();
            em.persist(rifa);
            em.getTransaction().commit();

            Parametro parametro = em.find(Parametro.class, Parametro.TipoParametro.ULTIMO_CAMPUS.name());
            if(parametro==null){
                em.getTransaction().begin();
                em.persist(new Parametro(Parametro.TipoParametro.ULTIMO_CAMPUS.name(), Parametro.TipoParametro.ULTIMO_CAMPUS.name(), ""));
                em.getTransaction().commit();
            }

        }


    }

    /**
     *
     * @param usuario
     * @param password
     * @return
     */
    public Usuario autenticarUsuario(String usuario, String password){
        Usuario usuarioMovil = null;

        //
        Query query = getEntityManager().createQuery("select u from Usuario u where u.usuario = :usuario and u.password = :password");
        query.setParameter("usuario", usuario);
        query.setParameter("password", password);
        try {
            usuarioMovil = (Usuario) query.getSingleResult();
        }catch (Exception ex){
        }

        return usuarioMovil;
    }

}
