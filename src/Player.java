import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.util.Iterator;

/**
 * The {@code Player} class creates the mediaPlayer which is then
 * added to a mediaView. This class also extends BorderPane to add
 * the mediaView and {@code MediaBar} together in the layout. A song
 * label is shown on the top left corner to the mediaView.
 */
public class Player extends BorderPane {

    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Pane pane;
    MediaBar bar;
    static Label songLabel;

    /**
     * Default constructor of {@code Player} class.
     */
    public Player() {
        // Song Label
        Image songLabelImg = new Image(getClass().getResourceAsStream("/Image/songLabelImg.png"));
        ImageView songLabelImage = new ImageView(songLabelImg);
        songLabelImage.setFitHeight(50);
        songLabelImage.setFitWidth(50);
        songLabel = new Label("Empty Playlist");
        songLabel.setPadding(new Insets(10,10,10,10));
        songLabel.setStyle("-fx-font-size:16");
        songLabel.setGraphic(songLabelImage);

        // Add media and label to pane
        pane = new Pane();
        createMediaView();
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

    /**
     * The {@code createMediaView} method instantiate new MediaView used
     * together with the {@code initMediaPlayer}
     */
    public void createMediaView() {
        mediaView = new MediaView();
        initMediaPlayer(mediaView);
    }

    /**
     * The {@code initMediaPlayer} uses iterator method in {@code LinkedList} class
     * to iterate through the Playlist and changes the mediaPlayer according to the
     * videoName of the song. If playlist is empty, mediaPlayer will be set to test.mp4
     * by default.
     *
     * @param mediaView the mediaView to be displayed.
     */
    private void initMediaPlayer(MediaView mediaView) {
        Iterator<Song> songIterator = Playlist.songs.iterator();
        if (songIterator.hasNext()) {
            File file=new File("../Video/" + songIterator.next().getVideoName());
            mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setOnEndOfMedia(() -> initMediaPlayer(mediaView));
        } else {
            File file=new File("../Video/test.mp4");
            mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
            mediaPlayer.setAutoPlay(false);
            mediaPlayer.stop();
        }
        mediaView.setMediaPlayer(mediaPlayer);
    }

}
