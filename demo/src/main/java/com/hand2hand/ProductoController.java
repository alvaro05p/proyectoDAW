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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    public void mostrarProducto(int id) {
        // Conectar a la base de datos y recuperar la información del producto
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT nombre, descripcion, precio, anyo, imagen, vendedor FROM productos WHERE idProductos = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombre.setText(resultSet.getString("nombre"));
                descripcion.setText(resultSet.getString("descripcion"));
                precio.setText(String.valueOf(resultSet.getDouble("precio")));
                anyo.setText(String.valueOf(resultSet.getInt("anyo")));
                
                if (resultSet.getString("vendedor").equals(Main.registradoAhora)){

                    botonComprarVender.setText("Eliminar");
                }else{
                    botonComprarVender.setText("Comprar");
                }
                
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
            // Obtener el precio del producto actual
            String precioProducto = "SELECT precio, vendedor FROM productos WHERE idProductos = ?";
            PreparedStatement saberPrecio = connection.prepareStatement(precioProducto);
            saberPrecio.setInt(1, Main.idProd);
            ResultSet precioResultSet = saberPrecio.executeQuery();
            double precio = 0.0;
            String vendedor = "";
            if (precioResultSet.next()) {
                precio = precioResultSet.getDouble("precio");
                vendedor = precioResultSet.getString("vendedor");
            }
    
            // Obtener el saldo del usuario registrado
            double saldoUsuario = 0.0;
            String saldoUsuarioQuery = "SELECT saldo FROM usuarios WHERE nombre = ?";
            PreparedStatement saldoUsuarioStatement = connection.prepareStatement(saldoUsuarioQuery);
            saldoUsuarioStatement.setString(1, Main.registradoAhora);
            ResultSet saldoUsuarioResultSet = saldoUsuarioStatement.executeQuery();
            if (saldoUsuarioResultSet.next()) {
                saldoUsuario = saldoUsuarioResultSet.getDouble("saldo");
            }
    
            // Verificar si el producto es del usuario y actuar en consecuencia
            if (!vendedor.equals(Main.registradoAhora)) {
                // Verificar si el saldo es suficiente para realizar la compra
                if (saldoUsuario >= precio) {
                    // Restar el precio del producto al saldo del usuario
                    String restarSaldo = "UPDATE usuarios SET saldo = saldo - ? WHERE nombre = ?";
                    PreparedStatement restarSaldoStatement = connection.prepareStatement(restarSaldo);
                    restarSaldoStatement.setDouble(1, precio);
                    restarSaldoStatement.setString(2, Main.registradoAhora);
                    restarSaldoStatement.executeUpdate();
    
                    // Eliminar el producto actual
                    String deleteSql = "DELETE FROM productos WHERE idProductos = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setInt(1, Main.idProd);
                    deleteStatement.executeUpdate();
                    System.out.println("Producto " + Main.idProd + " vendido o eliminado");
    
                    // Reposicionar el resto de productos
                    for (int i = Main.idProd + 1; i <= Main.totalProductos; i++) {
                        String updateSql = "UPDATE productos SET idProductos = ? WHERE idProductos = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                        updateStatement.setInt(1, i - 1);
                        updateStatement.setInt(2, i);
                        updateStatement.executeUpdate();
                        System.out.println("Reposicionado el producto " + i);
                    }
    
                    // Redirigir a la página principal
                    try {
                        Stage stage = (Stage) botonComprarVender.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Mostrar mensaje de saldo insuficiente
                    mostrarAvisoSinSaldo();
                }
            } else {
                // Si el producto es del usuario, simplemente elimínalo sin restar saldo
                String deleteSql = "DELETE FROM productos WHERE idProductos = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, Main.idProd);
                deleteStatement.executeUpdate();
                System.out.println("Producto " + Main.idProd + " eliminado");
    
                // Reposicionar el resto de productos
                for (int i = Main.idProd + 1; i <= Main.totalProductos; i++) {
                    String updateSql = "UPDATE productos SET idProductos = ? WHERE idProductos = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                    updateStatement.setInt(1, i - 1);
                    updateStatement.setInt(2, i);
                    updateStatement.executeUpdate();
                    System.out.println("Reposicionado el producto " + i);
                }
    
                // Volver a la página principal
                try {
                    Stage stage = (Stage) botonComprarVender.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    


    private void mostrarAvisoSinSaldo() {

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Saldo insuficiente");
        alert.setContentText("Lo siento, no tienes suficiente saldo para comprar este producto.");

        // Agregar una imagen al aviso (opcional)
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.showAndWait();
    }
    
}
