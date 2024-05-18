package com.hand2hand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar todos los productos en los primeros huecos disponibles
        mostrarTodosLosProductos(huecos, etiquetasNombres, etiquetasPrecios);

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
    private Button botonAgregarSaldo;

    @FXML
    private void irSaldo() {
        // Cambiar a la página principal
        // Aquí abrimos la página Principal.fxml
        try {
            Stage stage = (Stage) botonAgregarSaldo.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Saldo.fxml"))));
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
    
        
        private void mostrarTodosLosProductos(ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios) {
        // Realiza una consulta para obtener todos los productos
        String sql = "SELECT imagen, nombre, precio FROM productos";
        mostrarProductos(sql, huecos, etiquetasNombres, etiquetasPrecios);
        }

        private void mostrarProductosPorCategoria(String categoria, ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios) {
            // Realiza una consulta para obtener los productos de la categoría especificada
            String sql = "SELECT imagen, nombre, precio FROM productos WHERE categoria = ?";
            mostrarProductos(sql, huecos, etiquetasNombres, etiquetasPrecios, categoria);
        }

    private void mostrarProductos(String sql, ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios, Object... params) {
            
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);

            // Si hay parámetros, establece los valores
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            int index = 0;
            while (resultSet.next() && index < huecos.length) {
                Blob imagenBlob = resultSet.getBlob("imagen");
                InputStream inputStream = imagenBlob.getBinaryStream();
                Image imagen = new Image(inputStream);
                huecos[index].setImage(imagen);

                String nombreProducto = resultSet.getString("nombre");
                etiquetasNombres[index].setText(nombreProducto);

                String precio = resultSet.getString("precio") + "€";
                etiquetasPrecios[index].setText(precio);

                index++;
            }

            // Limpiar los elementos restantes si no hay suficientes productos para llenar todos los huecos
            for (int i = index; i < huecos.length; i++) {
                huecos[i].setImage(null);
                etiquetasNombres[i].setText("");
                etiquetasPrecios[i].setText("");
            }
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}


    private Button botonFiltrarCoches;
    private Button botonFiltrarMotos;
    private Button botonFiltrarHogar;
    private Button botonFiltrarDeporte;
    private Button botonFiltrarModa;
    private Button botonMostrarTodos;


    @FXML
    private void filtrarCoches() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Moda" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Coches", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarMotos() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Moda" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Motos", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarHogar() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Moda" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Hogar", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarDeporte() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Moda" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Deporte", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarModa() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Moda" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Moda", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void mostrarTodos() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar todos los productos en los primeros huecos disponibles
        mostrarTodosLosProductos(huecos, etiquetasNombres, etiquetasPrecios);
    }
        


    @FXML
    private Button prod1;

    @FXML
    private Button prod2;

    @FXML
    private Button prod3;

    @FXML
    private Button prod4;

    @FXML
    private Button prod5;

    @FXML
    private Button prod6;

    private ProductoController productoController;


    @FXML
    private void vistaPrevia1() {

        Main.idProd=1;
        

        try {
            Stage stage = (Stage) prod1.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vistaPrevia2() {

        Main.idProd=2;
        
        try {
            Stage stage = (Stage) prod2.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vistaPrevia3() {

        Main.idProd=3;
        

        try {
            Stage stage = (Stage) prod3.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vistaPrevia4() {

        Main.idProd=4;
        

        try {
            Stage stage = (Stage) prod4.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vistaPrevia5() {

        Main.idProd=5;
        

        try {
            Stage stage = (Stage) prod5.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void vistaPrevia6() {
        

        Main.idProd=6;

        try {
            Stage stage = (Stage) prod6.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
   

}

