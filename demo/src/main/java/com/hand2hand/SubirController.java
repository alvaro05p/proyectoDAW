package com.hand2hand;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubirController {

    @FXML
    private Button botonSeleccionar;

    @FXML
    private ImageView imagenView;

    @FXML
    private Button botonSubir;

    byte[] imagenBytes;

    @FXML
    private void seleccionarImagen() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Imagen del producto");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Todos los archivos", "*.*")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {

            // Cargar la imagen en el ImageView
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            imagenView.setImage(imagen);

            imagenBytes = Files.readAllBytes(archivoSeleccionado.toPath());
        }
    }

@FXML
private TextField nombre;

@FXML
private TextField descripcion;

@FXML
private TextField precio;

@FXML
private TextField anyo;

@FXML
private Button botonSubirProducto;



@FXML
private void subirProducto() {
    final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
    final String USER = "root"; // Cambia por tu nombre de usuario
    final String PASSWORD = "root"; // Cambia por tu contraseña
    
    // Obtener el texto de los campos de texto
    String nombreTexto = nombre.getText();
    String descripcionTexto = descripcion.getText();
    String precioTexto = precio.getText();
    String anyoTexto = anyo.getText();


    // Convertir el texto del precio y el año a un valor numérico
    int precio = Integer.parseInt(precioTexto);
    int anyo = Integer.parseInt(anyoTexto);

    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
        System.out.println("Conexión exitosa a la base de datos");

        // Query para insertar datos sin incluir la columna idUsuario
        String sql = "INSERT INTO productos (nombre, descripcion, precio, anyo, imagen) VALUES (?, ?, ?, ?, ?)";

        // Crear una declaración preparada
        PreparedStatement statement = connection.prepareStatement(sql);

        // Asignar valores a los parámetros de la declaración preparada
        statement.setString(1, nombreTexto); // Asigna una cadena para nombre
        statement.setString(2, descripcionTexto); // Asigna una cadena para descripcion
        statement.setInt(3, precio); // Asigna un entero para precio
        statement.setInt(4, anyo);
        statement.setBytes(5,imagenBytes);

        // Ejecutar la consulta de inserción
        int filasInsertadas = statement.executeUpdate();
        System.out.println("Se insertaron " + filasInsertadas + " filas");

        Stage stage = (Stage) botonSubirProducto.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Principal.fxml"))));

    } catch (SQLException | IOException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}


}


