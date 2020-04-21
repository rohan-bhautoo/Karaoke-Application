public class Song implements Comparable<Song> {
    private String title;
    private String artist;
    private double time;
    private String videoName;

    public Song() {
        this.title = " ";
        this.artist = " ";
        this.time = 0.0;
        this.videoName = " ";
    }

    public Song(String title, String artist, double time, String videoName) {
        this.title = title;
        this.artist = artist;
        this.time = time;
        this.videoName = videoName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Artist: " + artist + "\n" +
                "Time: " + time + "\n" +
                "VideoName: " + videoName;
    }

    @Override
    public int compareTo(Song song) {
        return 0;
    }
}
