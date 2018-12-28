package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.jms.Productor;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.GanadoresService;
import edu.pucmm.rifa.servicios.RifaService;
import edu.pucmm.rifa.utilidades.IObserver;
import edu.pucmm.rifa.utilidades.SubjectHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.jms.JMSException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vacax on 18/11/16.
 */
public class PantallaGanadorController {

    @FXML
    private Label lbGanador;

    @FXML
    private Button btnAprobado;
    @FXML
    private Button btnCancelado;

    private Main mainApp;
    private GanadoresService ganadoresService = GanadoresService.getInstance();
    private Ganadores ganadores;
    private SubjectHelper aprobadoGanadoresListener, canceladoGanadoresListener;
    private IObserver iObserver;



    @FXML
    public void initialize() {
        aprobadoGanadoresListener=new SubjectHelper();
        canceladoGanadoresListener=new SubjectHelper();

        //
        System.out.println("Agregando listener JMS en: "+this.getClass().getName());
        iObserver = new IObserver() {
            @Override
            public void update(Class clase, Object argumento, Enum anEnum) {
                //
                List<String> lista = Arrays.asList("aprobado", "noPresente");
                String mensaje = (String)argumento;
                if(lista.contains(mensaje)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            if(mensaje.equalsIgnoreCase("aprobado")){
                                btnAprobado.fire();
                            } else if(mensaje.equalsIgnoreCase("noPresente")){
                                System.out.println("No presente recibido JMS");
                                btnCancelado.fire();
                            }

                            //Notificando al cliente que seleccionó la información.
                            try {
                                new Productor().enviarMensaje("completado");
                            } catch (JMSException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        Main.consumidor.addMensajeListener(iObserver);
    }

    public void addAprobadoGanadoresListener(IObserver observer){
        aprobadoGanadoresListener.addObserver(observer);
    }

    public void addCanceladoGanadoreLista(IObserver observer){
        canceladoGanadoresListener.addObserver(observer);
    }

    public void aprobar(){
        aprobadoGanadoresListener.notify(null, "", null);
        removerListener();
    }

    public void cancelar(){
        canceladoGanadoresListener.notify(null, ganadores, null);
        removerListener();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setGanadores(Ganadores ganadores) {
        this.ganadores = ganadores;
        lbGanador.setText(""+ganadores.getPoblacionRifa().getNombre());
    }

    private void removerListener(){
        Main.consumidor.removeMensajeListener(iObserver);
    }

}
