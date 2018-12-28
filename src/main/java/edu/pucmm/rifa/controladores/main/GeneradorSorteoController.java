package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.jms.Productor;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.GanadoresService;
import edu.pucmm.rifa.servicios.PoblacionRifaService;
import edu.pucmm.rifa.servicios.RifaService;
import edu.pucmm.rifa.utilidades.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.CommandLinksDialog;

import javax.jms.JMSException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vacax on 23/10/16.
 */
public class GeneradorSorteoController {

    @FXML
    private Button botonGeneracion;
    @FXML
    private StackPane panelImagen;
    @FXML
    private Label lbPremio;

    private Main main;
    private RifaService rifaService = RifaService.getInstance();
    private GanadoresService ganadoresService = GanadoresService.getInstance();
    private PoblacionRifaService poblacionRifaService = PoblacionRifaService.getInstance();
    private Rifa rifa;
    private Ganadores ganadores;
    private Stage ventanaGanadores;
    private IObserver iObserver;



    @FXML
    public void initialize() {
        //
        rifa = rifaService.getRifaDisponible();

        //
        //lbPremio.setText(rifa.getPremio());
        lbPremio.setText("");
        //
        System.out.println("Agregando listener JMS boton inicio en: "+this.getClass().getName());
        Main.consumidor.addMensajeListener(new IObserver() {
            @Override
            public void update(Class clase, Object argumento, Enum anEnum) {
                String mensaje = (String) argumento;
                System.out.println("El mensaje: "+mensaje);
                if(mensaje.equalsIgnoreCase("inicio")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            botonGeneracion.fire();
                        }
                    });
                }
            }
        });


    }

    public void generarRifa(){
        System.out.println("Generar la rifa....");
        if(poblacionRifaService.getCantidadPoblacionTotal() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sin Lista de Participantes");
            alert.setHeaderText(null);
            alert.setContentText("No existen concursantes, debe crear uno");
            alert.showAndWait();
            return;
        }else if(poblacionRifaService.getCantidadPoblacionTotalPresentesNoGanadores() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sin Participantes Disponibles");
            alert.setHeaderText(null);
            alert.setContentText("Todos los Participantes tienen premios");
            alert.showAndWait();
            return;
        }
        panelImagen.getChildren().removeAll();

        String archivo = GeneradorSorteoController.class.getResource("/imagenes/tombola4.gif").toExternalForm();
        System.out.println("El archivo: "+archivo);
        Animation ani = new AnimatedGif(archivo, 500);
        ani.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(ganadores==null){
                    System.out.println("Animación terminada sin ganador");
                    ani.play();
                }else{
                    System.out.printf("Tenemos Ganador...");
                    panelImagen.getChildren().removeAll(ani.getView());
                    //
                    ani.stop();
                    //
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mostrarGanadores();
                        }
                    });
                }
            }
        });

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Recuperando el valor...");
                ganadores = rifaService.getGanadoresRamdon();
                System.out.println("El ganador es: "+ganadores.getPoblacionRifa().getCedula()+" - "+ganadores.getPoblacionRifa().getNombre());
                return null;
            }
        };


        Thread hilo = new Thread(task);

        panelImagen.getChildren().add(ani.getView());
        //
        botonGeneracion.setDisable(true);
        //
        hilo.start();
        //
        ani.setCycleCount(5);
        ani.play();
    }

    /**
     *
     */
    public void aprobado(){
        System.out.println("Aprobando el premio.");
        ganadoresService.procesarGanador(rifa, ganadores);

        //Limpiando
        ganadores = null;
        rifa = rifaService.getRifaDisponible();

        //
        botonGeneracion.setDisable(false);
        ventanaGanadores.close();
        //
        Main.consumidor.removeMensajeListener(iObserver);
    }

    /**
     *
     */
    public void mostrarGanadores(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/PantallaGanador.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            PantallaGanadorController pantallaGanadorController = loader.getController();
            pantallaGanadorController.setMainApp(main);
            pantallaGanadorController.setGanadores(ganadores);
            pantallaGanadorController.addAprobadoGanadoresListener(new IObserver() {
                @Override
                public void update(Class clase, Object argumento, Enum anEnum) {
                    if(ganadores!=null) {
                        aprobado();
                    }
                }
            });
            pantallaGanadorController.addCanceladoGanadoreLista(new IObserver() {
                @Override
                public void update(Class clase, Object argumento, Enum anEnum) {
                    buscarOtro((Ganadores) argumento);
                }
            });
            //
            new Productor().enviarMensaje("pantalla_ganadores");

            //
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ganador de la Rifa");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setAlwaysOnTop(true);
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    buscarOtro(null);
                }
            });
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //evento para cerrar la ventana.
            System.out.println("Agregando listener JMS para cierre en: "+this.getClass().getName());
            iObserver=new IObserver() {
                @Override
                public void update(Class clase, Object argumento, Enum anEnum) {
                    //
                    List<String> lista = Arrays.asList("cancelar");
                    String mensaje = (String)argumento;
                    if(lista.contains(mensaje)) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //javaFX operations should go here
                                if(mensaje.equalsIgnoreCase("cancelar")){
                                    dialogStage.close();
                                    buscarOtro(null);

                                    //Notificando al cliente que seleccionó la información.
                                    try {
                                        new Productor().enviarMensaje("completado");
                                    } catch (JMSException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
            };
            Main.consumidor.addMensajeListener(iObserver);


            //
            ventanaGanadores = dialogStage;

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Prepara el software para marcar al participante como no asistio.
     * @param ganadorNoAsistio
     */
    public void buscarOtro(Ganadores ganadorNoAsistio){
        if(ganadorNoAsistio!=null && ganadorNoAsistio.getPoblacionRifa()!=null){
            poblacionRifaService.marcarParticipanteNoAsistio(ganadorNoAsistio.getPoblacionRifa());
        }
        ganadores = null;
        botonGeneracion.setDisable(false);
        ventanaGanadores.close();
        Main.consumidor.removeMensajeListener(iObserver);
    }

    public void inicializarApp(Main main) {
        this.main = main;
    }
}
