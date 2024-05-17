package com.hand2hand;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

    @FXML
    private TextField correoField;

    @FXML
    private TextField contraField;

    @FXML
    private TextField nUsuarioField;

    @FXML
    private Button botonRegistro;

    @FXML
    private void registrar() {

        final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
        final String USER = "root"; // Cambia por tu nombre de usuario
        final String PASSWORD = "root"; // Cambia por tu contraseña
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        String correo = correoField.getText();
        String nUsuario = nUsuarioField.getText();
        String contra = contraField.getText();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión exitosa a la base de datos");

            // Query para insertar datos sin incluir la columna idUsuario
            String sql = "INSERT INTO usuarios (correo, nombre, contra) VALUES (?,?,?)";

            // Crear una declaración preparada
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Asignar valores a los parámetros de la declaración preparada
            statement.setString(1, correo); // Asigna una cadena para correo
            statement.setString(2, nUsuario); // Asigna una cadena para contra
            statement.setString(3, contra); // Asigna una cadena para contra

            // Ejecutar la consulta de inserción
            int filasInsertadas = statement.executeUpdate();
            System.out.println("Se insertaron " + filasInsertadas + " filas");

            Stage stage = (Stage) botonRegistro.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Login.fxml"))));

        } catch (SQLException | IOException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        
    }

    @FXML
    private Button botonVolver;

    @FXML
    private void volver(){
        try {
            Stage stage = (Stage) botonVolver.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Login.fxml"))));
        } catch (IOException e) {
            
        }
        
    }

}
