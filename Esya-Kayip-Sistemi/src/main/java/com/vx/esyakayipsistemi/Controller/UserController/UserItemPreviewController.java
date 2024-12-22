package com.vx.esyakayipsistemi.Controller.UserController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;


public class UserItemPreviewController {

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


    private UserController userController;


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

    //item Detail set et
    public void setItemDetail(String title, String category, String description, String date, byte[] image) {
        itemTitle.setText(title);
        itemCategory.setText("Category:   "+category);
        itemDescription.setText("Description:    "+description);
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

    public void setUserController(UserController userController) {
        this.userController= userController;
    }




}