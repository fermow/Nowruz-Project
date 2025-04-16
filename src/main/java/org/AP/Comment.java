package org.AP;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment implements Serializable {
    private String username;
    private String text;
    private String timestamp;

    public Comment(String username, String text) {
        this.username = username;
        this.text = text;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Getter methods
    public String getUsername() { return username; }
    public String getText() { return text; }
    public String getTimestamp() { return timestamp; }
}