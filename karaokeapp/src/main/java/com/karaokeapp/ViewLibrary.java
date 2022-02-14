package com.karaokeapp;

import com.karaokeapp.DataStructure.SET;
import com.karaokeapp.DataStructure.ST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/**
 * The {@code ViewLibrary} class contains the stage methods for users
 * to view the songs library. The songs are loaded from {@code LibraryFileIndex}
 * and added to a tableView. This class also enables users to search songs, add
 * new
 * songs to the library and add songs to playlist. Users can double-click in
 * tableView
 * and add songs to playlist.
 */
public class ViewLibrary {

    Stage viewLibraryWindow, addSongsWindow;
    TableView<Song> librarySongTable;
    LibraryFileIndex libraryFileIndex = new LibraryFileIndex();
    TextField searchTextField;
    TextField titleInput, artistInput, timeInput, videoNameInput;
    String[] word;

    /**
     * The {@code viewLibrary} method is used to show the viewLibraryWindow stage.
     */
    public void viewLibrary() {
        // Instantiate new stage
        viewLibraryWindow = new Stage();
        viewLibraryWindow.setTitle("Library");
        viewLibraryWindow.initModality(Modality.APPLICATION_MODAL);

        // Calling the libraryTable method before adding tableView to scene.
        libraryTable();

        // Double-clicking on the tableView will display information of the selected
        // song.
        librarySongTable.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if (librarySongTable.getSelectionModel().getSelectedItem() != null) {
                    Song song = librarySongTable.getSelectionModel().getSelectedItem();

                    // Display information in an alert method.
                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Song");
                    message.setHeaderText(null);
                    message.setContentText(song.toString());

                    // Creating button add and cancel
                    ButtonType buttonTypeAdd = new ButtonType("Add");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    message.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

                    // Adding an imageView to the alert message.
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

                    // If user clicks on add button, the selected song is added to the playlist.
                    if (result.isPresent() && result.get() == buttonTypeAdd) {
                        // Song is added to the last of the playlist.
                        Playlist.songs.addLast(song);
                    }
                }
            }
        });

        // Search Text field
        searchTextField = new TextField();
        searchTextField.setPrefSize(350, 45);
        searchTextField.setStyle("-fx-font-size:20");
        searchTextField.setPromptText("Enter title of song...");

        // Search button for songs
        Image searchImg, addImg;
        Button searchBtn = new Button("");
        Button addBtn = new Button("");
        try {
            searchImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/search.png"));

            ImageView searchImage = new ImageView(searchImg);
            searchImage.setFitHeight(30);
            searchImage.setFitWidth(30);
            searchBtn.setStyle("-fx-font-size:20");
            searchBtn.setGraphic(searchImage);
            searchBtn.setOnAction(e -> search());

            // Add button to playlist
            addImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/add.png"));
            ImageView addImage = new ImageView(addImg);
            addImage.setFitHeight(30);
            addImage.setFitWidth(30);
            addBtn.setStyle("-fx-font-size:20");
            addBtn.setGraphic(addImage);
            addBtn.setOnAction(e -> addSongs());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Spacer between search button and add button
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Instantiate new HBox to contain the search field, search button and add
        // button.
        HBox hBox = new HBox();
        hBox.getChildren().addAll(searchTextField, searchBtn, spacer, addBtn);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(4);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        // Instantiate new borderPane
        BorderPane bp = new BorderPane();
        bp.setTop(hBox);
        bp.setCenter(librarySongTable);

        Scene scene = new Scene(bp, 820, 900);
        String stylesheet = this.getClass().getResource("Stylesheet/Stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        viewLibraryWindow.setScene(scene);
        viewLibraryWindow.show();
    }

    /**
     * The {@code libraryTable} creates a new TableView which contains columns
     * for the title, artist, time and video file name.
     * Values of the cell are obtained in the {@code getSongs}
     */
    private void libraryTable() {
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
        librarySongTable = new TableView<>();

        /*
         * The .bind() is used to make the height and width of the table to be exactly
         * the same as the Stage(window) size.
         */
        librarySongTable.prefHeightProperty().bind(viewLibraryWindow.heightProperty());
        librarySongTable.prefWidthProperty().bind(viewLibraryWindow.widthProperty());

        /*
         * The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
         * This helps to remove extra spaces or unused columns.
         */
        librarySongTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSongs();

        librarySongTable.getColumns().add(titleColumn);
        librarySongTable.getColumns().add(artistColumn);
        librarySongTable.getColumns().add(timeColumn);
        librarySongTable.getColumns().add(videoColumn);
    }

    /**
     * The {@code getSongs} retrieves the key from {@code LibraryFileIndex} class
     * and is added to an observableList of type Song.
     */
    private void getSongs() {
        ST<String, SET<Song>> st = libraryFileIndex.symbolTableSong();
        ObservableList<Song> songsList = FXCollections.observableArrayList();

        // Loop to get every keys from st and is added to songsList
        for (String s : st.keys()) {
            // word is split by \n
            word = st.get(s).toString().split("\n");

            Song song = new Song();

            // Title is stored in index 0
            // Artist is stored in index 1
            // Time is stored in index 2
            // VideoName is stored in index 3
            song.setTitle(word[0]);
            song.setArtist(word[1]);
            song.setTime(Double.parseDouble(word[2]));
            song.setVideoName(word[3]);

            songsList.add(song);
        }

        // Add items of tableView
        librarySongTable.setItems(songsList);
    }

    /**
     * The {@code search} method is used to search the song by getting
     * the text entered by the user. The text is then searched in the
     * {@code LibraryFileIndex} using the key values stored in the symbol
     * table.
     * If {@code search} exists, a new alert message will appear containing
     * the song's information. Otherwise, it will show 'Not Found'.
     */
    private void search() {
        // Setting query equal to text entered by user.
        String query = searchTextField.getText();

        Alert songInformation = new Alert(Alert.AlertType.INFORMATION);
        songInformation.initStyle(StageStyle.UTILITY);
        songInformation.setTitle("Information");
        songInformation.setHeaderText(null);

        ButtonType buttonTypeAdd = new ButtonType("Add");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        songInformation.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

        // Checks if song is contained in the LibraryFileIndex class.
        if (libraryFileIndex.symbolTableSong().contains(query)) {
            // Get song's information using DataStructure.SET.
            SET<Song> set = libraryFileIndex.symbolTableSong().get(query);
            songInformation.setContentText(set.toString());

            Image songImg;
            try {
                songImg = new Image(new FileInputStream("src/main/java/com/karaokeapp/Image/song.png"));

                ImageView songImage = new ImageView(songImg);
                songImage.setFitHeight(60);
                songImage.setFitWidth(60);
                songInformation.setGraphic(songImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Optional<ButtonType> result = songInformation.showAndWait();

            // Add song to the end of playlist.
            if (result.isPresent() && result.get() == buttonTypeAdd) {

                // Get song's information previously stored in set.
                for (Song s : set) {
                    Song song = new Song();

                    song.setTitle(s.getTitle());
                    song.setArtist(s.getArtist());
                    song.setTime(s.getTime());
                    song.setVideoName(s.getVideoName());

                    Playlist.songs.addLast(song);
                }
            }
        } else {
            songInformation.setContentText("Not Found!");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            songInformation.getButtonTypes().setAll(cancel);

            songInformation.showAndWait();
        }
    }

    /**
     * The {@code addSongs} displays a new stage where users can add new songs
     * to the library.
     */
    private void addSongs() {
        // Instantiate new stage
        addSongsWindow = new Stage();
        addSongsWindow.setTitle("Add Song(s)");
        addSongsWindow.initModality(Modality.APPLICATION_MODAL);

        // TextField for title input
        titleInput = new TextField();
        titleInput.setPromptText("Enter title...");
        titleInput.setPrefSize(50, 50);

        // TextField for artist input
        artistInput = new TextField();
        artistInput.setPromptText("Enter artist's name...");
        artistInput.setPrefSize(50, 50);

        // TextField for time input
        timeInput = new TextField();
        timeInput.setPromptText("Enter time of song...");
        timeInput.setPrefSize(50, 50);

        // TextField for video file name input
        videoNameInput = new TextField();
        videoNameInput.setPromptText("Enter video name...");
        videoNameInput.setPrefSize(50, 50);

        // Add button to execute addToLibrary method.
        Button addButton = new Button("Add");
        addButton.setPrefSize(100, 50);
        addButton.setOnAction(e -> addToLibrary());

        // Back button to close addSongsWindow.
        Button backButton = new Button("Back");
        backButton.setPrefSize(100, 50);
        backButton.setOnAction(e -> {
            addSongsWindow.close();
        });

        // HBox to display button horizontally.
        HBox hBox = new HBox();
        hBox.getChildren().addAll(addButton, backButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        // Whole Layout
        VBox layout = new VBox();
        layout.getChildren().addAll(titleInput, artistInput, timeInput, videoNameInput, hBox);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 500, 250);
        addSongsWindow.setScene(scene);
        addSongsWindow.show();
    }

    /**
     * The {@code addToLibrary} method is used to get the text entered by the user.
     * It is then stored in the Song object which is added to
     * {@code LibraryFileIndex}
     * class. The title is added as the key in the symbol table.
     * The Song object is added to a DataStructure.SET which is then written to the
     * library file.
     */
    private void addToLibrary() {
        // Validation to check if text entered is null.
        // Checks if time contains only numbers and a dot.
        // Checks if video file name ends with '.mp4'.
        if (titleInput.getText().equals("")
                || artistInput.getText().equals("")
                || titleInput.getText().equals("")
                || videoNameInput.getText().equals("")) {
            Exception exception = new Exception("Please enter a valid input!");
            ErrorBox.error(exception);
        } else if (!timeInput.getText().matches("^[0-9.]+$")) {
            Exception exception = new Exception("Invalid Time!");
            ErrorBox.error(exception);
        } else if (!videoNameInput.getText().matches("([^\\s]+(\\.(?i)(mp4))$)")) {
            Exception exception = new Exception("Invalid Video Name!");
            ErrorBox.error(exception);
        } else {

            // Instantiate song object to store song details.
            Song song = new Song();

            song.setTitle(titleInput.getText());
            song.setArtist(artistInput.getText());
            song.setTime(Double.parseDouble(timeInput.getText()));
            song.setVideoName(videoNameInput.getText());

            // song.getTitle is added as the key in symbol tree.
            ST<String, SET<Song>> st = libraryFileIndex.symbolTableSong();
            st.put(song.getTitle(), new SET<>());

            SET<Song> songSET = st.get(song.getTitle());
            songSET.add(song);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(KaraokeApp.FilePath, true));

                // Loop through the songSET to write newSong to file.
                for (Song newSong : songSET) {
                    writer.newLine();
                    writer.write(newSong.getTitle() + "\t" + newSong.getArtist() + "\t" + newSong.getTime() +
                            "\t" + newSong.getVideoName());
                }
                writer.close();
            } catch (IOException ex) {
                ErrorBox.error(ex);
            }

            // Clears and reloads tableView.
            librarySongTable.getItems().clear();
            getSongs();
        }
    }

}
