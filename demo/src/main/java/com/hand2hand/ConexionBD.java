package com.hand2hand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ConexionBD {
    // Configuración de la conexión
    static final String URL = "jdbc:mysql://localhost:3306/hand2hand"; // Cambia por la URL de tu base de datos
    static final String USER = "root"; // Cambia por tu nombre de usuario
    static final String PASSWORD = "root"; // Cambia por tu contraseña

    public static void main(String[] args) {
        // Intentar establecer la conexión
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión exitosa a la base de datos");

            // Query para insertar datos sin incluir la columna idUsuario
            String sql = "INSERT INTO usuarios (correo, nombre, contra) VALUES (?,?,?)";

            // Crear una declaración preparada
            PreparedStatement statement = connection.prepareStatement(sql);
            
        

            // Asignar valores a los parámetros de la declaración preparada
            statement.setString(1, "pepe@gmail.com"); // Asigna una cadena para correo
            statement.setString(2, "pepito33"); // Asigna una cadena para contra
            statement.setString(3, "hola1234"); // Asigna una cadena para contra

            // Ejecutar la consulta de inserción
            int filasInsertadas = statement.executeUpdate();
            System.out.println("Se insertaron " + filasInsertadas + " filas");


        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}

