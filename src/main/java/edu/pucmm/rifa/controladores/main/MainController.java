package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.PoblacionRifaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by vacax on 03/07/16.
 */
public class MainController {

    private Main mainApp;

    @FXML
    private StackPane panelRifa;

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        //
        PoblacionRifaService.getInstance().cargarControlDepartamento();
    }

    public void poblacionRifa(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/PoblacionRifa.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            PoblacionRifaController poblacionRifaController = loader.getController();
            poblacionRifaController.setMainApp(mainApp);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Población Rifa");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(true);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void listaGanadores(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/ListaGanadores.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            ListaGanadoresController listaGanadoresController = loader.getController();
            listaGanadoresController.setMainApp(mainApp);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Listado de Ganadores");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(true);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void noPresentes(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/PoblacionRifaNoPresente.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            PoblacionRifaNoPresenteController poblacionRifaNoPresenteController = loader.getController();
            poblacionRifaNoPresenteController.setMainApp(mainApp);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Listado de Participantes No Presentes");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(true);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cerrar(){
        System.out.println("Cerrando la aplicación");
        System.exit(0);
    }

    public StackPane getPanelRifa() {
        return panelRifa;
    }

    public void setPanelRifa(StackPane panelRifa) {
        this.panelRifa = panelRifa;
    }
}
