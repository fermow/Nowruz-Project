package org.AP;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class EditRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final int id;
    private final String username;
    private final String suggestedLyrics;
    private final String requestDate;
    private String status; // "Pending", "Approved", "Rejected"
    private String responseNote;
    private final String songTitle;
    private LocalDateTime responseDate;

   
    public EditRequest(String username, String suggestedLyrics, String songTitle) {
        this.id = idCounter.incrementAndGet();
        this.username = username;
        this.suggestedLyrics = suggestedLyrics;
        this.songTitle = songTitle;
        this.requestDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "Pending";
        this.responseNote = "";
        this.responseDate = null;
    }
    public void approve(String note) {
        this.status = "Approved";
        this.responseNote = note != null ? note : "Approved by artist";
        this.responseDate = LocalDateTime.now();
    }

   
    public void reject(String note) {
        this.status = "Rejected";
        this.responseNote = note != null ? note : "Rejected by artist";
        this.responseDate = LocalDateTime.now();
    }

    
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getSuggestedLyrics() { return suggestedLyrics; }
    public String getRequestDate() { return requestDate; }
    public String getStatus() { return status; }
    public String getResponseNote() { return responseNote; }
    public String getSongTitle() { return songTitle; }

    public String getResponseDate() {
        return responseDate != null ?
                responseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                "N/A";
    }
    public boolean isPending() {
        return "Pending".equals(status);
    }


    public boolean isApproved() {
        return "Approved".equals(status);
    }

    public boolean isResponded() {
        return responseDate != null;
    }


    public String toDisplayString() {
        return String.format(
                "╔════════ Edit Request ════════\n" +
                        "║ ID: #%d\n" +
                        "║ Song: %s\n" +
                        "║ From: %s\n" +
                        "║ Request Date: %s\n" +
                        "║ Status: %s\n" +
                        "║ Response Date: %s\n" +
                        "╟──────────────────────────────\n" +
                        "║ Suggestion:\n%s\n" +
                        "╟──────────────────────────────\n" +
                        "║ Response: %s\n" +
                        "╚══════════════════════════════",
                id,
                songTitle,
                username,
                requestDate,
                status,
                getResponseDate(),
                suggestedLyrics,
                responseNote.isEmpty() ? "No response yet" : responseNote
        );
    }

   
    @Override
    public String toString() {
        return String.format("%d|%s|%s|%s|%s|%s|%s|%s",
                id,
                username,
                songTitle,
                suggestedLyrics,
                requestDate,
                status,
                responseNote,
                responseDate != null ? responseDate.toString() : "null"
        );
    }

    
    public static class Builder {
        private String username;
        private String suggestedLyrics;
        private String songTitle;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder suggestedLyrics(String suggestedLyrics) {
            this.suggestedLyrics = suggestedLyrics;
            return this;
        }

        public Builder songTitle(String songTitle) {
            this.songTitle = songTitle;
            return this;
        }

        public EditRequest build() {
            return new EditRequest(username, suggestedLyrics, songTitle);
        }
    }
}
