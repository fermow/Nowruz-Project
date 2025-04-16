package org.AP;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    ;
    private String title;
    private String releaseDate;
    private String genre;
    private String artistUsername;
    private List<Song> songs;

    public Album(String title, String releaseDate, String genre, String artistUsername, List<Song> songs) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.artistUsername = artistUsername;
        this.songs = songs;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtistUsername() {
        return artistUsername;
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public String toString() {
        return "📀 Album: " + title + " (" + releaseDate + ") by " + artistUsername + " | " + songs.size() + " song(s)";
    }
}
