import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

/**
 * The {@code KaraokeApp} class contains the main application contents. It
 * takes a file-path from command-line arguments. It takes the input file and
 * add it to the {@code LibraryFileIndex} class.
 *
 * Compilation: javac KaraokeApp.java
 * Execution:   java KaraokeApp --filePath=Library/sample_song_data.txt
 * The filePath specified can be any valid path to a music library text file.
 */
public class KaraokeApp extends Application {

    Stage window;
    Player player;
    MenuBar menuBar;
    static String FilePath;

    /**
     * Main method to launch application.
     * @param args command-line argument
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The {@code start} method contains the main entry for the JavaFX
     * application where the Stage is the top-level container.
     * @param primaryStage The main stage of application
     */
    @Override
    public void start(Stage primaryStage) {

        // Getting the command-line arguments in Parameter p.
        Parameters p = this.getParameters();

        // Retrieve read-only map of named parameters.
        Map<String, String> namedParams = p.getNamed();

        // Retrieve read-only raw arguments.
        List<String> rawArguments = p.getRaw();

        // Validation to check if user specified only one argument.
        if (rawArguments.size() != 1) {
            System.out.println("Invalid Command!\n" +
                    "Usage: java KaraokeApp --filePath=");
            System.exit(1);
        } else if (namedParams.size() != 1) {
            System.out.println("File path not specified.\n" +
                    "Usage: java KaraokeApp --filePath=");
            System.exit(1);
        }

        // Setting FilePath to the value of the named parameter.
        for (Map.Entry<String,String> entry : namedParams.entrySet()) {
            FilePath = entry.getValue();
        }

        window = primaryStage;
        window.setTitle("Karaoke Media Player");

        // Consumes the setOnCloseRequest and goes to closeWindow method.
        // Closing the program with x will work properly.
        window.setOnCloseRequest(e -> {
            e.consume();
            closeWindow();
        });

        // Menu
        // '_' is a shortcut for keyboard. ALT + F will open File Menu.
        Menu mediaMenu = new Menu("_Media");
        Menu playbackMenu = new Menu("_Playback");
        Menu audioMenu = new Menu("_Audio");
        Menu viewMenu = new Menu("_View");
        Menu helpMenu = new Menu("_Help");

        // Menu items
        MenuItems(mediaMenu, playbackMenu, audioMenu, viewMenu, helpMenu);

        // Menu Bar
        // Adding menu to menuBar.
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(mediaMenu, playbackMenu, audioMenu, viewMenu, helpMenu);

        // Instantiate media player
        player = new Player();

        // Setting up borderPane view
        player.setTop(menuBar);
        Scene scene = new Scene(player);

        // Applying stylesheet to scene.
        scene.getStylesheets().add("/Stylesheet/Stylesheet.css");
        window.setMaximized(true);
        window.setScene(scene);
        window.show();
    }

    /**
     * The {@code MenuItems} method adds menu-items to Menus. It is displayed
     * on the top section of the borderPane.
     * @param mediaMenu mediaMenu containing Open File, Open Directory and Quit function
     * @param playbackMenu playbackMenu containing MediaPlayer functions
     * @param audioMenu audioMenu containing volume functions
     * @param viewMenu viewMenu containing playlist and fullscreen
     * @param helpMenu helpMenu containing a help section for the user
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
     * Execute function from {@code ConfirmBox} class to display
     * confirmation message.
     * The {@code closeWindow} method uses Platform.exit() to exit
     * application even if other sub-stages are opened.
     */
    private void closeWindow() {
        Boolean answer = ConfirmBox.confirmation("Exit", "Are you sure you want to exit?", null);
        if(answer) {
            Platform.exit();
        }
    }
}
