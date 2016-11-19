package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.Rifa;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.GanadoresService;
import edu.pucmm.rifa.servicios.RifaService;
import edu.pucmm.rifa.utilidades.IObserver;
import edu.pucmm.rifa.utilidades.SubjectHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by vacax on 18/11/16.
 */
public class PantallaGanadorController {

    @FXML
    private Label lbGanador;

    @FXML
    private Button btnAprobado, btnCancelado;

    private Main mainApp;
    private GanadoresService ganadoresService = GanadoresService.getInstance();
    private Ganadores ganadores;
    private SubjectHelper aprobadoGanadoresListener, canceladoGanadoresListener;



    @FXML
    public void initialize() {
        aprobadoGanadoresListener=new SubjectHelper();
        canceladoGanadoresListener=new SubjectHelper();
    }

    public void addAprobadoGanadoresListener(IObserver observer){
        aprobadoGanadoresListener.addObserver(observer);
    }

    public void addCanceladoGanadoreLista(IObserver observer){
        canceladoGanadoresListener.addObserver(observer);
    }

    public void aprobar(){
        aprobadoGanadoresListener.notify(null, "", null);
    }

    public void cancelar(){
        canceladoGanadoresListener.notify(null, "", null);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setGanadores(Ganadores ganadores) {
        this.ganadores = ganadores;
        lbGanador.setText(""+ganadores.getPoblacionRifa().getNombre());
    }

}
