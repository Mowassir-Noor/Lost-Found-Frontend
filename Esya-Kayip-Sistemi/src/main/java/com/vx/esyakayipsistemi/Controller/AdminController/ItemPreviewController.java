package com.vx.esyakayipsistemi.Controller.AdminController;

import com.vx.esyakayipsistemi.Utils.ConfigUtils;
import com.vx.esyakayipsistemi.Utils.GlobalAuthHttpClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.net.http.HttpResponse;

public class ItemPreviewController {

    @FXML
    private Text itemTitle;

    @FXML
    private ImageView itemImageView;

    @FXML
    private Text itemCategory;

    @FXML
    private Text itemDescription;

    @FXML
    private Text itemDate;

    private Long itemId;
    private Scene previousScene;
    private AdminController adminController;

    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        // Handle the edit action, e.g., open an edit form
        showAlert("Edit Action", "Edit button clicked!");
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        try {

            String adminUrl= ConfigUtils.getProperty("app.adminUrl");

           String url=adminUrl+"/items/"+itemId.toString();

            // Öğeyi silmek için sunucuya bir SİLME isteği gönderin
           HttpResponse<String> response= GlobalAuthHttpClient.delete(url);

            System.out.println(response.body());
            System.out.println(response.statusCode());

            if (response.statusCode() == 200) {
                // Successfully deleted the item
                showAlert("Success", "Item deleted successfully!");


                // Navigate back to the previous scene
                adminController.updateItemContainer();
                handleBackButtonAction(event);

            } else {
                // Failed to delete the item
                showAlert("Error", "Failed to delete the item. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while deleting the item. Please try again.");
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        // Navigate back to the previous scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close();
        }


    }

    public void setItemDetail(String title, String category, String description, String date, byte[] image) {
        itemTitle.setText(title);
        itemCategory.setText("Category:   "+category);
        itemDescription.setText("Description:   "+description);
        itemDate.setText("Date:   "+date);
        if (image != null && image.length > 0) {
            Image itemImage = new Image(new ByteArrayInputStream(image));
            itemImageView.setImage(itemImage);
        }
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}