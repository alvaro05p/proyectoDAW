package com.hand2hand;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button iniciarSesion;

    @FXML
    private void enviarApagina() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) iniciarSesion.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button entrarRegistro;   

    @FXML
    private void enviaraRegistro() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) entrarRegistro.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Registro.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
