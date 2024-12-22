package com.vx.esyakayipsistemi.Controller.AdminController;

import com.vx.esyakayipsistemi.Classes.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AddItemController {
    @FXML
    private TextArea itemDescriptionField;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField itemCategoryField;

    @FXML
    private TextField itemDateField;

    @FXML
    private ImageView itemImageView;

    private String itemImagePath;

    @FXML
    private void handleSaveItem() throws Exception {
        String itemName = itemNameField.getText();
        String itemCategory = itemCategoryField.getText();
        String itemDescription = itemDescriptionField.getText();
        String itemDate = itemDateField.getText();


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("itemName",itemName);
        jsonObject.put("itemCategory",itemCategory);
        jsonObject.put("itemDescription",itemDescription);
        jsonObject.put("itemDate",itemDate);



       String url="http://localhost:8080/admin/items";

        if (!itemName.isEmpty() && !itemCategory.isEmpty() && !itemDate.isEmpty()) {


            Item newItem = new Item(itemName, itemCategory,itemDescription,itemDate, itemImagePath);

            AdminController.addItem(newItem);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ekleme Başarılı");
            alert.setHeaderText(null);
            alert.setContentText("Yeni eşya başarıyla eklendi.");
            alert.showAndWait();

            // Ekleme işlemi sonrası AddItem penceresini kapat
            Stage stage = (Stage) itemNameField.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ekleme Başarısız");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurun.");
            alert.showAndWait();
        }
    }



    public void handleImageUpload(ActionEvent actionEvent) {
        // file chooser nesne olustur
        FileChooser fileChooser = new FileChooser();

        //fotolarin tipine gore filtrele
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );


        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            try (
                    FileInputStream fis = new FileInputStream(file)) {
                byte[] itemImageData = fis.readAllBytes();
                itemImageView.setImage(new Image(file.toURI().toString()));

                // Uyumluluk için mutlak yolu dosya yolu olarak sakla
                itemImagePath = file.getAbsolutePath();

//                debugging icin printle
                System.out.println(itemImagePath);

                System.out.println("Resim başarıyla yüklendi!");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Dosya hatası oluştu: Görüntü yüklenemedi.");
            }
        } else {
            System.out.println("Resim yüklemesi kullanıcı tarafından iptal edildi.\n");
        }
    }


    public void handleCancel(ActionEvent actionEvent) {

            // Tüm giriş alanlarını temizle
            itemNameField.clear();
            itemDescriptionField.clear();
            itemCategoryField.clear();
            itemDateField.clear();

            // Görüntüyü temizle ve itemImageData'yı sıfırla
            itemImageView.setImage(null);


            // İsteğe bağlı olarak bir onay veya geri bildirim göster
            System.out.println("Form reset successfully!");
//

    }
}
