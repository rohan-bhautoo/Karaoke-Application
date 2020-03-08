import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ViewLibrary {

    Stage viewLibraryWindow;
    TableView<Song> libraryTable;
    ArrayList<Song> songArrayList;
    BufferedReader br;

    public void viewLibrary() {
        viewLibraryWindow = new Stage();

        viewLibraryWindow.setOnCloseRequest(e -> {
            e.consume();
            closeLibraryWindow();
        });

        libraryTable();

        libraryTable.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if(libraryTable.getSelectionModel().getSelectedItem() != null) {
                    Song song =  libraryTable.getSelectionModel().getSelectedItem();

                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Information");
                    message.setHeaderText(null);
                    message.setContentText("Title: " + song.getTitle() + "\n"
                            + "Time: " + song.getTime() + "\n"
                            + "Artist: " + song.getArtist());

                    ButtonType buttonTypeAdd = new ButtonType("Add");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    message.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

                    Image songImg = new Image(getClass().getResourceAsStream("/Image/song.png"));
                    ImageView songImage = new ImageView(songImg);
                    songImage.setFitHeight(60);
                    songImage.setFitWidth(60);
                    message.setGraphic(songImage);
                    message.showAndWait();
                }
            }
        });

        // Buttons
        Button addBtn = new Button("Add Song");
        addBtn.setStyle("-fx-font-size:20");
        addBtn.setPrefWidth(200);

        Button searchBtn = new Button("Search Song");
        searchBtn.setStyle("-fx-font-size:20");
        searchBtn.setPrefWidth(200);

        Button sortBtn = new Button("Sort Song");
        sortBtn.setStyle("-fx-font-size:20");
        sortBtn.setPrefWidth(200);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size:20");
        backBtn.setPrefWidth(120);
        backBtn.setOnAction(e -> closeLibraryWindow());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(addBtn, searchBtn, sortBtn, backBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(280);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20,10,10,10));

        BorderPane bp = new BorderPane();
        bp.setCenter(libraryTable);
        bp.setLeft(vBox);

        Scene scene = new Scene(bp);
        scene.getStylesheets().add("/Stylesheet/ViewLibraryStylesheet.css");
        viewLibraryWindow.setScene(scene);
        viewLibraryWindow.setMaximized(true);
        viewLibraryWindow.show();
    }

    private void closeLibraryWindow() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if(answer) {
            viewLibraryWindow.close();
        }
    }

    private void libraryTable() {
        // Title column
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Artist column
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        // Time column
        TableColumn<Song, Double> timeColumn = new TableColumn<>("Time(s)");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Creating tableView
        libraryTable = new TableView<>();

        /*
        The .bind() is used to make the height and width of the table to be exactly
        the same as the Stage(window) size.
         */
        libraryTable.prefHeightProperty().bind(viewLibraryWindow.heightProperty());
        libraryTable.prefWidthProperty().bind(viewLibraryWindow.widthProperty());

        /*
        The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
        This helps to remove extra spaces or unused columns.
         */
        libraryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Song> songObservableList = FXCollections.observableArrayList(songsLibrary());
        libraryTable.setItems(songObservableList);
        libraryTable.getColumns().addAll(titleColumn, artistColumn, timeColumn);
    }

    public List<Song> songsLibrary() {
        songArrayList = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader("sample_song_data"));

            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                Song song = new Song();
                String[] songList = inputLine.split("\t");

                song.setTitle(songList[0]);
                song.setArtist(songList[1]);
                song.setTime(Double.parseDouble(songList[2]));
                song.setVideoName(songList[3]);

                songArrayList.add(song);
            }

            br.close();
        } catch (Exception ex) {
            ErrorBox.error(ex);
        }

        return songArrayList;
    }

}
