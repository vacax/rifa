package edu.pucmm.rifa.main;

import edu.pucmm.rifa.controladores.main.GeneradorSorteoController;
import edu.pucmm.rifa.controladores.main.LoginController;
import edu.pucmm.rifa.controladores.main.MainController;
import edu.pucmm.rifa.dominios.Usuario;
import edu.pucmm.rifa.servicios.UsuarioServices;
import edu.pucmm.rifa.utilidades.Utilidades;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
            rootLayout = (BorderPane) loader.load();

            MainController mainController = loader.getController();
            mainController.setMainApp(this);

            //Creando las imagenes.
            double tamano = 250;
            double tamanoAlto = 700;
            ImageView imageViewDerecha = new ImageView("/imagenes/barcamp_rotado.png");
            imageViewDerecha.setFitWidth(tamano);
            imageViewDerecha.setFitHeight(tamanoAlto);

            ImageView imageViewIzquieda = new ImageView("/imagenes/barcamp_rotado.png");
            imageViewIzquieda.setFitWidth(tamano);
            imageViewIzquieda.setFitHeight(tamanoAlto);

            ImageView imageViewArriba = new ImageView("/imagenes/Patrocinadores_rifa.png");
            imageViewArriba.setFitHeight(100);

            BorderPane paneAbajo = new BorderPane();
            paneAbajo.setPrefSize(200, 100);
            paneAbajo.setCenter(imageViewArriba);


            rootLayout.setRight(imageViewDerecha);
            rootLayout.setLeft(imageViewIzquieda);
            rootLayout.setBottom(paneAbajo);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);


            //Incluyendo el punto de venta.
            addGeneradorSorteo();

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

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            GeneradorSorteoController controller = loader.getController();
            controller.inicializarApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
