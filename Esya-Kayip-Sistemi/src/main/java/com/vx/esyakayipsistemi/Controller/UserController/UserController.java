package com.vx.esyakayipsistemi.Controller.UserController;

import com.vx.esyakayipsistemi.Components.UserItemCard;
import com.vx.esyakayipsistemi.Classes.User;
import com.vx.esyakayipsistemi.Utils.ConfigUtils;
import com.vx.esyakayipsistemi.Utils.GlobalAuthHttpClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private FlowPane userItemContainer; // Removed static keyword

    private static List<User> userList = new ArrayList<>();

    static {
        // Add admin user to the list
        userList.add(new User("admin", "admin@gmail.com", "1234"));
    }

    public static List<User> getUserList() {
        return userList;
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Kayıt Hatası", "Tüm alanları doldurunuz.");
            return;
        }

        userList.add(new User(username, email, password));

        showAlert("Kayıt Başarılı", "Kullanıcı başarıyla kaydedildi.");

        navigateTo("/com/vx/esyakayipsistemi/Pages/AuthPages/Pages/login.fxml", "Giriş Yap");
    }

    @FXML
    private void handleBack() {
        navigateTo("/com/vx/esyakayipsistemi/Pages/index.fxml", "Ana Ekran");
    }

    @FXML
    private void initialize() throws IOException, InterruptedException {
        updateItemContainer();
    }

    @FXML
    public void updateItemContainer() throws IOException, InterruptedException {

        String userUrl= ConfigUtils.getProperty("app.userUrl");
        String url =userUrl+ "/items";

        HttpResponse<String> response = GlobalAuthHttpClient.get(url);
        ArrayList<JSONObject> list = new ArrayList<>();

        try {
            // Parsing as a JSON array
            JSONArray jsonArray = new JSONArray(response.body());
            jsonArray.forEach(item -> list.add((JSONObject) item));
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Failed to parse response to valid JSON. Please check the API output.");
            return;
        }

        userItemContainer.getChildren().clear();

        for (JSONObject item : list) {
            byte[] image = item.isNull("imageData") ? "default image".getBytes() : Base64.getDecoder().decode(item.getString("imageData"));
            String name = item.getString("itemName");
            String description = item.optString("itemDescription", "No description available");
            String category = item.optString("itemCategory", "No Category available");
            String date = item.optString("itemDate", "item Date unavailable");

            VBox container = new VBox(10);

            UserItemCard itemCard = new UserItemCard(url, image, name, category, description, date,

                    card -> {
                        // Preview handler implementation
                        handleDetailsButtonAction(new ActionEvent(card, null), item.optLong("itemId"));
                    },
                    this, // Pass the controller instance
                    item.optLong("itemId")); // Pass the item ID

            userItemContainer.getChildren().add(itemCard);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) userItemContainer.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigasyon Hatası", "Sayfa yüklenemedi: " + e.getMessage());
        }
    }

    public void handleDetailsButtonAction(ActionEvent event, Long itemId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vx/esyakayipsistemi/Pages/UserPages/Pages/userItemPreview.fxml"));
            Parent itemPreviewRoot = loader.load();

            UserItemPreviewController itemPreviewController = loader.getController();

            String userUrl=ConfigUtils.getProperty("app.userUrl");

            String url = userUrl+"items/" + itemId.toString();

            HttpResponse<String> response = GlobalAuthHttpClient.get(url);
            JSONObject item = new JSONObject(response.body());

            String itemName = item.getString("itemName");
            String itemCategory = item.getString("itemCategory");
            String itemDescription = item.getString("itemDescription");
            String itemDate = item.getString("itemDate");
            byte[] imageData = item.isNull("imageData") ? "default image".getBytes()
                    : Base64.getDecoder().decode(item.getString("imageData"));

            itemPreviewController.setItemDetail(itemName, itemCategory, itemDescription, itemDate, imageData);
            itemPreviewController.setItemId(itemId);
            itemPreviewController.setUserController(this);

            Scene currentScene = ((Node) event.getSource()).getScene();
            itemPreviewController.setPreviousScene(currentScene);

            Scene scene = new Scene(itemPreviewRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load item details. Please try again later.");
        }
    }
}