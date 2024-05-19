package com.hand2hand;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    
    
    @FXML
    private TextField contraField;

    @FXML
    private TextField usuarioField;

    @FXML
    private Button iniciarSesion;

    @FXML
    private void enviarApagina() {
        String nombre = usuarioField.getText();
        String contra = contraField.getText();

        Main.registradoAhora=nombre;
    
        // Verificar las credenciales del usuario
        if (verificarCredenciales(nombre, contra)) {
            // Credenciales válidas, cambiar a la página principal
            try {
                Stage stage = (Stage) iniciarSesion.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Credenciales inválidas, mostrar un mensaje de error
            System.out.println("Credenciales incorrectas. Por favor, inténtelo de nuevo.");
            mostrarAlertError(null);

            
        }
    }

    @FXML
    private void mostrarAlertError(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Vaya!");
        alert.setContentText("Usuario o contraseña incorrectos");
        alert.showAndWait();
    }        

    private boolean verificarCredenciales(String nombre, String contra) {
    // Consultar la base de datos para verificar las credenciales del usuario
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hand2hand", "root", "root")) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contra = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, contra);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // Devuelve true si se encontró una coincidencia
    } catch (SQLException e) {
        System.out.println("Error al verificar credenciales: " + e.getMessage());
        return false;
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
