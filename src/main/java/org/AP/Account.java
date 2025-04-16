package org.AP;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String email;
    private String username;
    private String password;
    private Role role;
    private List<String> lyricEditRequests = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<String> followingArtists = new ArrayList<String>();

    public Account(String name, int age, String email, String username, String password, Role role) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public void followArtist(String artistUsername) {
        if (!followingArtists.contains(artistUsername)) {
            followingArtists.add(artistUsername);
        }
    }

    public void unfollowArtist(String artistUsername) {
        followingArtists.remove(artistUsername);
    }

    public List<String> getFollowingArtists() {
        return followingArtists;
    }



    public void addLyricEditRequest(String suggestedLyric) {
        lyricEditRequests.add(suggestedLyric);
    }

    public void acceptLyricEdit(Song song, String acceptedLyric) {
        if (lyricEditRequests.contains(acceptedLyric)) {
            song.setLyrics(acceptedLyric);
            lyricEditRequests.remove(acceptedLyric);
            System.out.println("✅ Lyric updated successfully!");
        } else {
            System.out.println("❌ This lyric edit request does not exist.");
        }
    }

    public void rejectLyricEdit(String rejectedLyric) {
        if (lyricEditRequests.contains(rejectedLyric)) {
            lyricEditRequests.remove(rejectedLyric);
            System.out.println("❌ Lyric edit rejected.");
        } else {
            System.out.println("❌ This lyric edit request does not exist.");
        }
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
            System.out.println("✅ Song added successfully.");
        } else {
            System.out.println("❌ This song already exists.");
        }
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<String> getLyricEditRequests() {
        return lyricEditRequests;
    }

    public void removeLyricEditRequest(String lyricRequest) {
        lyricEditRequests.remove(lyricRequest);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public boolean songExists(String songTitle) {
        return songs.stream().anyMatch(song -> song.getTitle().equals(songTitle));
    }

    public int getNumberOfSongs() {
        return songs.size();
    }


}
