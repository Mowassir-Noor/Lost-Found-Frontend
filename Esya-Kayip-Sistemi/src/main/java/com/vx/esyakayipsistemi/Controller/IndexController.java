package com.vx.esyakayipsistemi.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class IndexController {
    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        // Set the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/com/vx/esyakayipsistemi/constants/images/gaziLogo.png"));
        logoImageView.setImage(logoImage);
    }

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private void handleLoginButtonAction() {
        try {
            // login.fxml dosyasını yükle
            Parent root = FXMLLoader.load(getClass().getResource("/com/vx/esyakayipsistemi/Pages/AuthPages/Pages/login.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Giriş Ekranı");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegisterButtonAction() {
        try {
            // register.fxml dosyasını yükle
            Parent root = FXMLLoader.load(getClass().getResource("/com/vx/esyakayipsistemi/Pages/AuthPages/Pages/register.fxml"));
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Kayıt Ekranı");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
