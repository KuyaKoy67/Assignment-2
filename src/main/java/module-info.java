module com.carl.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.carl.assignment2 to javafx.fxml;
    exports com.carl.assignment2;
}
