package org.AP;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ArtistMenu {
    public static void show(Account account, Scanner scanner) {
        while (true) {
            System.out.println("\n🎤 Artist Dashboard 🎤");
            System.out.println("══════════════════════");
            System.out.println("1. 🎵 Create New Song");
            System.out.println("2. 💿 Create New Album");
            System.out.println("3. ✏️ Edit Song Lyrics");
            System.out.println("4. 📊 View Statistics");
            System.out.println("5. ✉️ Review Edit Requests");
            System.out.println("6. 👤 Profile Settings");
            System.out.println("7. 🚪 Log Out");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1:
                    createNewSong(account, scanner);
                    break;
                case 2:
                    createNewAlbum(account, scanner);
                    break;
                case 3:
                    editSongLyrics(scanner);
                    break;
                case 4:
                    viewStatistics(account);
                    break;
                case 5:
                    reviewEditRequests(account, scanner);
                    break;
                case 6:
                    showProfile(account, scanner);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        }
    }

    private static void createNewSong(Account artist, Scanner scanner) {
        System.out.println("\n════════ Create New Song ════════");

        System.out.print("🎵 Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("📝 Lyrics (type 'END' on new line to finish):\n");
        StringBuilder lyrics = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            lyrics.append(line).append("\n");
        }

        System.out.print("📅 Release Date (YYYY-MM-DD): ");
        String releaseDate = scanner.nextLine().trim();

        System.out.print("🎼 Genre: ");
        String genre = scanner.nextLine().trim();

        System.out.print("🏷 Tags (comma separated): ");
        List<String> tags = List.of(scanner.nextLine().trim().split(","));

        Song newSong = new Song(title, lyrics.toString().trim(), releaseDate, genre, tags, artist.getUsername());
        Database.songs.add(newSong);
        Database.saveSongs();

        System.out.println("\n✅ Song created successfully!");
        System.out.println("Total songs: " + Database.songs.stream()
                .filter(s -> s.getArtistUsername().equals(artist.getUsername()))
                .count());
    }

    private static void createNewAlbum(Account artist, Scanner scanner) {
        System.out.println("\n════════ Create New Album ════════");

        System.out.print("💿 Album Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("📅 Release Date (YYYY-MM-DD): ");
        String releaseDate = scanner.nextLine().trim();

        System.out.print("🎼 Genre: ");
        String genre = scanner.nextLine().trim();

        List<Song> albumSongs = new ArrayList<>();
        System.out.println("\n➕ Add Songs to Album:");

        while (true) {
            System.out.println("\n1. Add existing song");
            System.out.println("2. Create new song");
            System.out.println("3. Finish album");
            System.out.print("Choice: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 3) break;

            if (option == 1) {
                System.out.println("\nYour existing songs:");
                List<Song> artistSongs = Database.songs.stream()
                        .filter(s -> s.getArtistUsername().equals(artist.getUsername()))
                        .collect(Collectors.toList());

                artistSongs.forEach(s -> System.out.println("- " + s.getTitle()));

                System.out.print("\nEnter song title to add: ");
                String songTitle = scanner.nextLine().trim();

                Song existing = artistSongs.stream()
                        .filter(s -> s.getTitle().equalsIgnoreCase(songTitle))
                        .findFirst()
                        .orElse(null);

                if (existing != null) {
                    albumSongs.add(existing);
                    System.out.println("✅ Song added to album!");
                } else {
                    System.out.println("❌ Song not found!");
                }
            }
            else if (option == 2) {
                createNewSong(artist, scanner);
                albumSongs.add(Database.songs.get(Database.songs.size()-1));
            }
        }

        if (!albumSongs.isEmpty()) {
            Album newAlbum = new Album(title, releaseDate, genre, artist.getUsername(), albumSongs);
            Database.albums.add(newAlbum);
            Database.saveAlbums();
            System.out.println("\n✅ Album created with " + albumSongs.size() + " songs!");
        } else {
            System.out.println("❌ Album creation cancelled - no songs added");
        }
    }

    private static void editSongLyrics(Scanner scanner) {
        System.out.println("\n════════ Edit Lyrics ════════");

        System.out.print("Search song by title: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        List<Song> matches = Database.songs.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            System.out.println("❌ No matching songs found!");
            return;
        }

        System.out.println("\nMatching songs:");
        matches.forEach(s -> System.out.printf("- %s (%d views)\n", s.getTitle(), s.getViewCount()));

        System.out.print("\nEnter exact song title to edit: ");
        String exactTitle = scanner.nextLine().trim();

        Song target = matches.stream()
                .filter(s -> s.getTitle().equals(exactTitle))
                .findFirst()
                .orElse(null);

        if (target == null) {
            System.out.println("❌ Song not found!");
            return;
        }

        System.out.println("\nCurrent lyrics:\n" + target.getLyrics());
        System.out.println("\nEnter new lyrics (type 'END' on new line to finish):");

        StringBuilder newLyrics = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            newLyrics.append(line).append("\n");
        }

        target.setLyrics(newLyrics.toString().trim());
        Database.saveSongs();
        System.out.println("✅ Lyrics updated successfully!");
    }

    private static void viewStatistics(Account artist) {
        List<Song> songs = Database.songs.stream()
                .filter(s -> s.getArtistUsername().equals(artist.getUsername()))
                .collect(Collectors.toList());

        System.out.println("\n════════ Your Statistics ════════");
        System.out.println("🎵 Total Songs: " + songs.size());

        int totalViews = songs.stream()
                .mapToInt(Song::getViewCount)
                .sum();
        System.out.println("👁️ Total Views: " + totalViews);

        if (!songs.isEmpty()) {
            Song mostPopular = songs.stream()
                    .max((s1, s2) -> Integer.compare(s1.getViewCount(), s2.getViewCount()))
                    .get();

            System.out.println("\n🔥 Most Popular Song:");
            System.out.println("- " + mostPopular.getTitle() + " (" + mostPopular.getViewCount() + " views)");
        }

        System.out.println("\n💿 Albums: " + Database.albums.stream()
                .filter(a -> a.getArtistUsername().equals(artist.getUsername()))
                .count());
    }

    private static void reviewEditRequests(Account artist, Scanner scanner) {
        List<Song> songsWithRequests = Database.songs.stream()
                .filter(s -> s.getArtistUsername().equals(artist.getUsername()) &&
                        !s.getEditRequests().isEmpty())
                .collect(Collectors.toList());

        if (songsWithRequests.isEmpty()) {
            System.out.println("\n✉️ No pending edit requests");
            return;
        }

        System.out.println("\n════════ Pending Edit Requests ════════");

        for (Song song : songsWithRequests) {
            System.out.println("\n🎵 Song: " + song.getTitle());
            System.out.println("Current lyrics:\n" + song.getLyrics());

            for (EditRequest request : song.getEditRequests()) {
                System.out.println("\n✏️ Request from: " + request.getUsername());
                System.out.println("Suggested edit:\n" + request.getSuggestedLyrics());

                System.out.print("\n1. Approve\n2. Reject\n3. Skip\nChoice: ");
                int decision = scanner.nextInt();
                scanner.nextLine();

                if (decision == 1) {
                    song.setLyrics(request.getSuggestedLyrics());
                    song.getEditRequests().remove(request);
                    Database.saveSongs();
                    System.out.println("✅ Changes approved!");
                    break;
                } else if (decision == 2) {
                    song.getEditRequests().remove(request);
                    Database.saveSongs();
                    System.out.println("❌ Request rejected");
                    break;
                }
            }
        }
    }

    private static void showProfile(Account artist, Scanner scanner) {
        System.out.println("\n════════ Artist Profile ════════");
        System.out.println("👤 Name: " + artist.getName());
        System.out.println("📛 Username: @" + artist.getUsername());
        System.out.println("📧 Email: " + artist.getEmail());

        System.out.println("\n📊 Statistics:");
        viewStatistics(artist);

        System.out.println("\n1. Edit Profile");
        System.out.println("2. Back to Menu");
        System.out.print("Choice: ");

        if (scanner.nextLine().equals("1")) {
            editProfile(artist, scanner);
        }
    }

    private static void editProfile(Account artist, Scanner scanner) {
        System.out.println("\n════════ Edit Profile ════════");

        System.out.print("New name (" + artist.getName() + "): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) artist.setName(newName);

        System.out.print("New email (" + artist.getEmail() + "): ");
        String newEmail = scanner.nextLine().trim();
        if (!newEmail.isEmpty()) artist.setEmail(newEmail);

        System.out.print("Change password? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("New password: ");
            artist.setPassword(scanner.nextLine().trim());
        }

        Database.saveAccounts();
        System.out.println("✅ Profile updated successfully!");
    }
}
