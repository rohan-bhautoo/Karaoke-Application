package com.karaokeapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The {@code MediaBar} class contains all the mediaPlayer main functionalities
 * such as play, pause, skip, viewLibrary, viewPlaylist. It extend the VBox
 * method
 * to add the buttons and sliders.
 */
public class MediaBar extends VBox {

    GridPane pane;
    HBox timeHBox, buttonHBox;
    Label startTime, endTime, volumeLabel, volumePercentage;
    Slider timeSlider, volumeSlider;
    Button playButton, slowButton, fastButton, stopButton, previousButton, nextButton, muteButton;
    MediaPlayer player;
    ViewLibrary viewLibrary;
    Playlist playlist;
    Duration duration;

    /**
     * Default constructor for the {@code MediaBar} class.
     * 
     * @param play the mediaPlayer where the functionalities in
     *             {@code MediaBar} should be applied.
     */
    public MediaBar(MediaPlayer play) {
        player = play;

        // Instantiate the classes
        viewLibrary = new ViewLibrary();
        playlist = new Playlist();

        // Setting up bottom HBox for time
        timeHBox = new HBox();
        timeHBox.setPadding(new Insets(10, 10, 10, 10));
        startTime = new Label("--:--");

        // Instantiate Time slider
        timeSlider = new Slider();

        // Duration of video being played.
        endTime = new Label("--:--");

        // Adding sliders to timeHBox
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        HBox.setMargin(timeSlider, new Insets(8, 10, 10, 10));
        timeHBox.getChildren().addAll(startTime, timeSlider, endTime);

        buttonHBox = new HBox();
        buttonHBox.setPadding(new Insets(10, 10, 10, 10));

        // Play button
        // It has the functionality to play the media player
        Image playImg;
        try {
            playImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/pause.png"));

            ImageView playImage = new ImageView(playImg);
            playImage.setFitHeight(35);
            playImage.setFitWidth(35);
            playButton = new Button();
            playButton.setPrefWidth(60);
            playButton.setPrefHeight(50);
            playButton.setGraphic(playImage);
            HBox.setMargin(playButton, new Insets(10, 30, 10, 10));
            playButton.setOnAction(e -> play());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Slow rate button
        // Sets the video play rate to 0.5
        slowButton = new Button("x0.5");
        slowButton.setPrefWidth(65);
        slowButton.setPrefHeight(50);
        slowButton.setOnAction(e -> slow());
        HBox.setMargin(slowButton, new Insets(10, 5, 10, 20));

        // Stop button
        // It stops the video from playing
        Image stopImg;
        try {
            stopImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/stop.png"));

            ImageView stopImage = new ImageView(stopImg);
            stopImage.setFitHeight(35);
            stopImage.setFitWidth(35);
            stopButton = new Button();
            stopButton.setPrefWidth(65);
            stopButton.setPrefHeight(50);
            stopButton.setGraphic(stopImage);
            stopButton.setOnAction(e -> {
                stop();

                // Changes the image back to play.png when stop is clicked
                Image pauseImg;
                try {
                    pauseImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/play.png"));

                    ImageView pauseImage = new ImageView(pauseImg);
                    pauseImage.setFitHeight(35);
                    pauseImage.setFitWidth(35);
                    playButton.setGraphic(pauseImage);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            });
            HBox.setMargin(stopButton, new Insets(10, 5, 10, 10));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Fast rate button
        // Sets the video play rate to 2.0
        fastButton = new Button("x2.0");
        fastButton.setPrefWidth(65);
        fastButton.setPrefHeight(50);
        fastButton.setOnAction(e -> fast());
        HBox.setMargin(fastButton, new Insets(10, 30, 10, 10));

        // Previous song button
        Image previousImg;
        try {
            previousImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/previous.png"));

            ImageView previousImage = new ImageView(previousImg);
            previousImage.setFitHeight(35);
            previousImage.setFitWidth(35);
            previousButton = new Button();
            previousButton.setPrefWidth(50);
            previousButton.setPrefHeight(50);
            previousButton.setGraphic(previousImage);
            HBox.setMargin(previousButton, new Insets(10, 5, 10, 10));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Next song button
        // It has the function to skip songs in the playlist
        Image nextImg;
        try {
            nextImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/next.png"));

            ImageView nextImage = new ImageView(nextImg);
            nextImage.setFitHeight(35);
            nextImage.setFitWidth(35);
            nextButton = new Button();
            nextButton.setPrefWidth(50);
            nextButton.setPrefHeight(50);
            nextButton.setGraphic(nextImage);
            HBox.setMargin(nextButton, new Insets(10, 5, 10, 10));
            nextButton.setOnAction(e -> {
                // Checks if playlist is not empty
                // Removes the first song when nextButton is clicked
                if (!Playlist.songs.isEmpty()) {
                    Playlist.songs.removeFirst();
                    player.seek(Duration.millis(0));

                    setSongLabel();
                } else {
                    Player.songLabel.setText("Empty Playlist");
                    player.stop();
                }
            });
        } catch (FileNotFoundException e1) {

            e1.printStackTrace();
        }

        // Library Button
        // Button to open ViewLibrary class
        Image libraryImg;
        Button viewLibraryBtn = new Button("Library");
        try {
            libraryImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/musicLibrary.png"));

            ImageView libraryImage = new ImageView(libraryImg);
            libraryImage.setFitHeight(35);
            libraryImage.setFitWidth(35);

            viewLibraryBtn.setStyle("-fx-font-size:20");
            viewLibraryBtn.setPrefWidth(150);
            viewLibraryBtn.setGraphic(libraryImage);
            HBox.setMargin(viewLibraryBtn, new Insets(10, 5, 10, 10));
            viewLibraryBtn.setOnAction(e -> {
                // Pause media player when playlist is opened
                player.pause();
                viewLibrary.viewLibrary();

                viewLibrary.viewLibraryWindow.setOnCloseRequest(ev -> {
                    ev.consume();
                    closeViewLibrary();
                });
            });
        } catch (FileNotFoundException e1) {

            e1.printStackTrace();
        }

        // Playlist button
        // Button to open Playlist class
        Image playlistImg;
        Button playlistBtn = new Button("Playlist");
        try {
            playlistImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/musicPlaylist.png"));

            ImageView playlistImage = new ImageView(playlistImg);
            playlistImage.setFitHeight(35);
            playlistImage.setFitWidth(35);

            playlistBtn.setStyle("-fx-font-size:20");
            playlistBtn.setPrefWidth(150);
            playlistBtn.setGraphic(playlistImage);
            HBox.setMargin(playlistBtn, new Insets(10, 5, 10, 10));
            playlistBtn.setOnAction(e -> {
                // Pause media player when playlist is opened
                player.pause();
                playlist.viewPlaylist();

                playlist.playlistWindow.setOnCloseRequest(ev -> {
                    ev.consume();
                    closePlaylist();
                });
            });
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Instantiate a gripPane method
        // It is used for the volume controls
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setHgap(10);

        // Setting different width values for each column.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setPrefWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.SOMETIMES);
        col2.setPrefWidth(250);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.SOMETIMES);
        col3.setPrefWidth(100);
        pane.getColumnConstraints().addAll(col1, col2, col3);

        // Volume label
        volumeLabel = new Label("Volume: ");
        //volumeLabel.setTranslateX(470);
        volumeLabel.setPadding(new Insets(0, 0, 0, 20));

        // Slider for volume
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(150);
        volumeSlider.setValue(100);

        // Value of current volume
        volumePercentage = new Label("0%");
        volumePercentage.setAlignment(Pos.CENTER);

        // Mute button
        Image muteImg;
        try {
            muteImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/mute.png"));

            ImageView muteImage = new ImageView(muteImg);
            muteImage.setFitHeight(35);
            muteImage.setFitWidth(35);
            muteButton = new Button();
            muteButton.setPrefWidth(60);
            muteButton.setPrefHeight(50);
            muteButton.setGraphic(muteImage);
            muteButton.setOnAction(e -> mute());
            HBox.setMargin(muteButton, new Insets(10, 5, 10, 10));

            // Adding buttons to grid pane.
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.add(volumeLabel, 0, 1);
            pane.add(volumeSlider, 1, 1);
            pane.add(volumePercentage, 2, 1);
            pane.add(muteButton, 3, 1);

            buttonHBox.getChildren().addAll(playButton, previousButton, stopButton, nextButton, slowButton, fastButton,
                    viewLibraryBtn, playlistBtn, pane);

            // Align in center
            setAlignment(Pos.CENTER);
            getChildren().addAll(timeHBox, buttonHBox);

            // Providing functionality to time slider
            player.setOnReady(() -> {
                duration = player.getMedia().getDuration();
                updateValues();
            });

            // Update time when video is playing
            player.currentTimeProperty().addListener(e -> updateValues());

            // Add functionality to time slider
            timeSlider.valueProperty().addListener(e -> {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    player.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            });

            // Add functionality to volume slider
            volumeSlider.valueProperty().addListener(e -> {
                if (volumeSlider.isPressed()) {
                    player.setMute(false);
                    muteButton.setGraphic(muteImage);
                    player.setVolume(volumeSlider.getValue() / 100.0);
                }
            });
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * The {@code play} provides the functionality for the mediaPlayer
     * to start playing. MediaPlayer.status is used to check if mediaPlayer
     * is already playing, on paused or stopped.
     * It changes the image of the play button when the video is paused or stopped.
     */
    public void play() {
        // Get status of player
        MediaPlayer.Status status = player.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }

        if (status == MediaPlayer.Status.PLAYING) {
            // If video is playing
            if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {
                // If the player is at the end of video
                // Restart the video
                player.seek(player.getStartTime());
                player.play();
                player.setRate(1.0);
            } else {
                // Pausing the player
                player.pause();

                Image pauseImg;
                try {
                    pauseImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/play.png"));

                    ImageView pauseImage = new ImageView(pauseImg);
                    pauseImage.setFitHeight(35);
                    pauseImage.setFitWidth(35);
                    playButton.setGraphic(pauseImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // If the video is stopped, halted or paused
        if (status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED) {
            player.play();
            player.setRate(1.0);

            Image pauseImg;
            try {
                pauseImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/pause.png"));

                ImageView pauseImage = new ImageView(pauseImg);
                pauseImage.setFitHeight(35);
                pauseImage.setFitWidth(35);
                playButton.setGraphic(pauseImage);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * The {@code stop} stops the mediaPlayer from playing and set time back to
     * 0.00.
     */
    public void stop() {
        player.stop();

        // Get status of player
        MediaPlayer.Status status = player.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }

        if (status == MediaPlayer.Status.STOPPED) {
            player.play();
            player.setVolume(volumeSlider.getValue() / 100.0);
            volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
            updateValues();
        }
    }

    /**
     * Sets the video rate to 2.0.
     */
    public void fast() {
        player.play();
        Image pauseImg;
        try {
            pauseImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/pause.png"));

            ImageView pauseImage = new ImageView(pauseImg);
            pauseImage.setFitHeight(35);
            pauseImage.setFitWidth(35);
            playButton.setGraphic(pauseImage);
            player.setRate(2.0);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * Sets the video rate to 0.5.
     */
    public void slow() {
        player.play();
        Image pauseImg;
        try {
            pauseImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/pause.png"));

            ImageView pauseImage = new ImageView(pauseImg);
            pauseImage.setFitHeight(35);
            pauseImage.setFitWidth(35);
            playButton.setGraphic(pauseImage);
            player.setRate(0.5);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * Sets the mediaPlayer to mute and changes the imageView.
     */
    public void mute() {
        player.setMute(true);
        Image soundImg;
        try {
            if (player.isMute()) {
                soundImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/sound.png"));
                ImageView soundImage = new ImageView(soundImg);
                soundImage.setFitHeight(35);
                soundImage.setFitWidth(35);
                muteButton.setGraphic(soundImage);
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * The {@code updateValues} method updates the time slider when the video
     * is playing. The text of the endTime is also changed.
     * If video is mute, the volume slider goes to 0 and mute icon is changed.
     */
    protected void updateValues() {
        if (endTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                // Updating to the new time value
                // This will move the slider while running your video
                Duration currentTime = player.getCurrentTime();
                startTime.setText(formatElapsedTime(currentTime));
                endTime.setText(formatDurationTime(duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis()
                            * 100.0);
                }

                // Checks if mediaPlayer is set to mute and changes text.
                if (player.isMute()) {
                    volumeSlider.setValue(0);
                    volumePercentage.setText((int) Math.round(volumeSlider.getValue() * 100) + "%");
                } else if (volumeSlider.getValue() == 0) {
                    Image soundImg;
                    try {
                        soundImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/sound.png"));

                        ImageView soundImage = new ImageView(soundImg);
                        soundImage.setFitHeight(35);
                        soundImage.setFitWidth(35);
                        muteButton.setGraphic(soundImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
                } else {
                    volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
                }
            });
        }
    }

    /**
     * The {@code formatElapsedTime} method is used to calculate the time when
     * the video is being played. The value gets changed every time until it
     * reaches end of media.
     * 
     * @param elapsed Time duration of video being played.
     * @return the string of time which can be in format mm:ss or hh:mm:ss.
     */
    private static String formatElapsedTime(Duration elapsed) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (elapsedHours > 0) {
            return String.format("%d:%02d:%02d", elapsedHours,
                    elapsedMinutes, elapsedSeconds);
        } else {
            return String.format("%02d:%02d", elapsedMinutes,
                    elapsedSeconds);
        }
    }

    /**
     * The {@code formatDurationTime} method is used to calculate the total time
     * of the video being played.
     * 
     * @param duration Time duration of video being played.
     * @return the string of time which can be in format mm:ss or hh:mm:ss.
     */
    private static String formatDurationTime(Duration duration) {
        int intDuration = (int) Math.floor(duration.toSeconds());
        int durationHours = intDuration / (60 * 60);
        if (durationHours > 0) {
            intDuration -= durationHours * 60 * 60;
        }
        int durationMinutes = intDuration / 60;
        int durationSeconds = intDuration - durationHours * 60 * 60
                - durationMinutes * 60;
        if (durationHours > 0) {
            return String.format("%d:%02d:%02d", durationHours, durationMinutes, durationSeconds);
        } else {
            return String.format("%02d:%02d", durationMinutes, durationSeconds);
        }
    }

    /**
     * Execute function from {@code ConfirmBox} class to display
     * confirmation message.
     * It changes the songLabel when the window is closed.
     */
    public void closeViewLibrary() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if (answer) {
            viewLibrary.viewLibraryWindow.close();

            setSongLabel();
        }
    }

    /**
     * Execute function from {@code ConfirmBox} class to display
     * confirmation message.
     * It changes the songLabel when the window is closed.
     */
    public void closePlaylist() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if (answer) {
            playlist.playlistWindow.close();

            setSongLabel();
        }
    }

    /**
     * The {@code setSongLabel} iterates through the DataStructure.LinkedList songs
     * at changes
     * the song label in {@code Player} class.
     */
    public void setSongLabel() {
        // Checks if Playlist is not empty.
        if (!Playlist.songs.isEmpty()) {
            Player.songLabel.setText("Title: " + Playlist.songs.peekFirst().getTitle() + "\nArtist: "
                    + Playlist.songs.peekFirst().getArtist());
            player.play();
        } else {
            Player.songLabel.setText("Empty Playlist");
            player.stop();
        }
    }
}