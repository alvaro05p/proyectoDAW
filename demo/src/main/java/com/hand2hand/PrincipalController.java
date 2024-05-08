package com.hand2hand;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class PrincipalController {

    @FXML
    private Button botonSalir;

    @FXML
    private Button botonirSubir;

    @FXML
    private ImageView hueco1; // Debes tener un ImageView en tu FXML para mostrar la imagen
    
    @FXML
    private ImageView hueco2;
    @FXML
    private ImageView hueco3;
    @FXML
    private ImageView hueco4;
    @FXML
    private ImageView hueco5;
    @FXML
    private ImageView hueco6;

    

    @FXML
    private void initialize() {
        // Mostrar los productos al inicializar el controlador
        mostrarProducto(1,hueco1);
        mostrarProducto(2,hueco2);
        mostrarProducto(3,hueco3);
        mostrarProducto(4,hueco4);
        mostrarProducto(5,hueco5);
        mostrarProducto(6,hueco6);

        
    }

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

    @FXML
    private void irSubir() {
        // Cambiar a la página principal
        // Aquí abrimos la página Subir.fxml
        try {
            Stage stage = (Stage) botonirSubir.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Subir.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        static final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
        static final String USER = "root"; // Cambia por tu nombre de usuario
        static final String PASSWORD = "root"; // Cambia por tu contraseña
    
    
        private void mostrarProducto(int idProducto, ImageView hueco) {

        // Conectar a la base de datos y recuperar la imagen
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT imagen FROM productos WHERE idProductos = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idProducto);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Blob imagenBlob = resultSet.getBlob("imagen");
                InputStream inputStream = imagenBlob.getBinaryStream();
                Image imagen = new Image(inputStream);
                hueco.setImage(imagen);
            } else {
                System.out.println("No se encontró la imagen en la base de datos para el producto con ID: " + idProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
