package com.vx.esyakayipsistemi.Controller.AdminController;

import com.vx.esyakayipsistemi.Components.ItemCard;
//import com.vx.esyakayipsistemi.Trial.Checking;
import com.vx.esyakayipsistemi.Classes.Item;
import com.vx.esyakayipsistemi.Utils.ConfigUtils;
import com.vx.esyakayipsistemi.Utils.GlobalAuthHttpClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AdminController {





    private static final List<Item> itemList = new ArrayList<>();

    @FXML
    private FlowPane itemContainer;

    public static void addItem(Item item) throws Exception {

        String itemName = item.getName();
        String itemCategory = item.getCategory();
        String itemDescription = item.getDescription();
        String itemDate = item.getDate();
        String imagePath = item.getImagePath();

        // Görüntü yolundan doğru bir şekilde dosya nesnesi oluştur
        File file = new File(imagePath);

        // Sorunları önlemek için dosyanın mevcut olup olmadığını kontrol etme
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("The provided image path is invalid: " + imagePath);
        }

        System.out.println("Admin received valid file path for item: " + file.getAbsolutePath());

        // Build JSON request
        JSONObject itemsDto = new JSONObject();
        itemsDto.put("itemName", itemName);
        itemsDto.put("itemCategory", itemCategory);
        itemsDto.put("itemDescription", itemDescription);
        itemsDto.put("itemDate", itemDate);

        // API endpoint
        String baseUrl=ConfigUtils.getProperty("app.adminUrl");
        String url = baseUrl+"/items";

        // Dosya kaynağını POST isteğine güvenli bir şekilde ekleyin
        HttpResponse<String> response = GlobalAuthHttpClient.postWithJsonAndImage(url, itemsDto.toString(), file);

    }





    public static List<Item> getItemList() {
        return itemList;
    }

    @FXML
    private void initialize() throws IOException, InterruptedException {
        updateItemContainer();
    }

    @FXML
    public void updateItemContainer() throws IOException, InterruptedException {



        String baseUrl= ConfigUtils.getProperty("app.adminUrl");
        String url=baseUrl+"/items";

        //Get request gonder
        HttpResponse<String> response = GlobalAuthHttpClient.get(url);

        //Arraylist olustur
        ArrayList<JSONObject> list = new ArrayList<>();


        try {
            // Parsing as a Json array
            JSONArray jsonArray = new JSONArray(response.body());
            jsonArray.forEach(item -> list.add((JSONObject) item));

        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Failed to parse response to valid JSON. Please check the API output.");
            return;
        }


        itemContainer.getChildren().clear();


        for (JSONObject item : list) {

            byte[] image = item.isNull("imageData")
                    ? "default image".getBytes()
                    : Base64.getDecoder().decode(item.getString("imageData"));
            String name = item.getString("itemName");

            String description = item.isNull("itemDescription") ? "No description available"
                    : item.optString("itemDescription");

            String category = item.isNull("itemCategory") ? "No Category available"
                    : item.optString("itemCategory");

            String date = item.isNull("itemDate") ? "item Date unavailable" : item.optString("itemDate");



            VBox container = new VBox(10);

            ItemCard itemCard = new ItemCard(url, image, name, category, description, date,
                    card -> {
                String delBaseUrl=ConfigUtils.getProperty("app.adminUrl");

                String delUrl=delBaseUrl+"/items";
                        System.out.println(delUrl);
                        // Delete handler implementation
                        try {
                            HttpResponse<String> delResponse =GlobalAuthHttpClient.delete(delUrl,item.optLong("itemId") );
                            System.out.println(delResponse);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        itemContainer.getChildren().remove(card);
                    },
                    card -> {
                        // Preview handler implementation
                        handleDetailsButtonAction(new ActionEvent(card, null), item.optLong("itemId"));
                    },
                    this, // Pass the controller instance
                    item.optLong("itemId")); // Pass the item ID

            itemContainer.getChildren().add(itemCard);
        }
    }


    public void handleDetailsButtonAction(ActionEvent event, Long itemId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vx/esyakayipsistemi/Pages/AdminPages/Pages/itemPreview.fxml"));
            Parent itemPreviewRoot = loader.load();

            ItemPreviewController itemPreviewController = loader.getController();

            String adminUrl=ConfigUtils.getProperty("app.adminUrl");
            String url = adminUrl+"/items/" + itemId.toString();
            HttpResponse<String> response = GlobalAuthHttpClient.get(url);
            JSONObject item = new JSONObject(response.body());

            String itemName = item.getString("itemName");
            System.out.println(itemName);
            String itemCategory = item.getString("itemCategory");
            String itemDescription = item.getString("itemDescription");
            String itemDate = item.getString("itemDate");
            byte[] imageData = item.isNull("imageData") ? "default image".getBytes()
                    : Base64.getDecoder().decode(item.getString("imageData"));

            itemPreviewController.setItemDetail(itemName, itemCategory, itemDescription, itemDate, imageData);
            itemPreviewController.setItemId(itemId);
            itemPreviewController.setAdminController(this);

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleNewItem() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/vx/esyakayipsistemi/Pages/AdminPages/Pages/addItem.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Yeni Eşya Ekle");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Yeni eklenen eşyaları güncelle
            updateItemContainer();
//            UserController.updateItemContainer(); // UserController'ı çağırarak güncellemeleri yap
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/vx/esyakayipsistemi/Pages/index.fxml"));
            Stage stage = (Stage) itemContainer.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ana Ekran");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
