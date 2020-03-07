import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * Main class where application contains all functionality.
 * @author Rohan
 */
public class KaraokeApp extends Application {

    Stage window, viewLibrary;
    Player player;
    TableView<Song> libraryTable, playlistTable;
    ArrayList<Song> songArrayList;
    BufferedReader br;
    BufferedWriter bw;
    Scanner scanner;

    /**
     * Main method to launch application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method to start when application is launched.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Karaoke Media Player");

        /* Consumes the setOnCloseRequest and goes to closeProgram method.
        Closing the program with x will work properly. */
        window.setOnCloseRequest(e -> {
            e.consume();
            closeWindow();
        });

        //Menu
        //'_' is a shortcut for keyboard. ALT + F will open File Menu.
        Menu mediaMenu = new Menu("_Media");
        Menu playbackMenu = new Menu("_Playback");
        Menu audioMenu = new Menu("_Audio");
        Menu viewMenu = new Menu("_View");
        Menu helpMenu = new Menu("_Help");

        //Menu items
        MenuItems(mediaMenu, playbackMenu, audioMenu, viewMenu, helpMenu);

        //Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(mediaMenu, playbackMenu, audioMenu, viewMenu, helpMenu);

        // Label
        Label label = new Label("Karaoke Application");
        label.setStyle("-fx-font: 25 arial;");

        // Setting up buttons
        Button viewLibrary = new Button("View Library");
        viewLibrary.setStyle("-fx-font-size:20");
        viewLibrary.setPrefWidth(200);
        viewLibrary.setOnAction(e -> viewLibrary());

        // Add song button
        Button addSong = new Button("Add Song");
        addSong.setStyle("-fx-font-size:20");
        addSong.setPrefWidth(200);

        // Search song button
        Button searchSong = new Button("Search Song");
        searchSong.setStyle("-fx-font-size:20");
        searchSong.setPrefWidth(200);

        // Delete song button
        Button sortSong = new Button("Sort Song");
        sortSong.setStyle("-fx-font-size:20");
        sortSong.setPrefWidth(200);

        // Adding button to VBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, viewLibrary, addSong, searchSong, sortSong);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(280);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10,10,10,10));

        // Setting up right contents
        Label playlistLabel = new Label("Playlist");
        playlistLabel.setPadding(new Insets(5,5,5,5));

        // Creating a tableView
        playlistTable = new TableView();
        playlistTable.setPrefWidth(500);
        playlistTable.prefHeightProperty().bind(window.heightProperty());

        // Add to playlist button
        Button add = new Button("+");
        add.setStyle("-fx-font-size: 30");

        // Remove from playlist button
        Button remove = new Button("-");
        remove.setStyle("-fx-font-size: 30");

        // Adding buttons to HBox
        HBox playlistBtn = new HBox();
        playlistBtn.getChildren().addAll(add, remove);
        playlistBtn.setSpacing(20);
        playlistBtn.setPadding(new Insets(5,5,0,10));
        playlistBtn.setAlignment(Pos.CENTER);

        VBox playlist = new VBox();
        playlist.setSpacing(5);
        playlist.getChildren().addAll(playlistLabel, playlistTable, playlistBtn);

        // Adding file to media player
        player = new Player("file:///home/Rohan/IdeaProjects/KaraokeApp/Video/narcos2.mp4");

        // Setting up borderPane view
        player.setTop(menuBar);
        //player.setLeft(vBox);
        //player.setRight(playlist);
        Scene scene = new Scene(player);
        scene.getStylesheets().add("/Stylesheet/Stylesheet.css");
        window.setMaximized(true);
        window.setScene(scene);
        window.show();
    }

    /**
     * Add items to menu in top of application.
     * @param mediaMenu
     * @param playbackMenu
     * @param audioMenu
     * @param viewMenu
     * @param helpMenu
     */
    private void MenuItems(Menu mediaMenu, Menu playbackMenu, Menu audioMenu, Menu viewMenu, Menu helpMenu) {
        // Media Menu items
        mediaMenu.getItems().add(new MenuItem("Open File...\t\t\tCtrl+O"));
        mediaMenu.getItems().add(new MenuItem("Open Directory...\t\tCtrl+F"));

        // Separator creates a line between 2 MenuItems.
        mediaMenu.getItems().add(new SeparatorMenuItem());
        mediaMenu.getItems().add(new MenuItem("Save Playlist to File...\tCtrl+S"));
        mediaMenu.getItems().add(new SeparatorMenuItem());
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(e -> closeWindow());
        mediaMenu.getItems().add(quit);

        // Playback Menu items
        playbackMenu.getItems().add(new MenuItem("Play"));
        playbackMenu.getItems().add(new MenuItem("Stop"));
        playbackMenu.getItems().add(new MenuItem("Previous"));
        playbackMenu.getItems().add(new MenuItem("Next"));

        // Audio Menu items
        audioMenu.getItems().add(new MenuItem("Increase Volume"));
        audioMenu.getItems().add(new MenuItem("Decrease Volume"));
        audioMenu.getItems().add(new MenuItem("Mute"));

        // View Menu items
        viewMenu.getItems().add(new MenuItem("Playlist\t\t\t\tCtrl+L"));
        viewMenu.getItems().add(new SeparatorMenuItem());
        viewMenu.getItems().add(new MenuItem("Fullscreen Interface\t\tF11"));

        // Help Menu items
        helpMenu.getItems().add(new MenuItem("Help\t\t\t\t\tF1"));
        helpMenu.getItems().add(new SeparatorMenuItem());
        helpMenu.getItems().add(new MenuItem("Check for Updates"));
        helpMenu.getItems().add(new MenuItem("About\t\t\t\tShift+F1"));
    }

    /**
     * Execute function from ConfirmBox class to display message.
     */
    private void closeWindow() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if(answer) {
            window.close();
        }
    }

    private void libraryTable() {
        // Title column
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Title column
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        // Title column
        TableColumn<Song, Double> timeColumn = new TableColumn<>("Time(s)");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        //Creating tableView
        libraryTable = new TableView<>();

        /*
        The .bind() is used to make the height and width of the table to be exactly
        the same as the Stage(window) size.
         */
        libraryTable.prefHeightProperty().bind(window.heightProperty());
        libraryTable.prefWidthProperty().bind(window.widthProperty());

        /*
        The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
        This helps to remove extra spaces or unused columns.
         */
        libraryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Song> songObservableList = FXCollections.observableArrayList(songsLibrary());
        libraryTable.setItems(songObservableList);
        libraryTable.getColumns().addAll(titleColumn, artistColumn, timeColumn);
    }

    private List<Song> songsLibrary() {
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
            ex.printStackTrace();
        }

        return songArrayList;
    }

    private void viewLibrary() {
        viewLibrary = new Stage();

        libraryTable();

        BorderPane bp = new BorderPane();
        bp.setCenter(libraryTable);

        Scene scene = new Scene(bp);
        viewLibrary.setScene(scene);
        viewLibrary.show();
    }
}
