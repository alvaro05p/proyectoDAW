module com.hand2hand {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.hand2hand to javafx.fxml;
    exports com.hand2hand;
}
