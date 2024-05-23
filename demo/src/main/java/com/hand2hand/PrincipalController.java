package com.hand2hand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private static final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
    private static final String USER = "root"; // Cambia por tu nombre de usuario
    private static final String PASSWORD = "root"; // Cambia por tu contraseña

    private int paginaActual = 0;
    private static final int TAMANO_PAGINA = 6;

    @FXML
    private Button botonAnterior;
    @FXML
    private Button botonSiguiente;

    @FXML
    private void initialize() {
    
        actualizar();

    }

    private void actualizar(){
    ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};
        paginaActual = 0; // Iniciar en la primera página
        mostrarPaginaActual(huecos, etiquetasNombres, etiquetasPrecios);
        contarTotalProductos();
    }

    private void mostrarProductos(String sql, ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios, int offset, Object... params) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Si hay parámetros, establece los valores
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            // Establecer los parámetros adicionales para limitar y offset
            statement.setInt(params.length + 1, TAMANO_PAGINA);
            statement.setInt(params.length + 2, offset);
    
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
    
            // Limpiar los huecos restantes si hay menos de 6 productos
            for (int i = index; i < huecos.length; i++) {
                huecos[i].setImage(null);
                etiquetasNombres[i].setText("");
                etiquetasPrecios[i].setText("");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
    

    @FXML
    private void irPaginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            actualizarVistaProductos();
        }
    }
    
    @FXML
    private void irPaginaSiguiente() {
        if ((paginaActual + 1) * TAMANO_PAGINA < contarTotalProductos()) {
            paginaActual++;
            actualizarVistaProductos();
        }
    }
    
    private void actualizarVistaProductos() {
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};
        mostrarPaginaActual(huecos, etiquetasNombres, etiquetasPrecios);
    }
    
    private void mostrarPaginaActual(ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios) {
        String sql = "SELECT imagen, nombre, precio FROM productos LIMIT ? OFFSET ?";
        int offset = paginaActual * TAMANO_PAGINA;
        mostrarProductos(sql, huecos, etiquetasNombres, etiquetasPrecios, offset);
    }
    
    private int contarTotalProductos() {
        String sql = "SELECT COUNT(*) FROM productos";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Main.totalProductos=resultSet.getInt(1);
                return resultSet.getInt(1);
               
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return 0;
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

    @FXML
    private void mostrarTodos() {
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};
        paginaActual = 0;
        mostrarTodosLosProductos(huecos, etiquetasNombres, etiquetasPrecios);
    }

    private void mostrarTodosLosProductos(ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios) {
        String sql = "SELECT imagen, nombre, precio FROM productos LIMIT ? OFFSET ?";
        int offset = paginaActual * TAMANO_PAGINA;
        mostrarProductos(sql, huecos, etiquetasNombres, etiquetasPrecios, offset);
    }


    private void mostrarProductosPorCategoria(String categoria, ImageView[] huecos, Label[] etiquetasNombres, Label[] etiquetasPrecios) {
        String sql = "SELECT imagen, nombre, precio FROM productos WHERE categoria = ? LIMIT ? OFFSET ?";
        int offset = paginaActual * TAMANO_PAGINA;
        mostrarProductos(sql, huecos, etiquetasNombres, etiquetasPrecios, offset, categoria);
    }
    

    @FXML
    private Button botonFiltrarCoches;
    @FXML
    private Button botonFiltrarMotos;
    @FXML
    private Button botonFiltrarHogar;
    @FXML
    private Button botonFiltrarDeporte;
    @FXML
    private Button botonFiltrarModa;
    @FXML
    private Button botonMostrarTodos;

    @FXML
    private void filtrarCoches() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Coches" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Coches", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarMotos() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Motos" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Motos", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarHogar() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Hogar" en los primeros huecos disponibles
        mostrarProductosPorCategoria("Hogar", huecos, etiquetasNombres, etiquetasPrecios);
    }

    @FXML
    private void filtrarDeporte() {
        // Define los huecos y etiquetas de nombre y precio asociados
        ImageView[] huecos = {hueco1, hueco2, hueco3, hueco4, hueco5, hueco6};
        Label[] etiquetasNombres = {nombre1, nombre2, nombre3, nombre4, nombre5, nombre6};
        Label[] etiquetasPrecios = {precio1, precio2, precio3, precio4, precio5, precio6};

        // Mostrar productos de la categoría "Deporte" en los primeros huecos disponibles
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
    private void vistaPrevia(ActionEvent event) {
        Button button = (Button) event.getSource();
        int id = Integer.parseInt(button.getId().substring(4)); // Extraer el número del ID del botón
        int adjustedId = id + (paginaActual * TAMANO_PAGINA); // Ajustar el ID en función de la página actual
        Main.idProd = adjustedId;

        try {
            Stage stage = (Stage) button.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hand2hand/fxml/Producto.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador del producto y establecer el producto correspondiente
            productoController = loader.getController();
            productoController.mostrarProducto(adjustedId);
            
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
