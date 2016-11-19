package edu.pucmm.rifa.controladores.main;

import edu.pucmm.rifa.dominios.Usuario;
import edu.pucmm.rifa.main.Main;
import edu.pucmm.rifa.servicios.UsuarioServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by vacax on 03/07/16.
 */
public class LoginController {

    private Main mainApp;

    @FXML
    Button btnEntrar, btnCancelar, btnConfiguracion;
    @FXML
    TextField txtUsuario;
    @FXML
    PasswordField txtPassword;

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }

    /**
     *
     */
    public void entrar(){
        System.out.println("Entrada al sistema");
        Usuario usuarioMovil = UsuarioServices.getInstance().autenticarUsuario(txtUsuario.getText(), txtPassword.getText());
        if(usuarioMovil!=null){
            Main.usuarioMovil = usuarioMovil;
            System.out.println("Usuario autenticado: "+usuarioMovil.getNombre());
            mainApp.initRootLayout();
        } else{
            System.out.println("Error de autentificacion");
        }
    }

    public void configuracion(){
        System.out.println("Configuración");
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/pantallas/Configuracion.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Configuración Sistema");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Mostrando y esperando el cierre.
            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void cancelar(){
        System.out.println("Salida del sistema");
        System.exit(0);
    }


}
