package com.hand2hand;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SaldoController {

    @FXML
    private void initialize() {
        
        consultarSaldo();

    }
    
    @FXML
    private Button botonVolver;

    @FXML
    private void volver() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) botonVolver.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField cuantoAgregar;

   @FXML
    private Label saldoActual;

    String registradoAhora = Main.registradoAhora;

    @FXML 
    private Button botonAgregarSaldo;

    @FXML
    private void consultarSaldo() {

        final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
        final String USER = "root"; // Cambia por tu nombre de usuario
        final String PASSWORD = "root"; // Cambia por tu contraseña
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Conexión exitosa a la base de datos");

            // Query para obtener el saldo
            String query = "SELECT saldo FROM usuarios WHERE nombre LIKE ?";

            // Crear una declaración preparada
            PreparedStatement statement = connection.prepareStatement(query);

            // Asignar valores a los parámetros de la declaración preparada
            statement.setString(1, registradoAhora);
            
            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Procesar el resultado
            if (resultSet.next()) {
                int saldo = resultSet.getInt("saldo");
                saldoActual.setText(String.valueOf(saldo)+"€");
                System.out.println("Saldo consultado: " + saldo);
            } else {
                System.out.println("No se encontró el usuario con el nombre: " + registradoAhora);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void agregarSaldo(){

        final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
        final String USER = "root"; // Cambia por tu nombre de usuario
        final String PASSWORD = "root"; // Cambia por tu contraseña
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Conexión exitosa a la base de datos");

            // Primero, obtener el saldo actual
            String selectQuery = "SELECT saldo FROM usuarios WHERE nombre = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, registradoAhora);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    int saldoActual = resultSet.getInt("saldo");

                    int cuanto = Integer.parseInt(cuantoAgregar.getText());

                    // Sumar el monto al saldo actual
                    int nuevoSaldo = saldoActual + cuanto;

                    // Actualizar el saldo en la base de datos
                    String updateQuery = "UPDATE usuarios SET saldo = ? WHERE nombre = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, nuevoSaldo);
                        updateStatement.setString(2, registradoAhora);
                        updateStatement.executeUpdate();
                        initialize();
                    }
                } else {
                    System.out.println("No se encontró el usuario con idUsuario: " + selectQuery);
                }
            }
        
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

    }
    

}
