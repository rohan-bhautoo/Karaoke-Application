import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * Main class where application contains all functionality.
 * @author Rohan
 */
public class KaraokeApp extends Application {

    Stage window;
    Player player;

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
     */
    @Override
    public void start(Stage primaryStage) {
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

        // Adding file to media player
        player = new Player("file:///home/Rohan/IdeaProjects/KaraokeApp/Video/narcos2.mp4");

        // Setting up borderPane view
        player.setTop(menuBar);
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
            Platform.exit();
        }
    }
}
