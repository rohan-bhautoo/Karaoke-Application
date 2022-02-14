package com.karaokeapp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import java.util.Optional;

/**
 * The {@code ConfirmBox} class provides to the user a confirmation
 * message before doing certain actions.
 */
public class ConfirmBox extends KaraokeApp {

    static boolean answer;

    /**
     * The {@code confirmation} method is used to display message in box.
     * @param title The title
     * @param message The confirmation message
     * @param information Information that need to be displayed
     * @return {@code false} if user click on cancel button and {@code true} if
     * ok button is clicked
     */
    public static boolean confirmation(String title, String message, String information) {
        // Instantiate Alert with confirmation type
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Alert must be closed to perform other actions in the program
        alert.initModality(Modality.APPLICATION_MODAL);

        // Adding information to alert
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(information);

        Optional<ButtonType> option = alert.showAndWait();

        // Perform certain action when user clicks on button
        if(option.isPresent() && option.get() == ButtonType.OK) {
            answer = true;
            alert.close();
        } else if (option.isPresent() && option.get() == ButtonType.CANCEL) {
            answer = false;
            alert.close();
        }

        return answer;
    }

}