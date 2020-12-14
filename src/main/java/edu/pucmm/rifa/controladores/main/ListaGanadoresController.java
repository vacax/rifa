package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.Ganadores;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.encapsulaciones.GanadoresData;
import edu.pucmm.rifa.encapsulaciones.PoblacionRifaData;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.GanadoresService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by vacax on 18/11/16.
 */
public class ListaGanadoresController {

    @FXML
    private TableView<GanadoresData> tabla;

    @FXML
    private TableColumn<GanadoresData, String> cedulaCol, nombreCol,departamentoCol, posicionCol, campusCol;
    @FXML
    private TableColumn<GanadoresData, Long> idCol;

    private ObservableList<GanadoresData> data = FXCollections.observableArrayList();
    private GanadoresService ganadoresService = GanadoresService.getInstance();
    private Main mainApp;

    @FXML
    public void initialize() {
        System.out.println("Incializando la población de rifa");
        //
        idCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        cedulaCol.setCellValueFactory(cellData -> cellData.getValue().getCedula());
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        departamentoCol.setCellValueFactory(cellData -> cellData.getValue().getDepartamento());
        posicionCol.setCellValueFactory(cellData -> cellData.getValue().getPosicion());
        campusCol.setCellValueFactory(cellData -> cellData.getValue().getCampus());
        cargarGanadores();
    }

    private void cargarGanadores() {
        //limpiar
        data.clear();
        //seteando la información
        List<Ganadores> lista = ganadoresService.getListaGanadores();
        for(Ganadores p : lista){
            data.add(new GanadoresData(p));
        }
        tabla.setItems(data);
    }

    @FXML
    private void buscar(){
        System.out.println("Filtrando...");
    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }

}
