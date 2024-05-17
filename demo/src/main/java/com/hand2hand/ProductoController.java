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
    private ImageView imagen;

    @FXML
    private Label nombre;

    @FXML
    private Label descripcion;

    @FXML
    private Label precio;

    @FXML
    private Label anyo;

    

    int id = Main.idProd;

    @FXML
    private void initialize() {
        // Llama a mostrarProducto() cuando se inicializa el controlador
        mostrarProducto(id);
        //botonComprarVender.setOnAction(event -> comprarVender(id));
    }

    @FXML
    private void mostrarProducto(int id) {
        // Conectar a la base de datos y recuperar la información del producto
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
    private Button botonComprarVender;

    int i;

    @FXML
    private void comprarVender() {
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Eliminar el producto actual
            String deleteSql = "DELETE FROM `hand2hand`.`productos` WHERE (`idProductos` = ?);";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, Main.idProd);
            deleteStatement.executeUpdate();
            System.out.println("Producto " + Main.idProd + " vendido o eliminado");

            // Cambiar la idProductos a una menos 
            for (i = Main.idProd + 1; i <= 6; i++) {
                String updateSql = "UPDATE `hand2hand`.`productos` SET `idProductos` = ? WHERE (`idProductos` = ?);";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, i - 1); // Nuevo valor de idProductos
                updateStatement.setInt(2, i);     // Valor actual de idProductos
                updateStatement.executeUpdate();
                System.out.println("Reposicionado el producto " + i);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    
}
