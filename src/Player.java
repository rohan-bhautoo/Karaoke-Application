import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;

/**
 * Class to display media player.
 * Extends borderPane to divide media.
 * @author Rohan
 */
public class Player extends BorderPane {

    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Pane pane;
    MediaBar bar;

    /**
     * Default constructor
     * @param file
     */
    public Player(String file) {
        // Setting up media
        media = new Media(file);
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        // Add media to pane
        pane = new Pane();
        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(pane.widthProperty());
        mediaView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(mediaView);

        // Dividing the media display
        setCenter(pane);
        bar = new MediaBar(mediaPlayer);
        setBottom(bar);
        setStyle("-fx-background-color:#2a2727");

        // Making the video play
        mediaPlayer.play();
    }

}
