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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class ViewLibrary {

    Stage viewLibraryWindow, addSongsWindow;
    TableView<Song> librarySongTable;
    LibraryFileIndex libraryFileIndex = new LibraryFileIndex();
    TextField searchTextField;
    TextField titleInput, artistInput, timeInput, videoNameInput;
    String[] word;

    public void viewLibrary() {
        viewLibraryWindow = new Stage();
        viewLibraryWindow.setTitle("Library");
        viewLibraryWindow.initModality(Modality.APPLICATION_MODAL);

        libraryTable();

        librarySongTable.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if(librarySongTable.getSelectionModel().getSelectedItem() != null) {
                    Song song =  librarySongTable.getSelectionModel().getSelectedItem();

                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Song");
                    message.setHeaderText(null);
                    message.setContentText(song.toString());

                    ButtonType buttonTypeAdd = new ButtonType("Add");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    message.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

                    Image songImg = new Image(getClass().getResourceAsStream("/Image/song.png"));
                    ImageView songImage = new ImageView(songImg);
                    songImage.setFitHeight(70);
                    songImage.setFitWidth(70);
                    message.setGraphic(songImage);
                    Optional<ButtonType> result = message.showAndWait();

                    if (result.isPresent() && result.get() == buttonTypeAdd) {
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

        // Buttons
        Image searchImg = new Image(getClass().getResourceAsStream("/Image/search.png"));
        ImageView searchImage = new ImageView(searchImg);
        searchImage.setFitHeight(30);
        searchImage.setFitWidth(30);
        Button searchBtn = new Button("");
        searchBtn.setStyle("-fx-font-size:20");
        searchBtn.setGraphic(searchImage);
        searchBtn.setOnAction(e -> search());

        Image addImg = new Image(getClass().getResourceAsStream("/Image/add.png"));
        ImageView addImage = new ImageView(addImg);
        addImage.setFitHeight(30);
        addImage.setFitWidth(30);
        Button addBtn = new Button("");
        addBtn.setStyle("-fx-font-size:20");
        addBtn.setGraphic(addImage);
        addBtn.setOnAction(e -> addSongs());

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(searchTextField, searchBtn, spacer, addBtn);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(4);
        hBox.setPadding(new Insets(10,10,10,10));

        BorderPane bp = new BorderPane();
        bp.setTop(hBox);
        bp.setCenter(librarySongTable);

        Scene scene = new Scene(bp, 820, 900);
        scene.getStylesheets().add("/Stylesheet/ViewLibraryStylesheet.css");
        viewLibraryWindow.setScene(scene);
        viewLibraryWindow.show();
    }

    private void libraryTable() {
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Song, Double> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Song, String> videoColumn = new TableColumn<>("Video");
        videoColumn.setCellValueFactory(new PropertyValueFactory<>("videoName"));

        // Creating tableView
        librarySongTable = new TableView<>();

        /*
        The .bind() is used to make the height and width of the table to be exactly
        the same as the Stage(window) size.
         */
        librarySongTable.prefHeightProperty().bind(viewLibraryWindow.heightProperty());
        librarySongTable.prefWidthProperty().bind(viewLibraryWindow.widthProperty());

        /*
        The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
        This helps to remove extra spaces or unused columns.
         */
        librarySongTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSongs();

        librarySongTable.getColumns().add(titleColumn);
        librarySongTable.getColumns().add(artistColumn);
        librarySongTable.getColumns().add(timeColumn);
        librarySongTable.getColumns().add(videoColumn);
    }

    private void getSongs() {
        ST<String, SET<Song>> st = libraryFileIndex.symbolTableSong();
        ObservableList<Song> songsList = FXCollections.observableArrayList();

        for (String s : st.keys()) {
            word = st.get(s).toString().split("\n");

            Song song = new Song();

            song.setTitle(word[0]);
            song.setArtist(word[1]);
            song.setTime(Double.parseDouble(word[2]));
            song.setVideoName(word[3]);

            songsList.add(song);
        }

        librarySongTable.setItems(songsList);
    }

    private void search() {
        final int DELAY = 500;

        Stopwatch timer = new Stopwatch();

        timer.start();

        try {
            String query = searchTextField.getText();

            SET<Song> set;

            Alert songInformation = new Alert(Alert.AlertType.INFORMATION);
            songInformation.initStyle(StageStyle.UTILITY);
            songInformation.setTitle("Information");
            songInformation.setHeaderText(null);

            ButtonType buttonTypeAdd = new ButtonType("Add");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            songInformation.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

            if (libraryFileIndex.symbolTableSong().contains(query)) {
                set = libraryFileIndex.symbolTableSong().get(query);
                songInformation.setContentText(set.toString());
                System.out.println("Took " + timer.stop() + " seconds");

                Image songImg = new Image(getClass().getResourceAsStream("/Image/song.png"));
                ImageView songImage = new ImageView(songImg);
                songImage.setFitHeight(60);
                songImage.setFitWidth(60);
                songInformation.setGraphic(songImage);

                Optional<ButtonType> result = songInformation.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeAdd) {

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

            Thread.sleep(DELAY);
        } catch(InterruptedException ex) {
            System.out.println("Sleep interrupted");
        }
    }

    private void addSongs() {
        addSongsWindow = new Stage();
        addSongsWindow.setTitle("Add Song(s)");
        addSongsWindow.initModality(Modality.APPLICATION_MODAL);

        titleInput = new TextField();
        titleInput.setPromptText("Enter title...");
        titleInput.setPrefSize(50, 50);

        artistInput = new TextField();
        artistInput.setPromptText("Enter artist's name...");
        artistInput.setPrefSize(50, 50);

        timeInput = new TextField();
        timeInput.setPromptText("Enter time of song...");
        timeInput.setPrefSize(50, 50);

        videoNameInput = new TextField();
        videoNameInput.setPromptText("Enter video name...");
        videoNameInput.setPrefSize(50, 50);

        Button addButton = new Button("Add");
        addButton.setPrefSize(100, 50);
        addButton.setOnAction(e -> addToLibrary());

        Button backButton = new Button("Back");
        backButton.setPrefSize(100, 50);
        backButton.setOnAction(e -> {
            addSongsWindow.close();
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(addButton, backButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        //Whole Layout
        VBox layout = new VBox();
        layout.getChildren().addAll(titleInput, artistInput, timeInput, videoNameInput, hBox);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 500, 250);
        addSongsWindow.setScene(scene);
        addSongsWindow.show();
    }

    private void addToLibrary() {
        Song song = new Song();

        song.setTitle(titleInput.getText());
        song.setArtist(artistInput.getText());
        song.setTime(Double.parseDouble(timeInput.getText()));
        song.setVideoName(videoNameInput.getText());

        Stopwatch timer = new Stopwatch();

        timer.start();

        ST<String, SET<Song>> st = libraryFileIndex.symbolTableSong();
        st.put(song.getTitle(), new SET<>());

        SET<Song> songSET = st.get(song.getTitle());
        songSET.add(song);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(KaraokeApp.FilePath, true));

            for (Song newSong : songSET) {
                writer.newLine();
                writer.write(newSong.getTitle() + "\t" + newSong.getArtist() + "\t" + newSong.getTime() +
                        "\t" + newSong.getVideoName());
            }
            writer.close();
            System.out.println("Took " + timer.stop() + " seconds");
        } catch (IOException ex) {
            ErrorBox.error(ex);
        }

        librarySongTable.getItems().clear();
        getSongs();
    }

}
