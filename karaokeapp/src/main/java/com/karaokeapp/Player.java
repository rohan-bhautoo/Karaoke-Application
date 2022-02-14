package com.karaokeapp;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The {@code Player} class creates the mediaPlayer which is then
 * added to a mediaView. This class also extends BorderPane to add
 * the mediaView and {@code MediaBar} together in the layout. A song
 * label is shown on the top left corner to the mediaView.
 */
public class Player extends BorderPane {

    MediaPlayer mediaPlayer;
    final MediaView mediaView = new MediaView();
    Pane pane;
    MediaBar bar;
    static Label songLabel;

    /**
     * Default constructor of {@code Player} class.
     */
    public Player() {
        // Song Label
        Image songLabelImg;
        try {
            songLabelImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/songLabelImg.png"));

            ImageView songLabelImage = new ImageView(songLabelImg);
            songLabelImage.setFitHeight(50);
            songLabelImage.setFitWidth(50);
            songLabel = new Label("Empty Playlist");
            songLabel.setPadding(new Insets(10, 10, 10, 10));
            songLabel.setStyle("-fx-font-size:16");
            songLabel.setGraphic(songLabelImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Add media and label to pane
        pane = new Pane();

        playMedia("C:/Users/rohan/Downloads/karaokeapp/src/main/java/com/karaokeapp/Video/Default.mp4");

        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(pane.widthProperty());
        mediaView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().addAll(mediaView, songLabel);

        // Dividing the media display
        setCenter(pane);
        setStyle("-fx-background-color:#2a2727");
    }

    /**
     * The {@code playMedia} method creates the media player
     * and changes the video in the GUI by getting the video
     * file name of the first song in {@code Playlist}.
     * If {@code Playlist} is empty, the song label will be
     * set to empty playlist and media player will stop.
     * 
     * @param mediaFile the path of the video file
     */
    public void playMedia(String mediaFile) {
        try {
            mediaPlayer = new MediaPlayer(new Media(new File(mediaFile).toURI().toString()));
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();

                if (!Playlist.songs.isEmpty()) {
                    playMedia("Video/" + Playlist.songs.peekFirst().getVideoName());
                    Player.songLabel.setText("Title: " + Playlist.songs.peekFirst().getTitle() + "\nArtist: "
                            + Playlist.songs.peekFirst().getArtist());
                    Playlist.songs.removeFirst();
                } else {
                    Player.songLabel.setText("Empty Playlist");
                    playMedia("C:/Users/rohan/Downloads/karaokeapp/src/main/java/com/karaokeapp/Video/Default.mp4");
                    mediaPlayer.stop();
                }
            });
            bar = new MediaBar(mediaPlayer);
            setBottom(bar);
        } catch (Exception e) {
            ErrorBox.error(e);
        }
    }

}
