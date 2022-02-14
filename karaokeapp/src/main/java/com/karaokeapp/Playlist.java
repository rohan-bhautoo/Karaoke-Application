package com.karaokeapp;

import com.karaokeapp.DataStructure.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * The {@code Playlist} class contains the stage method to display the
 * songs playlist. The songs are added to playlist from the {@code ViewLibrary}
 * class and stored in the DataStructure.LinkedList
 * {@code DataStructure.LinkedList} songs. Users can remove
 * a song from the playlist by double-clicking on the selected songs.
 */
public class Playlist {

    Stage playlistWindow;
    static LinkedList<Song> songs = new LinkedList<>();
    TableView<Song> songTableView;

    /**
     * The {@code viewPlaylist} method is used to show the playlistWindow stage.
     */
    public void viewPlaylist() {
        // Instantiate new stage
        playlistWindow = new Stage();
        playlistWindow.setTitle("Playlist");
        playlistWindow.initModality(Modality.APPLICATION_MODAL);

        // Calling the playlistTable method before adding tableView to scene.
        playlistTable();

        // Double-clicking on the tableView will display information of the selected
        // song.
        songTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if (songTableView.getSelectionModel().getSelectedItem() != null) {
                    Song song = songTableView.getSelectionModel().getSelectedItem();

                    // Display information in an alert method.
                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Song");
                    message.setHeaderText(null);
                    message.setContentText(song.toString());

                    // Creating button remove and cancel
                    ButtonType buttonTypeRemove = new ButtonType("Remove");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    message.getButtonTypes().setAll(buttonTypeRemove, buttonTypeCancel);

                    Image songImg;
                    try {
                        songImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/song.png"));

                        ImageView songImage = new ImageView(songImg);
                        songImage.setFitHeight(70);
                        songImage.setFitWidth(70);
                        message.setGraphic(songImage);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    Optional<ButtonType> result = message.showAndWait();

                    // If user clicks on remove button, the selected song is removed from the
                    // playlist.
                    if (result.isPresent() && result.get() == buttonTypeRemove) {
                        // The index of the selected song is obtained using the songTableView.
                        songs.removeAt(songTableView.getSelectionModel().getSelectedIndex());

                        // Clears and reloads tableView.
                        songTableView.getItems().clear();
                        getSongs();
                    }
                }
            }
        });

        // Instantiate new borderPane
        BorderPane bp = new BorderPane();
        bp.setCenter(songTableView);

        Scene scene = new Scene(bp, 620, 700);
        String ViewLibraryStylesheet = this.getClass().getResource("Stylesheet/ViewLibraryStylesheet.css").toExternalForm();
        scene.getStylesheets().add(ViewLibraryStylesheet);
        playlistWindow.setScene(scene);
        playlistWindow.show();
    }

    /**
     * Method to create linkedList from {@code DataStructure.LinkedList} class.
     * 
     * @return DataStructure.LinkedList<Song> songs
     */
    public LinkedList<Song> playlist() {
        return songs;
    }

    /**
     * The {@code playlistTable} creates a new TableView which contains columns
     * for the title, artist, time and video file name.
     * Values of the cell are obtained in the {@code getSongs}
     */
    public void playlistTable() {
        // Title column
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Artist column
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        // Time column
        TableColumn<Song, Double> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Video file name column
        TableColumn<Song, String> videoColumn = new TableColumn<>("Video");
        videoColumn.setCellValueFactory(new PropertyValueFactory<>("videoName"));

        // Creating tableView
        songTableView = new TableView<>();

        /*
         * The .bind() is used to make the height and width of the table to be exactly
         * the same as the Stage(window) size.
         */
        songTableView.prefHeightProperty().bind(playlistWindow.heightProperty());
        songTableView.prefWidthProperty().bind(playlistWindow.widthProperty());

        /*
         * The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
         * This helps to remove extra spaces or unused columns.
         */
        songTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSongs();

        songTableView.getColumns().add(titleColumn);
        songTableView.getColumns().add(artistColumn);
        songTableView.getColumns().add(timeColumn);
        songTableView.getColumns().add(videoColumn);
    }

    /**
     * The {@code getSongs} retrieves the song added to the {@code playlist} method
     * and stores it in an ObservableList of type Song.
     */
    private void getSongs() {
        ObservableList<Song> songsList = FXCollections.observableArrayList();

        // Loop to get every song from playlist()
        for (Song s : playlist()) {
            Song song = new Song();

            song.setTitle(s.getTitle());
            song.setArtist(s.getArtist());
            song.setTime(s.getTime());
            song.setVideoName(s.getVideoName());

            songsList.add(song);
        }

        // Add items of tableView
        songTableView.setItems(songsList);
    }

}
