import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.util.Iterator;

/**
 * Class to display media player.
 * Extends borderPane to divide media.
 *
 */
public class Player extends BorderPane {

    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Pane pane;
    MediaBar bar;
    String file;
    static Label songLabel;

    /**
     * Default constructor
     *
     */
    public Player() {

        if (!Playlist.songs.isEmpty()) {
            file = "file:///home/Rohan/IdeaProjects/KaraokeApp/Video/" + Playlist.songs.peekFirst().getVideoName();
        } else {
            file = "file:///home/Rohan/IdeaProjects/KaraokeApp/Video/test.mp4";
        }

        // Setting up media
        media = new Media(file);
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        // Making the video play
        mediaPlayer.setAutoPlay(false);

        Image songLabelImg = new Image(getClass().getResourceAsStream("/Image/songLabelImg.png"));
        ImageView songLabelImage = new ImageView(songLabelImg);
        songLabelImage.setFitHeight(50);
        songLabelImage.setFitWidth(50);
        songLabel = new Label("Empty Playlist");
        songLabel.setPadding(new Insets(10,10,10,10));
        songLabel.setStyle("-fx-font-size:16");
        songLabel.setGraphic(songLabelImage);

        // Add media to pane
        pane = new Pane();
        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(pane.widthProperty());
        mediaView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().addAll(mediaView, songLabel);

        // Dividing the media display
        setCenter(pane);
        bar = new MediaBar(mediaPlayer);
        setBottom(bar);
        setStyle("-fx-background-color:#2a2727");
    }

}
