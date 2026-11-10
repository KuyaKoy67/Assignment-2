module com.carl.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.carl.assignment2 to javafx.fxml;
    exports com.carl.assignment2;
}
