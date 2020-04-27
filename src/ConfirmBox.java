import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import java.util.Optional;

/**
 * Class used when user click on close button of application.
 *
 */
public class ConfirmBox extends KaraokeApp {

    static boolean answer;

    /**
     * Method to display message in box.
     * @param title
     * @param message
     * @param information
     * @return
     */
    public static boolean confirmation(String title, String message, String information) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Alert must be closed to perform other actions in the program
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(information);

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.OK) {
            answer = true;
            alert.close();
        } else if (option.get() == ButtonType.CANCEL) {
            answer = false;
            alert.close();
        }

        return answer;
    }

}