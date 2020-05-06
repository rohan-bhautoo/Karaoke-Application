import DataStructure.SET;
import DataStructure.ST;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code LibraryFileIndex} class provide user for indexing of the
 * library file specified in command-line arguments in {@code KaraokeApp} class.
 * It takes the file, where details of one song per line is separated by a \t,
 * and add it to an ordered symbol table {@code DataStructure.ST} of generic key-value pairs.
 */
public class LibraryFileIndex {
    
    public ST<String, SET<Song>> symbolTableSong() {
        // key = title, value = Song
        ST<String, SET<Song>> st = new ST<>();

        String[] word;
        try {
            // Read file from FilePath
            File file = new File(KaraokeApp.FilePath);
            Scanner scanner = new Scanner(file);

            // Loop until it reach end of line
            while (scanner.hasNextLine()) {
                // Instantiate song class
                Song song = new Song();

                // Split file using tab \t
                word = scanner.nextLine().split("\t");

                // Title is stored in index 0
                // Artist is stored in index 1
                // Time is stored in index 2
                // VideoName is stored in index 3
                song.setTitle(word[0]);
                song.setArtist(word[1]);
                song.setTime(Double.parseDouble(word[2]));
                song.setVideoName(word[3]);

                // Setting key equal to title of song
                st.put(song.getTitle(), new SET<>());

                // Add song to set
                SET<Song> set = st.get(song.getTitle());
                set.add(song);
            }
        } catch (IOException exception) {
            ErrorBox.error(exception);
        }

        return st;
    }
}
