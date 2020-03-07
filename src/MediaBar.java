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
    Duration duration;

    /**
     * Default constructor
     * @param play
     */
    public MediaBar(MediaPlayer play) {
        player = play;

        // Setting up bottom hbox for time
        timeHBox = new HBox();
        timeHBox.setPadding(new Insets(10,10,10,10));
        startTime = new Label("--:--");

        // Time slider
        timeSlider = new Slider();

        // Duration of video being played.
        endTime = new Label("--:--");

        timeHBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeHBox.setMargin(timeSlider,new Insets(10,10,10,10));
        timeHBox.getChildren().addAll(startTime, timeSlider, endTime);

        // Adding buttons to hbox
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

        // GridPane for volume controls
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);

        // Setting different width values for each column.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setPrefWidth(900);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPrefWidth(250);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.SOMETIMES);
        col3.setPrefWidth(75);
        pane.getColumnConstraints().addAll(col1, col2, col3);

        // Volume label
        volumeLabel = new Label("Volume: ");
        volumeLabel.setTranslateX(800);

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

        buttonHBox.getChildren().addAll(playButton, previousButton, stopButton, nextButton, slowButton, fastButton, pane);

        // Align in center
        setAlignment(Pos.CENTER);
        getChildren().addAll(timeHBox, buttonHBox);

        // Providing functionality to time slider
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

        // Adding functionality to buttons
        playButton.setOnAction(e -> play());
    }

    public void play() {
        // Get status of player
        MediaPlayer.Status status = player.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }

        if (status == status.PLAYING) {
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
        if (status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED) {
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
        player.setVolume(volumeSlider.getValue() / 100.0);
        volumePercentage.setText((int) Math.round(player.getVolume() * 100) + "%");
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

        if (player.isMute() == true) {
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
        Platform.runLater(() -> {
            // Updating to the new time value
            // This will move the slider while running your video
            timeSlider.setValue(player.getCurrentTime().toMillis() * 100);

            if (player.isMute() == true) {
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