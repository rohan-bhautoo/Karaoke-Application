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
 *
 * Class for bottom media bar containing media player functionality.
 * Class extends Vertical Box.
 * @author Rohan
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
     * Default constructor
     * @param play
     */
    public MediaBar(MediaPlayer play) {
        player = play;
        viewLibrary = new ViewLibrary();
        playlist = new Playlist();

        // Setting up bottom HBox for time
        timeHBox = new HBox();
        timeHBox.setPadding(new Insets(10,10,10,10));
        startTime = new Label("--:--");

        // Time slider
        timeSlider = new Slider();

        // Duration of video being played.
        endTime = new Label("--:--");

        timeHBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeHBox.setMargin(timeSlider,new Insets(8,10,10,10));
        timeHBox.getChildren().addAll(startTime, timeSlider, endTime);

        // Adding buttons to HBox
        buttonHBox = new HBox();
        buttonHBox.setPadding(new Insets(10,10,10,10));

        // Play button
        Image playImg = new Image(getClass().getResourceAsStream("/Image/pause.png"));
        ImageView playImage = new ImageView(playImg);
        playImage.setFitHeight(35);
        playImage.setFitWidth(35);
        playButton = new Button();
        playButton.setPrefWidth(60);
        playButton.setPrefHeight(50);
        playButton.setGraphic(playImage);
        buttonHBox.setMargin(playButton,new Insets(10,30,10,10));
        playButton.setOnAction(e -> play());

        // Slow rate button
        slowButton = new Button("x0.5");
        slowButton.setPrefWidth(65);
        slowButton.setPrefHeight(50);
        slowButton.setOnAction(e -> slow());
        buttonHBox.setMargin(slowButton,new Insets(10,5,10,20));

        // Stop button
        Image stopImg = new Image(getClass().getResourceAsStream("/Image/stop.png"));
        ImageView stopImage = new ImageView(stopImg);
        stopImage.setFitHeight(35);
        stopImage.setFitWidth(35);
        stopButton = new Button();
        stopButton.setPrefWidth(65);
        stopButton.setPrefHeight(50);
        stopButton.setGraphic(stopImage);
        stopButton.setOnAction(e -> {
            stop();
            Image pauseImg = new Image(getClass().getResourceAsStream("/Image/play.png"));
            ImageView pauseImage = new ImageView(pauseImg);
            pauseImage.setFitHeight(35);
            pauseImage.setFitWidth(35);
            playButton.setGraphic(pauseImage);
        });
        buttonHBox.setMargin(stopButton,new Insets(10,5,10,10));

        // Fast rate button
        fastButton = new Button("x2.0");
        fastButton.setPrefWidth(65);
        fastButton.setPrefHeight(50);
        fastButton.setOnAction(e -> fast());
        buttonHBox.setMargin(fastButton,new Insets(10,30,10,10));

        // Previous song button
        Image previousImg = new Image(getClass().getResourceAsStream("/Image/previous.png"));
        ImageView previousImage = new ImageView(previousImg);
        previousImage.setFitHeight(35);
        previousImage.setFitWidth(35);
        previousButton = new Button();
        previousButton.setPrefWidth(50);
        previousButton.setPrefHeight(50);
        previousButton.setGraphic(previousImage);
        buttonHBox.setMargin(previousButton,new Insets(10,5,10,10));

        // Next song button
        Image nextImg = new Image(getClass().getResourceAsStream("/Image/next.png"));
        ImageView nextImage = new ImageView(nextImg);
        nextImage.setFitHeight(35);
        nextImage.setFitWidth(35);
        nextButton = new Button();
        nextButton.setPrefWidth(50);
        nextButton.setPrefHeight(50);
        nextButton.setGraphic(nextImage);
        buttonHBox.setMargin(nextButton,new Insets(10,5,10,10));

        // Library Button
        Image libraryImg = new Image(getClass().getResourceAsStream("/Image/musicLibrary.png"));
        ImageView libraryImage = new ImageView(libraryImg);
        libraryImage.setFitHeight(35);
        libraryImage.setFitWidth(35);
        Button viewLibraryBtn = new Button("Library");
        viewLibraryBtn.setStyle("-fx-font-size:20");
        viewLibraryBtn.setPrefWidth(150);
        viewLibraryBtn.setGraphic(libraryImage);
        buttonHBox.setMargin(viewLibraryBtn,new Insets(10,5,10,10));
        viewLibraryBtn.setOnAction(e -> {
            player.pause();
            viewLibrary.viewLibrary();

            viewLibrary.viewLibraryWindow.setOnCloseRequest(ev -> {
                ev.consume();
                closeViewLibrary();
            });
        });

        // Playlist button
        Image playlistImg = new Image(getClass().getResourceAsStream("/Image/musicPlaylist.png"));
        ImageView playlistImage = new ImageView(playlistImg);
        playlistImage.setFitHeight(35);
        playlistImage.setFitWidth(35);
        Button playlistBtn = new Button("Playlist");
        playlistBtn.setStyle("-fx-font-size:20");
        playlistBtn.setPrefWidth(150);
        playlistBtn.setGraphic(playlistImage);
        buttonHBox.setMargin(playlistBtn,new Insets(10,5,10,10));
        playlistBtn.setOnAction(e -> {
            player.pause();
            playlist.viewPlaylist();

            playlist.playlistWindow.setOnCloseRequest(ev -> {
                ev.consume();
                closePlaylist();
            });
        });

        // GridPane for volume controls
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);

        // Setting different width values for each column.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setPrefWidth(600);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPrefWidth(250);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.SOMETIMES);
        col3.setPrefWidth(75);
        pane.getColumnConstraints().addAll(col1, col2, col3);

        // Volume label
        volumeLabel = new Label("Volume: ");
        volumeLabel.setTranslateX(500);

        // Slider for volume
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(150);
        volumeSlider.setValue(100);

        // Value of current volume
        volumePercentage = new Label("0%");
        volumePercentage.setAlignment(Pos.CENTER);

        // Mute button
        Image muteImg = new Image(getClass().getResourceAsStream("/Image/mute.png"));
        ImageView muteImage = new ImageView(muteImg);
        muteImage.setFitHeight(35);
        muteImage.setFitWidth(35);
        muteButton = new Button();
        muteButton.setPrefWidth(60);
        muteButton.setPrefHeight(50);
        muteButton.setGraphic(muteImage);
        muteButton.setOnAction(e -> mute());
        buttonHBox.setMargin(muteButton,new Insets(10,5,10,10));

        // Adding buttons to grid pane.
        buttonHBox.setHgrow(pane, Priority.ALWAYS);
        pane.add(volumeLabel, 0, 1);
        pane.add(volumeSlider, 1,1);
        pane.add(volumePercentage,2,1);
        pane.add(muteButton,3 ,1);

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

        player.currentTimeProperty().addListener(e -> updateValues());

        // Add functionality to time slider
        timeSlider.valueProperty().addListener(e -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                player.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
        });

        player.currentTimeProperty().addListener(e -> updateValues());

        // Add functionality to volume slider
        volumeSlider.valueProperty().addListener(e -> {
            if (volumeSlider.isPressed()) {
                player.setMute(false);
                muteButton.setGraphic(muteImage);
                player.setVolume(volumeSlider.getValue() / 100.0);
            }
        });
    }

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
            }
            else {
                // Pausing the player
                player.pause();

                Image pauseImg = new Image(getClass().getResourceAsStream("/Image/play.png"));
                ImageView pauseImage = new ImageView(pauseImg);
                pauseImage.setFitHeight(35);
                pauseImage.setFitWidth(35);
                playButton.setGraphic(pauseImage);
            }
        }

        // If the video is stopped, halted or paused
        if (status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED) {
            player.play();
            player.setRate(1.0);

            Image pauseImg = new Image(getClass().getResourceAsStream("/Image/pause.png"));
            ImageView pauseImage = new ImageView(pauseImg);
            pauseImage.setFitHeight(35);
            pauseImage.setFitWidth(35);
            playButton.setGraphic(pauseImage);
        }
    }

    // Stop media player
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

    // Set play rate to 2.0
    public void fast() {
        player.play();
        Image pauseImg = new Image(getClass().getResourceAsStream("/Image/pause.png"));
        ImageView pauseImage = new ImageView(pauseImg);
        pauseImage.setFitHeight(35);
        pauseImage.setFitWidth(35);
        playButton.setGraphic(pauseImage);
        player.setRate(2.0);
    }

    // Set play rate to 0.5
    public void slow() {
        player.play();
        Image pauseImg = new Image(getClass().getResourceAsStream("/Image/pause.png"));
        ImageView pauseImage = new ImageView(pauseImg);
        pauseImage.setFitHeight(35);
        pauseImage.setFitWidth(35);
        playButton.setGraphic(pauseImage);
        player.setRate(0.5);
    }

    // Set player to mute
    public void mute() {
        player.setMute(true);

        if (player.isMute()) {
            Image soundImg = new Image(getClass().getResourceAsStream("/Image/sound.png"));
            ImageView soundImage = new ImageView(soundImg);
            soundImage.setFitHeight(35);
            soundImage.setFitWidth(35);
            muteButton.setGraphic(soundImage);
        }
    }

    /**
     * Method to update slider's values.
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

                if (player.isMute()) {
                    volumeSlider.setValue(0);
                    volumePercentage.setText((int) Math.round(volumeSlider.getValue() * 100) + "%");
                } else if (volumeSlider.getValue() == 0) {
                    Image soundImg = new Image(getClass().getResourceAsStream("/Image/sound.png"));
                    ImageView soundImage = new ImageView(soundImg);
                    soundImage.setFitHeight(35);
                    soundImage.setFitWidth(35);
                    muteButton.setGraphic(soundImage);

                    volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
                } else {
                    volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
                }
            });
        }
    }

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

    public void closeViewLibrary() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if(answer) {
            viewLibrary.viewLibraryWindow.close();

            player.play();
        }
    }

    public void closePlaylist() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if(answer) {
            playlist.playlistWindow.close();

            player.play();
        }
    }
}