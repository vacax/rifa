package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.PoblacionRifa;
import edu.pucmm.rifa.encapsulaciones.PoblacionRifaData;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.PoblacionRifaService;
import edu.pucmm.rifa.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * Created by vacax on 06/11/16.
 */
public class PoblacionRifaController {

    @FXML
    private TableView<PoblacionRifaData> tabla;

    @FXML
    private TableColumn<PoblacionRifaData, String> cedulaCol, nombreCol,departamentoCol;
    @FXML
    private TableColumn<PoblacionRifaData, Long> idCol;

    private ObservableList<PoblacionRifaData> data = FXCollections.observableArrayList();
    private PoblacionRifaService poblacionRifaService = PoblacionRifaService.getInstance();
    private Main mainApp;


    @FXML
    public void initialize() {
        System.out.println("Incializando la población de rifa");
        //
        idCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        cedulaCol.setCellValueFactory(cellData -> cellData.getValue().getCedula());
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        departamentoCol.setCellValueFactory(cellData -> cellData.getValue().getDepartamento());
        cargarPoblacionRifa();


    }

    private void cargarPoblacionRifa() {
        //limpiar
        data.clear();
        //seteando la información
        List<PoblacionRifa> lista = poblacionRifaService.getListaPoblacionHabilitado();
        for(PoblacionRifa p : lista){
           data.add(new PoblacionRifaData(p));
        }
        tabla.setItems(data);
    }

    @FXML
    private void buscar(){
        System.out.println("Filtrando...");
    }

    @FXML
    private void cargar(){
        System.out.println("Cargando la información");
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("xlsx", "*.xlsx")
            );
            fileChooser.setTitle("Open Resource File");
            File archivo = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

            if(archivo!=null) {
                System.out.println("La ruta: "+archivo.getPath());
                List<PoblacionRifa> listaDesdeArchivoExcel = Utilidades.getListaDesdeArchivoExcel(archivo.getPath());
                poblacionRifaService.cargarListaPoblacionRifa(listaDesdeArchivoExcel);
                //
                cargarPoblacionRifa();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar(){
        System.out.println("Limpiando...");
        poblacionRifaService.limpiarPoblacionRifa();
        cargarPoblacionRifa();
    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }

}
