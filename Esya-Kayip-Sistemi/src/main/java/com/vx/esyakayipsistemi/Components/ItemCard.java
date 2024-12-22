package com.vx.esyakayipsistemi.Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.io.ByteArrayInputStream;
import java.util.function.Consumer;
import com.vx.esyakayipsistemi.Controller.AdminController.AdminController;
import javafx.event.ActionEvent;

public class ItemCard extends BorderPane {

    public ItemCard(String url, byte[] imageData, String name, String category, String description, String date, Consumer<ItemCard> deleteHandler, Consumer<ItemCard> previewHandler, AdminController adminController, Long itemId) {
        // Set padding and style for the card
        this.setPadding(new Insets(15));
        this.getStyleClass().add("item-card");

        // Set preferred size for the card
        this.setPrefSize(250, 400);

        // Item Image
        ImageView itemImage = new ImageView(new Image(new ByteArrayInputStream(imageData)));
        itemImage.setFitWidth(150);
        itemImage.setFitHeight(150);
        itemImage.setPreserveRatio(true);
        itemImage.setSmooth(true);
        itemImage.setCache(true);
        this.setTop(itemImage);
        BorderPane.setAlignment(itemImage, Pos.CENTER);

        // Item Name
        Text itemName = new Text(name);
        itemName.setFont(Font.font("Arial", 18));
        itemName.setFill(Color.BLACK);
        itemName.setTextAlignment(TextAlignment.CENTER);
        itemName.setWrappingWidth(200);

        // Item Category
        Text itemCategory = new Text("Category: " + category);
        itemCategory.setFont(Font.font("Arial", 14));
        itemCategory.setFill(Color.DARKBLUE);
        itemCategory.setTextAlignment(TextAlignment.CENTER);
        itemCategory.setWrappingWidth(200);


        // Item Date
        Text itemDate = new Text("Date: " + date);
        itemDate.setFont(Font.font("Arial", 14));
        itemDate.setFill(Color.DARKGRAY);
        itemDate.setWrappingWidth(200);

        VBox detailsBox = new VBox(5, itemName, itemCategory, itemDate);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(10, 0, 10, 0));
        this.setCenter(detailsBox);

        // Action Buttons
        Button detailsButton = new Button("View Details");
        detailsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        detailsButton.setOnMouseEntered(e -> detailsButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white;"));
        detailsButton.setOnMouseExited(e -> detailsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        detailsButton.setOnAction(e -> adminController.handleDetailsButtonAction(new ActionEvent(detailsButton, null), itemId));

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
        deleteButton.setOnAction(e -> deleteHandler.accept(this));

        HBox buttonBox = new HBox(10, detailsButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        this.setBottom(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(10, 0, 0, 0));
    }
}