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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.security.auth.kerberos.KerberosKey;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ViewLibrary {

    MediaBar mediaBar;
    Stage viewLibraryWindow;
    TableView<Song> libraryTable;
    ArrayList<Song> songArrayList;
    BufferedReader br;
    FileChooser fileChooser;

    public void viewLibrary() {
        //mediaBar.player.pause();

        viewLibraryWindow = new Stage();

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

        VBox vBox = new VBox();
        vBox.getChildren().addAll(addBtn, searchBtn, sortBtn);
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

    private List<Song> songsLibrary() {
        songArrayList = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(chooser()));

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
            //ErrorBox.error(ex);
            ex.printStackTrace();
        }

        return songArrayList;
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

        ObservableList<Song> songList = FXCollections.observableArrayList(songsLibrary());
        libraryTable.setItems(songList);
        libraryTable.getColumns().addAll(titleColumn, artistColumn, timeColumn);
    }

    private String chooser() {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Library"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(viewLibraryWindow);

        if (file != null) {
            getLibrary(file.getAbsolutePath());
        }

        return file.getAbsolutePath();
    }

    private String getLibrary(String library) {
        return library;
    }

}
