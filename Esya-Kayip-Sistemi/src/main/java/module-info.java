module com.vx.esyakayipsistemi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires annotations;

    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.httpmime;


    opens com.vx.esyakayipsistemi to javafx.fxml;
    exports com.vx.esyakayipsistemi;
    exports com.vx.esyakayipsistemi.Controller;
    opens com.vx.esyakayipsistemi.Controller to javafx.fxml;
    exports com.vx.esyakayipsistemi.Controller.AuthController;
    opens com.vx.esyakayipsistemi.Controller.AuthController to javafx.fxml;
    exports com.vx.esyakayipsistemi.Controller.AdminController;
    opens com.vx.esyakayipsistemi.Controller.AdminController to javafx.fxml;
    exports com.vx.esyakayipsistemi.Controller.UserController;
    opens com.vx.esyakayipsistemi.Controller.UserController to javafx.fxml;
    exports com.vx.esyakayipsistemi.Classes;
    opens com.vx.esyakayipsistemi.Classes to javafx.fxml;
}