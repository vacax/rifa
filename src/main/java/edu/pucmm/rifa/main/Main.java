package edu.pucmm.rifa.main;

import edu.pucmm.rifa.controladores.main.GeneradorSorteoController;
import edu.pucmm.rifa.controladores.main.LoginController;
import edu.pucmm.rifa.controladores.main.MainController;
import edu.pucmm.rifa.dominios.Usuario;
import edu.pucmm.rifa.encapsulaciones.ControlDepartamentos;
import edu.pucmm.rifa.servicios.UsuarioServices;
import edu.pucmm.rifa.utilidades.Utilidades;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.h2.tools.Server;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by vacax on 22/10/16.
 */
public class Main extends Application {

    private Stage primaryStage;
    public static Usuario usuarioMovil;
    private BorderPane rootLayout;
    private AnchorPane caja;
    public static ControlDepartamentos controlDepartamentos;
    

    public static void main(String[] args) throws SQLException {
        //
        startDb();
        UsuarioServices.getInstance();
        //
        Utilidades.agregarSerializacionUniRest();
        //
        launch(args);
    }

    @Override
    protected void finalize() throws Throwable {
        stopDb();
        super.finalize();
    }

    /**
     *
     * @throws SQLException
     */
    public static void startDb() throws SQLException {
        System.out.println("Inicializando la base de datos");
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        System.out.println("bajando la  base de datos.");
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sistema de Rifa de PUCMM");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        //entrada del sistema
        loginLayout();

    }

    public void loginLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL url = Main.class.getResource("/fxml/pantallas/Login.fxml");
            System.out.println("La URL: "+url.toString());
            loader.setLocation(url);
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);

            //recuperando el controlador.
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);

            //
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL url = Main.class.getResource("/fxml/main/Main.fxml");
            System.out.println("La URL: "+url.toString());
            loader.setLocation(url);
            caja =  loader.load();
            rootLayout = (BorderPane) caja.getChildren().get(1);
            rootLayout.setPadding(new Insets(0,0,0,0));

            MainController mainController = loader.getController();
            mainController.setMainApp(this);

            //Creando las imagenes.
            double tamano = 250;
            double tamanoAltoLaterales = 300;
            ImageView imageViewDerecha = new ImageView("/imagenes/navidad/Marco-01.png");
            imageViewDerecha.setFitWidth(tamano);
            imageViewDerecha.setFitHeight(tamanoAltoLaterales);

            ImageView imageViewIzquieda = new ImageView("/imagenes/navidad/Marco-03.png");
            imageViewIzquieda.setFitWidth(tamano);
            imageViewIzquieda.setFitHeight(tamanoAltoLaterales);

            ImageView imageViewArriba = new ImageView("/imagenes/navidad/Marco-02.png");
            imageViewArriba.setFitHeight(200);
            imageViewArriba.setFitWidth(600);

            ImageView imageViewAbajo = new ImageView("/imagenes/navidad/Marco-04.png");
            imageViewAbajo.setFitHeight(200);
            imageViewAbajo.setFitWidth(300);

            BorderPane paneArriba = new BorderPane();
            //paneArriba.setStyle("-fx-border-color: black");
            paneArriba.setPadding(new Insets(0,0,0,0));
            paneArriba.setPrefSize(200, 100);
            paneArriba.setCenter(imageViewArriba);
            paneArriba.setLeft(null);
            paneArriba.setRight(null);
            paneArriba.setBottom(null);
            paneArriba.setTop(null);
            paneArriba.setMargin(imageViewIzquieda, new Insets(0,0,0,0));

            BorderPane paneAbajo = new BorderPane();
            //paneAbajo.setStyle("-fx-border-color: black");
            paneAbajo.setPadding(new Insets(0,0,0,0));
            paneAbajo.setPrefSize(1000, 200);
            paneAbajo.setCenter(imageViewAbajo);
            paneAbajo.setLeft(null);
            paneAbajo.setRight(null);
            paneAbajo.setBottom(null);
            paneAbajo.setTop(null);
            paneAbajo.setMargin(imageViewIzquieda, new Insets(0,0,0,0));

            BorderPane paneDerecha = new BorderPane();
            //paneDerecha.setStyle("-fx-border-color: black");
            paneDerecha.setPadding(new Insets(0,0,0,0));
            paneDerecha.setPrefSize(200, tamanoAltoLaterales);
            paneDerecha.setCenter(imageViewDerecha);
            paneDerecha.setLeft(null);
            paneDerecha.setRight(null);
            paneDerecha.setBottom(null);
            paneDerecha.setTop(null);
            paneDerecha.setMargin(imageViewIzquieda, new Insets(0,0,0,0));

            BorderPane paneIzquierda = new BorderPane();
            //paneIzquierda.setStyle("-fx-border-color: black");
            paneIzquierda.setPadding(new Insets(0,0,0,0));
            paneIzquierda.setPrefSize(200, tamanoAltoLaterales);
            paneIzquierda.setCenter(imageViewIzquieda);
            paneIzquierda.setLeft(null);
            paneIzquierda.setRight(null);
            paneIzquierda.setBottom(null);
            paneIzquierda.setTop(null);
            paneIzquierda.setMargin(imageViewIzquieda, new Insets(0,0,0,0));

            //rootLayout.setTop(paneArriba);
            rootLayout.setRight(paneDerecha);
            rootLayout.setLeft(paneIzquierda);
            rootLayout.setTop(null);
            //rootLayout.setRight(null);
            //rootLayout.setLeft(null);
            rootLayout.setBottom(paneAbajo);
            
            rootLayout.setMargin(paneAbajo, new Insets(0,0,0,0));
            rootLayout.setMargin(paneDerecha, new Insets(0,0,0,0));
            rootLayout.setMargin(paneIzquierda, new Insets(0,0,0,0));

            //Incluyendo.
            addGeneradorSorteo();

            // Show the scene containing the root layout.
            Scene scene = new Scene(caja);
            primaryStage.setScene(scene);

            //
            primaryStage.setResizable(true);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRifaNavidena() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL url = Main.class.getResource("/fxml/main/MainBarcamp.fxml");
            System.out.println("La URL: "+url.toString());
            loader.setLocation(url);
            caja =  loader.load();

            MainController mainController = loader.getController();
            mainController.setMainApp(this);

            //Incluyendo el punto del sorteo.
            FXMLLoader loaderTemp = new FXMLLoader();
            loaderTemp.setLocation(Main.class.getResource("/fxml/pantallas/GeneradorSorteo.fxml"));
            AnchorPane loteriaPane = (AnchorPane) loaderTemp.load();
            mainController.getPanelRifa().getChildren().add(loteriaPane);

            //
            GeneradorSorteoController controllerLoteria = loaderTemp.getController();
            controllerLoteria.inicializarApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(caja);
            primaryStage.setScene(scene);

            //
            primaryStage.setResizable(true);
            primaryStage.setMaximized(true);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addGeneradorSorteo(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/GeneradorSorteo.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            //personOverview.setPrefSize(200,200);

            // Set person overview into the center of root layout.
            rootLayout = (BorderPane) caja.getChildren().get(1);
            rootLayout.setCenter(personOverview);


            rootLayout.setMargin(personOverview, new Insets(0,0,0,0));

            //
            GeneradorSorteoController controller = loader.getController();
            controller.inicializarApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
