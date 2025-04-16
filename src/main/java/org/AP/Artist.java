package org.AP;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artist extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isVerified;
    private List<Song> songs;


    public Artist(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, Role.ARTIST);
        this.isVerified = false;
        this.songs = new ArrayList<>();

    }

    public boolean isVerified() {
        return isVerified;
    }

    public void verify() {
        this.isVerified = true;
    }

    public void reject() {
        this.isVerified = false;
    }



   
    public void addSong(Song song) {
        this.songs.add(song);
    }



    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public String toString() {
        return super.toString() + (isVerified ? " (Verified)" : " (Pending verification)");
    }
}
