package org.AP;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserMenu {

    public static void show(Account account, Scanner scanner) {
        while (true) {
            System.out.println("\n🎶 User Dashboard 🎶");
            System.out.println("══════════════════════");
            System.out.println("1. 🔍 Search Artists");
            System.out.println("2. 🎵 Browse Music");
            System.out.println("3. 👥 Following List");
            System.out.println("4. ➕ Follow Artist");
            System.out.println("5. 🎶 Music from Followed Artists");
            System.out.println("6. ✏️ Submit Lyric Edit");
            System.out.println("7. 🚪 Log Out");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number (1-7)");
                continue;
            }

            switch (choice) {
                case 1:
                    searchArtist(account, scanner);
                    break;
                case 2:
                    viewMusic(account, scanner);
                    break;
                case 3:
                    viewFollowedArtists(account);
                    break;
                case 4:
                    followArtist(account, scanner);
                    break;
                case 5:
                    viewFollowedArtistsMusic(account);
                    break;
                case 6:
                    requestLyricEdit(account, scanner);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid option! Please choose 1-7");
            }
        }
    }

    private static void searchArtist(Account account, Scanner scanner) {
        System.out.println("\n════════ Artist Search ════════");
        System.out.print("Enter artist name or username: ");
        String query = scanner.nextLine().trim();

        List<Account> results = Database.getApprovedArtists().stream()
                .filter(artist -> artist.getUsername().equalsIgnoreCase(query) ||
                        artist.getName().equalsIgnoreCase(query))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("❌ No matching artists found");
            return;
        }

        System.out.println("\n🔍 Search Results:");
        results.forEach(artist ->
                System.out.printf("- %s (@%s)\n", artist.getName(), artist.getUsername()));

        System.out.print("\nView profile? (username or 'no'): ");
        String input = scanner.nextLine();

        if (!input.equalsIgnoreCase("no")) {
            Account selected = Database.getAccountByUsername(input);
            if (selected != null) {
                showArtistProfile(selected);
            } else {
                System.out.println("❌ Artist not found");
            }
        }
    }

    private static void showArtistProfile(Account artist) {
        System.out.println("\n════════ Artist Profile ════════");
        System.out.printf("👤 %s (@%s)\n", artist.getName(), artist.getUsername());

        List<Song> songs = Database.getSongs().stream()
                .filter(s -> s.getArtistUsername().equals(artist.getUsername()))
                .collect(Collectors.toList());

        System.out.printf("\n🎵 %d Songs:\n", songs.size());
        songs.forEach(s -> System.out.printf("- %s (%d views)\n",
                s.getTitle(), s.getViewCount()));

        System.out.println("══════════════════════════════");
    }

    private static void viewMusic(Account account, Scanner scanner) {
        List<Song> songs = Database.getSongs();
        if (songs.isEmpty()) {
            System.out.println("❌ No songs available");
            return;
        }

        System.out.println("\n════════ Browse Songs ════════");
        System.out.println("Sort by:");
        System.out.println("1. Most Popular");
        System.out.println("2. Newest");
        System.out.println("3. By Artist");
        System.out.print("Choose: ");

        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        List<Song> sortedSongs = songs.stream()
                .sorted((s1, s2) -> {
                    switch (sortChoice) {
                        case 2: return s2.getReleaseDate().compareTo(s1.getReleaseDate());
                        case 3: return s1.getArtistUsername().compareTo(s2.getArtistUsername());
                        default: return Integer.compare(s2.getViewCount(), s1.getViewCount());
                    }
                })
                .collect(Collectors.toList());

        System.out.println("\nAvailable Songs:");
        sortedSongs.forEach(s ->
                System.out.printf("- %s by @%s (%d views)\n",
                        s.getTitle(), s.getArtistUsername(), s.getViewCount()));

        System.out.print("\nEnter song number to view (0 to cancel): ");
        int songNum = scanner.nextInt();
        scanner.nextLine();

        if (songNum == 0) return;
        if (songNum < 1 || songNum > songs.size()) {
            System.out.println("❌ Invalid selection");
            return;
        }

        Song selected = sortedSongs.get(songNum - 1);
        selected.incrementViewCount();

        System.out.println("\n════════ Song Details ════════");
        System.out.println(selected.toString());

        // Interaction menu
        System.out.println("\n1. Add Comment");
        System.out.println("2. Request Edit");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                System.out.print("Your comment: ");
                String comment = scanner.nextLine();
                selected.addComment(account.getUsername(), comment);
                System.out.println("✅ Comment added");
                break;
            case 2:
                System.out.print("Suggested lyrics: ");
                String lyrics = scanner.nextLine();
                selected.addEditRequest(account.getUsername(), lyrics, selected.getTitle());
                System.out.println("✅ Edit request submitted");
                break;
        }
    }

    private static void viewFollowedArtists(Account account) {
        if (account.getFollowingArtists().isEmpty()) {
            System.out.println("\n❌ You're not following any artists yet.");
            return;
        }

        System.out.println("\n👥 Artists You Follow:");
        account.getFollowingArtists().forEach(username -> {
            Account artist = Database.getAccountByUsername(username);
            System.out.printf("- %s (@%s)\n", artist.getName(), username);
        });
    }

    private static void followArtist(Account user, Scanner scanner) {
        System.out.print("\nEnter artist username to follow: ");
        String username = scanner.nextLine().trim();

        Account artist = Database.getAccountByUsername(username);
        if (artist == null || artist.getRole() != Role.ARTIST) {
            System.out.println("❌ Artist not found!");
            return;
        }

        if (user.getFollowingArtists().contains(username)) {
            System.out.println("❌ You're already following this artist!");
            return;
        }

        user.followArtist(username);
        Database.saveAccounts();
        System.out.println("✅ You're now following @" + username);
    }

    private static void viewFollowedArtistsMusic(Account account) {
        if (account.getFollowingArtists().isEmpty()) {
            System.out.println("\n❌ Follow some artists first!");
            return;
        }

        System.out.println("\n🎶 Music from Artists You Follow:");
        account.getFollowingArtists().forEach(username -> {
            List<Song> songs = Database.getSongs().stream()
                    .filter(s -> s.getArtistUsername().equals(username))
                    .collect(Collectors.toList());

            if (!songs.isEmpty()) {
                System.out.println("\n@" + username + ":");
                songs.forEach(s -> System.out.println("- " + s.getTitle()));
            }
        });
    }

    private static void requestLyricEdit(Account user, Scanner scanner) {
        System.out.println("\n✏️ Select a song to request edit:");
        List<Song> songs = Database.getSongs();

        if (songs.isEmpty()) {
            System.out.println("❌ No songs available!");
            return;
        }

        for (int i = 0; i < songs.size(); i++) {
            System.out.printf("%d. %s by @%s\n",
                    i+1, songs.get(i).getTitle(), songs.get(i).getArtistUsername());
        }

        System.out.print("\nEnter song number (0 to cancel): ");
        int songNum;
        try {
            songNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!");
            return;
        }

        if (songNum == 0) return;
        if (songNum < 1 || songNum > songs.size()) {
            System.out.println("❌ Invalid selection!");
            return;
        }

        Song selected = songs.get(songNum - 1);
        System.out.println("\nCurrent lyrics:\n" + selected.getLyrics());

        System.out.print("\nEnter your suggested lyrics (or 'cancel' to abort):\n");
        String suggestion = scanner.nextLine();

        if (suggestion.equalsIgnoreCase("cancel")) {
            return;
        }


        selected.addEditRequest(user.getUsername(), suggestion, selected.getTitle());
        Database.saveSongs();
        System.out.println("\n✅ Your edit request has been sent to @" + selected.getArtistUsername());
    }
}

