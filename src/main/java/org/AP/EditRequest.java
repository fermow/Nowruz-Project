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

    // ------------ Constructor ------------
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

    // ------------ Main Methods ------------
    /**
     * Approves the edit request with a response note
     * @param note The approval note/comment
     */
    public void approve(String note) {
        this.status = "Approved";
        this.responseNote = note != null ? note : "Approved by artist";
        this.responseDate = LocalDateTime.now();
    }

    /**
     * Rejects the edit request with a response note
     * @param note The rejection note/comment
     */
    public void reject(String note) {
        this.status = "Rejected";
        this.responseNote = note != null ? note : "Rejected by artist";
        this.responseDate = LocalDateTime.now();
    }

    // ------------ Accessor Methods ------------
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getSuggestedLyrics() { return suggestedLyrics; }
    public String getRequestDate() { return requestDate; }
    public String getStatus() { return status; }
    public String getResponseNote() { return responseNote; }
    public String getSongTitle() { return songTitle; }

    /**
     * Gets the response date in formatted string
     * @return Formatted date string or "N/A" if not responded
     */
    public String getResponseDate() {
        return responseDate != null ?
                responseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                "N/A";
    }

    // ------------ Status Check Methods ------------
    /**
     * Checks if request is pending
     * @return true if status is "Pending"
     */
    public boolean isPending() {
        return "Pending".equals(status);
    }

    /**
     * Checks if request is approved
     * @return true if status is "Approved"
     */
    public boolean isApproved() {
        return "Approved".equals(status);
    }

    /**
     * Checks if request has been responded to
     * @return true if response date exists
     */
    public boolean isResponded() {
        return responseDate != null;
    }

    // ------------ Display Methods ------------
    /**
     * Formats the request for display
     * @return Formatted string with all request details
     */
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

    // ------------ Serialization Method ------------
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

    // ------------ Builder Pattern (Optional) ------------
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