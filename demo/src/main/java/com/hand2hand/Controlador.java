package com.hand2hand;



import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controlador {

    @FXML
    private Button boton;

    @FXML
    public void initialize() {
        boton.setOnAction(event -> {
            System.out.println("¡El botón fue clickeado!");
        });
    }
}
