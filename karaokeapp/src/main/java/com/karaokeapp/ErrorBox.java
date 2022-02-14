package com.karaokeapp;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The {@code ErrorBox} class displays exception errors in AlertType.ERROR.
 * It contains an expandable section showing the exception stacktrace and
 * where the error has occurred.
 */
public class ErrorBox extends KaraokeApp {

    public static void error(Exception ex) {
        // Instantiate Alert with error type
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Alert must be closed to perform other actions in the program
        alert.initModality(Modality.APPLICATION_MODAL);

        // Adding information to alert
        alert.setTitle("Error!");
        alert.setHeaderText("Error Found! Please check and try again");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        // Adding exception to textArea
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();

        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

}
