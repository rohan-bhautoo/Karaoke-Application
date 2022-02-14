package com.karaokeapp;

/**
 * The {@code Song} class is used to initialise the object of
 * type Song which will hold the details of a song. The detail
 * will include song title, artist, time and video file name.
 */
public class Song implements Comparable<Song> {
    private String title;
    private String artist;
    private double time;
    private String videoName;

    /**
     * Default constructor for {@code Song} class.
     */
    public Song() {
        this.title = " ";
        this.artist = " ";
        this.time = 0.0;
        this.videoName = " ";
    }

    /**
     * Second constructor for {@code Song} class.
     * @param title string title of song
     * @param artist string artist name
     * @param time double time of song
     * @param videoName string video file name
     */
    public Song(String title, String artist, double time, String videoName) {
        this.title = title;
        this.artist = artist;
        this.time = time;
        this.videoName = videoName;
    }

    /**
     * Method to get title of song.
     * @return string title of song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set value to equal to title.
     * @param title string title of song
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get artist name.
     * @return string artist name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Method to set value to equal to the artist name.
     * @param artist string artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Method to get time duration of song.
     * @return double time of song
     */
    public double getTime() {
        return time;
    }

    /**
     * Method to set value to equal to the time duration of song.
     * @param time double time of song
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Method to get video file name of song.
     * @return string video file name of song
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * Method to set value to equal to the video file name of song.
     * @param videoName video file name of song
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * Method to return string representation of title, artist, time and videoName.
     * @return string representation of title, artist, time and videoName
     */
    @Override
    public String toString() {
        return title + "\n" + artist + "\n" + time + "\n" + videoName;
    }

    /**
     * Method to compare values of song.
     * @param song Object song
     * @return 0
     */
    @Override
    public int compareTo(Song song) {
        return 0;
    }
}
