package org.AP;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminMenu {

    public static void show(Account account, Scanner scanner) {
        System.out.println("\n════════ ADMIN DASHBOARD ════════");
        System.out.println("Welcome, " + account.getName() + "!");

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. 👥 View All Registered Users");
            System.out.println("2. 🎤 View All Approved Artists");
            System.out.println("3. ⏳ View Pending Artist Requests");
            System.out.println("4. 🚪 Log Out");
            System.out.print("👉 Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllUsers(scanner);
                    break;
                case "2":
                    showAllArtists(scanner);
                    break;
                case "3":
                    managePendingArtists(scanner);
                    break;

                case "4":
                    System.out.println("\nLogging out...");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void showAllUsers(Scanner scanner) {
        List<Account> users = Database.getUsers();
        if (users.isEmpty()) {
            System.out.println("\n❌ No registered users found.");
            return;
        }

        System.out.println("\n════════ REGISTERED USERS (" + users.size() + ") ════════");
        for (int i = 0; i < users.size(); i++) {
            Account user = users.get(i);
            System.out.printf("%d. %s (@%s) | Age: %d | Email: %s\n",
                    i + 1, user.getName(), user.getUsername(),
                    user.getAge(), user.getEmail());
        }

        System.out.print("\nEnter user number to view details (0 to return): ");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection > 0 && selection <= users.size()) {
                viewUserDetails(users.get(selection - 1), scanner);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!");
        }
    }

    private static void showAllArtists(Scanner scanner) {
        List<Account> artists = Database.getApprovedArtists();
        if (artists.isEmpty()) {
            System.out.println("\n❌ No approved artists found.");
            return;
        }

        System.out.println("\n════════ APPROVED ARTISTS (" + artists.size() + ") ════════");
        for (int i = 0; i < artists.size(); i++) {
            Account artist = artists.get(i);
            System.out.printf("%d. %s (@%s) | Songs: %d | Followers: %d\n",
                    i + 1, artist.getName(), artist.getUsername(),
                    artist.getSongs().size(), artist.getFollowingArtists().size());
        }

        System.out.print("\nEnter artist number to view details (0 to return): ");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection > 0 && selection <= artists.size()) {
                viewArtistDetails(artists.get(selection - 1), scanner);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!");
        }
    }

    private static void managePendingArtists(Scanner scanner) {
        List<UnverifiedArtist> pending = Database.getPendingArtists();
        if (pending.isEmpty()) {
            System.out.println("\n✅ No pending artist requests.");
            return;
        }

        System.out.println("\n════════ PENDING ARTISTS (" + pending.size() + ") ════════");
        for (int i = 0; i < pending.size(); i++) {
            UnverifiedArtist artist = pending.get(i);
            System.out.printf("%d. %s (@%s) | Age: %d | Email: %s\n",
                    i + 1, artist.getName(), artist.getUsername(),
                    artist.getAge(), artist.getEmail());
        }

        System.out.print("\nEnter artist number to approve/reject (0 to cancel): ");
        try {
            int selected = Integer.parseInt(scanner.nextLine());
            if (selected > 0 && selected <= pending.size()) {
                UnverifiedArtist artist = pending.get(selected - 1);
                System.out.println("\n1. Approve Artist");
                System.out.println("2. Reject Application");
                System.out.print("Choose action: ");

                String action = scanner.nextLine();
                if (action.equals("1")) {
                    Artist approved = new Artist(
                            artist.getName(),
                            artist.getAge(),
                            artist.getEmail(),
                            artist.getUsername(),
                            artist.getPassword()
                    );
                    Database.addApprovedArtistToAccounts(approved);
                    Database.removePendingArtist(artist);
                    System.out.println("✅ Artist approved successfully!");
                } else if (action.equals("2")) {
                    Database.removePendingArtist(artist);
                    System.out.println("❌ Artist application rejected.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input!");
        }
    }

    private static void viewUserDetails(Account user, Scanner scanner) {
        System.out.println("\n════════ USER DETAILS ════════");
        System.out.println("👤 Name: " + user.getName());
        System.out.println("📛 Username: @" + user.getUsername());
        System.out.println("🎂 Age: " + user.getAge());
        System.out.println("📧 Email: " + user.getEmail());
        System.out.println("📅 Account Type: " + user.getRole());
        System.out.println("👥 Following: " + user.getFollowingArtists().size() + " artists");

        System.out.println("\n1. Ban/Unban this user");
        System.out.println("2. View following list");
        System.out.println("3. Back to menu");
        System.out.print("Choose action: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                // Ban/unban logic
                break;
            case "2":
                showFollowingList(user);
                break;
        }
    }

    private static void viewArtistDetails(Account artist, Scanner scanner) {
        System.out.println("\n════════ ARTIST DETAILS ════════");
        System.out.println("🎤 Name: " + artist.getName());
        System.out.println("📛 Username: @" + artist.getUsername());
        System.out.println("🎂 Age: " + artist.getAge());
        System.out.println("📧 Email: " + artist.getEmail());
        System.out.println("🎵 Songs: " + artist.getSongs().size());
        System.out.println("👥 Followers: " + artist.getFollowingArtists().size());

        System.out.println("\n1. View songs");
        System.out.println("2. View followers");
        System.out.println("3. Back to menu");
        System.out.print("Choose action: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                showArtistSongs(artist);
                break;
            case "2":
                showFollowersList(artist);
                break;
        }
    }

    private static void showFollowingList(Account user) {
        List<String> following = user.getFollowingArtists();
        if (following.isEmpty()) {
            System.out.println("\n❌ This user isn't following anyone.");
            return;
        }

        System.out.println("\n════════ FOLLOWING LIST ════════");
        following.forEach(username -> {
            Account artist = Database.getAccountByUsername(username);
            System.out.println("- " + artist.getName() + " (@" + username + ")");
        });
    }

    private static void showArtistSongs(Account artist) {
        List<Song> songs = artist.getSongs();
        if (songs.isEmpty()) {
            System.out.println("\n❌ This artist hasn't uploaded any songs yet.");
            return;
        }

        System.out.println("\n════════ SONGS ════════");
        songs.forEach(song -> {
            System.out.println("- " + song.getTitle() + " (" + song.getViewCount() + " views)");
        });
    }

    private static void showFollowersList(Account artist) {
        List<Account> followers = Database.getUsers().stream()
                .filter(u -> u.getFollowingArtists().contains(artist.getUsername()))
                .collect(Collectors.toList());

        if (followers.isEmpty()) {
            System.out.println("\n❌ This artist has no followers yet.");
            return;
        }

        System.out.println("\n════════ FOLLOWERS ════════");
        followers.forEach(user -> {
            System.out.println("- " + user.getName() + " (@" + user.getUsername() + ")");
        });
    }
}