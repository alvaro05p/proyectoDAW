module com.hand2hand {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.hand2hand to javafx.fxml;
    exports com.hand2hand;
}
