package com.hand2hand;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProductoController {
    
    

    @FXML
    private Button botonSalir;
    
    @FXML
    private void salir() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) botonSalir.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
    static final String USER = "root"; // Cambia por tu nombre de usuario
    static final String PASSWORD = "root"; // Cambia por tu contraseña
 
    @FXML
    ImageView imagen;

    @FXML
    Label nombre;

    @FXML
    Label descripcion;

    @FXML
    Label precio;

    @FXML 
    Label anyo;

    @FXML
    private void mostrarProducto(int id) {

            // Conectar a la base de datos y recuperar la imagen
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT nombre, descripcion, precio, anyo, imagen FROM productos WHERE idProductos = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

               if (resultSet.next()) {
            nombre.setText(resultSet.getString("nombre"));
            descripcion.setText(resultSet.getString("descripcion"));
            precio.setText(String.valueOf(resultSet.getDouble("precio")));
            anyo.setText(String.valueOf(resultSet.getInt("anyo")));

            Blob imagenBlob = resultSet.getBlob("imagen");
            if (imagenBlob != null) {
                InputStream inputStream = imagenBlob.getBinaryStream();
                Image imagenProducto = new Image(inputStream);
                imagen.setImage(imagenProducto);
            }
        }

                 
                
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }

    }

    
    @FXML
    private void initialize() {
        // Llama a mostrarProducto() cuando se inicializa el controlador
        mostrarProducto(Main.idProd);
    }

}
