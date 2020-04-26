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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Playlist {

    Stage playlistWindow;
    public static LinkedList<Song> songs = new LinkedList<>();
    TableView<Song> songTableView;

    public void viewPlaylist() {
        playlistWindow = new Stage();
        playlistWindow.setTitle("Playlist");
        playlistWindow.initModality(Modality.APPLICATION_MODAL);

        playlistTable();

        songTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if(songTableView.getSelectionModel().getSelectedItem() != null) {
                    Song song =  songTableView.getSelectionModel().getSelectedItem();

                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Information");
                    message.setHeaderText(null);
                    message.setContentText(song.toString());

                    ButtonType buttonTypeRemove = new ButtonType("Remove");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    message.getButtonTypes().setAll(buttonTypeRemove, buttonTypeCancel);

                    Image songImg = new Image(getClass().getResourceAsStream("/Image/song.png"));
                    ImageView songImage = new ImageView(songImg);
                    songImage.setFitHeight(60);
                    songImage.setFitWidth(60);
                    message.setGraphic(songImage);
                    Optional<ButtonType> result = message.showAndWait();

                    if (result.isPresent() && result.get() == buttonTypeRemove) {
                        //songs.indexOf(song);

                        System.out.println(songs.remove(song));

                        /*System.out.println(songs.toString());

                        songTableView.getItems().clear();
                        getSongs();*/
                    }
                }
            }
        });

        BorderPane bp = new BorderPane();
        bp.setCenter(songTableView);

        Scene scene = new Scene(bp, 820, 900);
        scene.getStylesheets().add("/Stylesheet/ViewLibraryStylesheet.css");
        playlistWindow.setScene(scene);
        playlistWindow.show();
    }

    public LinkedList<Song> playlist() {
        return songs;
    }

    public void playlistTable() {
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Song, Double> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Song, String> videoColumn = new TableColumn<>("Video");
        videoColumn.setCellValueFactory(new PropertyValueFactory<>("videoName"));

        // Creating tableView
        songTableView = new TableView<>();

        /*
        The .bind() is used to make the height and width of the table to be exactly
        the same as the Stage(window) size.
         */
        songTableView.prefHeightProperty().bind(playlistWindow.heightProperty());
        songTableView.prefWidthProperty().bind(playlistWindow.widthProperty());

        /*
        The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
        This helps to remove extra spaces or unused columns.
         */
        songTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSongs();

        songTableView.getColumns().addAll(titleColumn, artistColumn, timeColumn, videoColumn);
    }

    private void getSongs() {
        ObservableList<Song> songsList = FXCollections.observableArrayList();

        for (Song s : playlist()) {
            Song song = new Song();

            song.setTitle(s.getTitle());
            song.setArtist(s.getArtist());
            song.setTime(s.getTime());
            song.setVideoName(s.getVideoName());

            songsList.add(song);
        }

        songTableView.setItems(songsList);
    }

}
