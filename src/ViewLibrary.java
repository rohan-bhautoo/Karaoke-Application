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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class ViewLibrary {

    Stage viewLibraryWindow;
    TableView<ST<String, SET<Song>>> librarySongTable;
    LibraryFileIndex libraryFileIndex;
    TextField searchTextField;

    public void viewLibrary() {
        viewLibraryWindow = new Stage();
        viewLibraryWindow.setTitle("Library");

        libraryTable();

        librarySongTable.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if(librarySongTable.getSelectionModel().getSelectedItem() != null) {
                    ST<String, SET<Song>> song =  librarySongTable.getSelectionModel().getSelectedItem();

                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.initStyle(StageStyle.UTILITY);
                    message.setTitle("Information");
                    message.setHeaderText(null);
                    message.setContentText(song.toString());

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

        Image sortImg = new Image(getClass().getResourceAsStream("/Image/sort.png"));
        ImageView sortImage = new ImageView(sortImg);
        sortImage.setFitHeight(30);
        sortImage.setFitWidth(30);
        Button sortBtn = new Button("");
        sortBtn.setStyle("-fx-font-size:20");
        sortBtn.setGraphic(sortImage);

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(searchTextField, searchBtn, spacer, addBtn, sortBtn);
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
        // Title column
        TableColumn<ST<String, SET<Song>>, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Artist column
        TableColumn<ST<String, SET<Song>>, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        // Time column
        TableColumn<ST<String, SET<Song>>, Double> timeColumn = new TableColumn<>("Time(s)");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

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

        libraryFileIndex = new LibraryFileIndex();
        ST<String, SET<Song>> st = libraryFileIndex.songLibrary();

        for (String s : st.keys()) {
            System.out.println(s);
        }

        /*ArrayList<ST<String, SET<Song>>> arrayList = new ArrayList<>();
        arrayList.add(libraryFileIndex.songLibrary());
        System.out.println(arrayList.toString());*/

        //ObservableList<ST<String, SET<Song>>> songList = FXCollections.observableArrayList(arrayList);

        //librarySongTable.setItems(songList);
        librarySongTable.getColumns().addAll(titleColumn, artistColumn, timeColumn);
    }

    private void search() {
        libraryFileIndex = new LibraryFileIndex();
        final int DELAY = 500;

        Stopwatch timer = new Stopwatch();

        timer.start();

        try {
            String query = searchTextField.getText();

            Alert songInformation = new Alert(Alert.AlertType.INFORMATION);
            songInformation.initStyle(StageStyle.UTILITY);
            songInformation.setTitle("Information");
            songInformation.setHeaderText(null);

            if (libraryFileIndex.songLibrary().contains(query)) {
                SET<Song> set = libraryFileIndex.songLibrary().get(query);
                songInformation.setContentText(set.toString());
                System.out.println("Took " + timer.stop() + " seconds");

                ButtonType buttonTypeAdd = new ButtonType("Add");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                songInformation.getButtonTypes().setAll(buttonTypeAdd, buttonTypeCancel);

                Image songImg = new Image(getClass().getResourceAsStream("/Image/song.png"));
                ImageView songImage = new ImageView(songImg);
                songImage.setFitHeight(60);
                songImage.setFitWidth(60);
                songInformation.setGraphic(songImage);
            } else {
                songInformation.setContentText("Not Found!");
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                songInformation.getButtonTypes().setAll(cancel);
            }
            songInformation.showAndWait();

            Thread.sleep(DELAY);
        } catch(InterruptedException ex) {
            System.out.println("Sleep interrupted");
        }
    }

}
