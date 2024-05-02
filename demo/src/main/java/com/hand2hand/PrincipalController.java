package com.hand2hand;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrincipalController {
    @FXML
    private Button botonSalir;

    @FXML
    private void salir() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) botonSalir.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
