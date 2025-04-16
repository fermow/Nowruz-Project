package org.AP;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Song implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private String lyrics;
    private String releaseDate;
    private String genre;
    private List<String> tags;
    private String artistUsername;
    private int viewCount;
    private List<Comment> comments = new ArrayList<>();
    private List<EditRequest> editRequests = new ArrayList<>();

    public Song(String title, String lyrics, String releaseDate, String genre,
                List<String> tags, String artistUsername) {
        this.title = title;
        this.lyrics = lyrics;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.tags = new ArrayList<>(tags); 
        this.artistUsername = artistUsername;
        this.viewCount = 0;
    }

    
    public String getTitle() { return title; }
    public String getLyrics() { return lyrics; }
    public String getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public List<String> getTags() { return new ArrayList<>(tags); } 
    public String getArtistUsername() { return artistUsername; }
    public int getViewCount() { return viewCount; }
    public List<Comment> getComments() { return new ArrayList<>(comments); }
    public List<EditRequest> getEditRequests() { return new ArrayList<>(editRequests); }


    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        Database.saveSongs();
    }


    public void incrementViewCount() {
        this.viewCount++;
        Database.saveSongs();
    }

    public void addComment(String username, String text) {
        comments.add(new Comment(username, text));
        Database.saveSongs();
    }

    public void addEditRequest(String username, String suggestedLyrics, String title) {
        editRequests.add(new EditRequest(username, suggestedLyrics, this.title));
        Database.saveSongs();
    }

    public void approveEditRequest(int requestId) {
        editRequests.stream()
                .filter(r -> r.getId() == requestId)
                .findFirst()
                .ifPresent(request -> {
                    this.lyrics = request.getSuggestedLyrics();
                    editRequests.remove(request);
                    Database.saveSongs();
                });
    }

    public void displayComments() {
        if (comments.isEmpty()) {
            System.out.println("No comments yet.");
            return;
        }
        System.out.println("\n💬 Comments (" + comments.size() + "):");
        comments.forEach(comment ->
                System.out.printf("%s: %s\n", comment.getUsername(), comment.getText()));
    }

    @Override
    public String toString() {
        return String.format(
                "🎵 Title: %s\n" +
                        "👁️ Views: %d\n" +
                        "📝 Lyrics: %s\n" +
                        "📅 Released: %s\n" +
                        "🎼 Genre: %s\n" +
                        "🏷 Tags: %s\n" +
                        "🎤 Artist: %s",
                title, viewCount, lyrics, releaseDate,
                genre, String.join(", ", tags), artistUsername
        );
    }
}
