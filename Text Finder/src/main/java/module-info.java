module com.example.textfinder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.textfinder to javafx.fxml;
    exports com.example.textfinder;
}