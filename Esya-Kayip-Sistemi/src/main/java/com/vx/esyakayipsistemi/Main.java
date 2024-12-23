package com.vx.esyakayipsistemi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Başlangıçta index.fxml dosyasını yükle
            Parent root = FXMLLoader.load(getClass().getResource("/com/vx/esyakayipsistemi/Pages/index.fxml"));

            // Ana sahne oluştur
            Scene scene = new Scene(root);

            // Sahneyi ve başlığı ayarla
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ana Ekran");

            // Sahneyi göster
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
