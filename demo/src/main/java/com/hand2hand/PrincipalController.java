package com.hand2hand;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private ImageView hueco1;
    @FXML
    private Label nombre1;
    @FXML
    private Label precio1;
    @FXML
    private ImageView hueco2;
    @FXML
    private Label nombre2;
    @FXML
    private Label precio2;
    @FXML
    private ImageView hueco3;
    @FXML
    private Label nombre3;
    @FXML
    private Label precio3;
    @FXML
    private ImageView hueco4;
    @FXML
    private Label nombre4;
    @FXML
    private Label precio4;
    @FXML
    private ImageView hueco5;
    @FXML
    private Label nombre5;
    @FXML
    private Label precio5;
    @FXML
    private ImageView hueco6;
    @FXML
    private Label nombre6;
    @FXML
    private Label precio6;

    

    @FXML
    private void initialize() {
        // Mostrar los productos al inicializar el controlador
        mostrarProducto(1,hueco1);
        mostrarProducto(2,hueco2);
        mostrarProducto(3,hueco3);
        mostrarProducto(4,hueco4);
        mostrarProducto(5,hueco5);
        mostrarProducto(6,hueco6);

        mostrarNombre(1,nombre1);
        mostrarNombre(2,nombre2);
        mostrarNombre(3,nombre3);
        mostrarNombre(4,nombre4);
        mostrarNombre(5,nombre5);
        mostrarNombre(6,nombre6);

        mostrarPrecio(1,precio1);
        mostrarPrecio(2,precio2);
        mostrarPrecio(3,precio3);
        mostrarPrecio(4,precio4);
        mostrarPrecio(5,precio5);
        mostrarPrecio(6,precio6);


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

        private void mostrarNombre(int idProducto2, Label etiqueta) {

            // Conectar a la base de datos y recuperar el nombre del producto
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT nombre FROM productos WHERE idProductos = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, idProducto2);
        
                ResultSet resultSet = statement.executeQuery();
        
                if (resultSet.next()) {
                    // Obtener el nombre del producto del ResultSet
                    String nombreProducto = resultSet.getString("nombre");
                    // Establecer el nombre del producto en la etiqueta
                    etiqueta.setText(nombreProducto);
                } else {
                    System.out.println("No se encontró el producto en la base de datos con ID: " + idProducto2);
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }

        private void mostrarPrecio(int idProducto3, Label etiqueta) {

            // Conectar a la base de datos y recuperar el nombre del producto
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT precio FROM productos WHERE idProductos = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, idProducto3);
        
                ResultSet resultSet = statement.executeQuery();
        
                if (resultSet.next()) {
                    // Obtener el nombre del producto del ResultSet
                    String precio = resultSet.getString("precio");
                    precio += "€";
                    // Establecer el nombre del producto en la etiqueta
                    etiqueta.setText(precio);
                } else {
                    System.out.println("No se encontró el producto en la base de datos con ID: " + idProducto3);
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }

}

