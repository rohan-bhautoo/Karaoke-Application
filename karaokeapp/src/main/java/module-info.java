module com.karaokeapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.karaokeapp to javafx.fxml;
    exports com.karaokeapp;
}
