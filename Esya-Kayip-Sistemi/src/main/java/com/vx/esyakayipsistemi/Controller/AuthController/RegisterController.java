package com.vx.esyakayipsistemi.Controller.AuthController;

import com.vx.esyakayipsistemi.Utils.ConfigUtils;
import com.vx.esyakayipsistemi.Utils.GlobalAuthHttpClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton;

    @FXML
    private ImageView logoImageView;


    @FXML
    public void initialize() {
        // Set the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/com/vx/esyakayipsistemi/constants/images/gaziLogo.png"));
        logoImageView.setImage(logoImage);
    }


    @FXML
    private void handleRegister() throws Exception {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Kayıt Başarısız", "Lütfen tüm alanları doldurun.");
            return;
        }

        // Prepare JSON credentials
        JSONObject credentials = new JSONObject();
        credentials.put("username", username);
        credentials.put("password", password);
        credentials.put("email", email);


        String authUrl= ConfigUtils.getProperty("app.authUrl");

        String url=authUrl+"/register";
        // Send the registration request
        HttpResponse<String> response = GlobalAuthHttpClient.post(url, String.valueOf(credentials));

        // Process the response
        if (response.statusCode() == 200) {
            showAlert(Alert.AlertType.INFORMATION, "Kayıt Başarılı", "Kayıt başarılı! Hoş geldiniz " + username + "!");
            navigateTo("/com/vx/esyakayipsistemi/Pages/AuthPages/Pages/login.fxml", "Giriş Ekranı");
        } else {
            showAlert(Alert.AlertType.ERROR, "Kayıt Başarısız", "Kayıt başarısız: " + response.body());
        }
    }

    @FXML
    private void handleBack() {
        navigateTo("/com/vx/esyakayipsistemi/Pages/index.fxml", "Ana Ekran");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigasyon Hatası", "Sayfa yüklenemedi: " + e.getMessage());
        }
    }
}