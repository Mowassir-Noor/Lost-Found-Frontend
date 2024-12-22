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
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML
    private TextField emailField; // usernameField yerine emailField

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        // Set the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/com/vx/esyakayipsistemi/constants/images/gaziLogo.png"));
        logoImageView.setImage(logoImage);
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText(); // username yerine email
        String password = passwordField.getText();

        // Check if email or password fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Giriş Hatası", "Email veya şifre boş olamaz.");
            return;
        }

        try {
            JSONObject credentials = new JSONObject();
            credentials.put("email", email);
            credentials.put("password", password);

            String authUrl= ConfigUtils.getProperty("app.authUrl");
            String url=authUrl+"/login";

            HttpResponse<String> response = GlobalAuthHttpClient.post(url, String.valueOf(credentials));

            if (response.statusCode() != 200) {
                showAlert("Giriş Hatası", "Email veya şifre hatalı.");
                return;
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            String role = jsonResponse.getString("role");

            // Set the auth header for further operations
            GlobalAuthHttpClient.setBasicAuth(email, password);

            if (role.equals("ROLE_ADMIN")) {
                navigateTo("/com/vx/esyakayipsistemi/Pages/AdminPages/Pages/admin.fxml", "Admin Paneli");
            } else if (role.equals("ROLE_USER")) {
                navigateTo("/com/vx/esyakayipsistemi/Pages/UserPages/Pages/user.fxml", "Kullanıcı Ekranı");
            } else {
                showAlert("Giriş Hatası", "Geçersiz kullanıcı rolü.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showAlert("Giriş Hatası", "Bir hata oluştu: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleBack() {
        navigateTo("/com/vx/esyakayipsistemi/Pages/index.fxml", "Ana Ekran");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
            showAlert("Navigasyon Hatası", "Sayfa yüklenemedi: " + e.getMessage());
        }
    }
}