package com.vx.esyakayipsistemi.Components;

import com.vx.esyakayipsistemi.Controller.UserController.UserController;
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

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.scene.text.TextAlignment;

public class  UserItemCard extends BorderPane {
//    detailsButton.setOnAction(e -> userController.handleDetailsButtonAction(new ActionEvent(detailsButton, null), itemId));

    public UserItemCard(String url, byte[] imageData, String name, String category, String description, String date, Consumer<ItemCard> previewHandler, UserController userController , Long itemId) {
//        this.getStyleClass().add("user-item-card");
//
//
//        this.setPrefSize(250, 400);
//
//        // Image container with fixed size
//        ImageView itemImage = new ImageView(new Image(new ByteArrayInputStream(imageData)));
//        itemImage.setFitWidth(150);
//        itemImage.setFitHeight(150);
//        itemImage.setPreserveRatio(true);
//
//        // Container for image to ensure fixed size
//        VBox imageContainer = new VBox(itemImage);
//        imageContainer.setAlignment(Pos.CENTER);
//        imageContainer.setMinHeight(150);
//        imageContainer.setMaxHeight(150);
//        this.setTop(imageContainer);
//        BorderPane.setAlignment(imageContainer, Pos.CENTER);
//
//        // Item Details
//        Text itemName = new Text(name);
//        itemName.getStyleClass().add("item-name");
//
//        Text itemCategory = new Text("Category: " + category);
//        itemCategory.getStyleClass().add("item-category");
//
//        Text itemDate = new Text("Date: " + date);
//        itemDate.getStyleClass().add("item-date");
//
//        VBox detailsBox = new VBox(10, itemName, itemCategory, itemDate);
//        detailsBox.setAlignment(Pos.CENTER);
//        detailsBox.setPadding(new Insets(10, 0, 0, 0));
//        this.setCenter(detailsBox);
//
//        // Action Button
//        Button detailsButton = new Button("View Details");
//        detailsButton.getStyleClass().add("details-button");
//        detailsButton.setOnAction(e -> userController.handleDetailsButtonAction(new ActionEvent(detailsButton,null),itemId));
//
//        HBox buttonBox = new HBox(10, detailsButton);
//        buttonBox.setAlignment(Pos.CENTER);
//        buttonBox.setPadding(new Insets(10, 0, 0, 0));
//        this.setBottom(buttonBox);
//        BorderPane.setMargin(buttonBox, new Insets(10, 0, 0, 0));

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
        detailsButton.setOnAction(e -> userController.handleDetailsButtonAction(new ActionEvent(detailsButton, null), itemId));

//        Button deleteButton = new Button("Delete");
//        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
//        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;"));
//        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
//        deleteButton.setOnAction(e -> deleteHandler.accept(this));

        HBox buttonBox = new HBox(10, detailsButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        this.setBottom(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(10, 0, 0, 0));
    }
}