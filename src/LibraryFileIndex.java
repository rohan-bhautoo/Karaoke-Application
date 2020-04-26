import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LibraryFileIndex {

    public ST<String, SET<Song>> symbolTableSong() {
        // key = word, value = Song
        ST<String, SET<Song>> st = new ST<>();

        String[] word;
        try {
            File file = new File(KaraokeApp.FilePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                Song song = new Song();

                word = scanner.nextLine().split("\t");

                song.setTitle(word[0]);
                song.setArtist(word[1]);
                song.setTime(Double.parseDouble(word[2]));
                song.setVideoName(word[3]);

                st.put(song.getTitle(), new SET<>());
                SET<Song> set = st.get(song.getTitle());
                set.add(song);
            }
        } catch (IOException exception) {
            ErrorBox.error(exception);
        }

        return st;
    }
}
