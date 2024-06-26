package com.hand2hand;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/hand2hand/fxml/Login.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("hand2hand");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int idProd;

    public static String registradoAhora;

    public static int totalProductos;
 
}
