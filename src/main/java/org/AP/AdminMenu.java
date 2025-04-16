package org.AP;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void show(Account account, Scanner scanner) {
        System.out.println("Welcome to the Admin menu!");

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Pending Artist Requests");
            System.out.println("2. View All Registered Users");
            System.out.println("3. View All Approved Artists");
            System.out.println("4. Log Out");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showPendingArtists(scanner);
                    break;
                case "2":
                    showAllUsers();
                    break;
                case "3":
                    showAllArtists();
                    break;
                case "4":
                    System.out.println("Logged out.");
                    MainMenu.show(scanner); 
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showPendingArtists(Scanner scanner) {
        List<UnverifiedArtist> pending = Database.getPendingArtists();
        if (pending.isEmpty()) {
            System.out.println("There are no pending artist requests.");
            return;
        }

        System.out.println("\n--- Pending Artist Requests ---");
        for (int i = 0; i < pending.size(); i++) {
            UnverifiedArtist artist = pending.get(i);
            System.out.printf("%d. %s (Username: %s)\n", i + 1, artist.getName(), artist.getUsername());
        }

        System.out.print("Enter the number of the artist to approve or reject (0 to cancel): ");
        int selected = -1;
        try {
            selected = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        if (selected > 0 && selected <= pending.size()) {
            UnverifiedArtist selectedArtist = pending.remove(selected - 1);
            Artist approved = new Artist(
                    selectedArtist.getName(),
                    selectedArtist.getAge(),
                    selectedArtist.getEmail(),
                    selectedArtist.getUsername(),
                    selectedArtist.getPassword()
            );
            Database.addApprovedArtistToAccounts(approved);
            Database.removePendingArtist(selectedArtist);
            System.out.println("Artist approved successfully.");
        } else if (selected == 0) {
            System.out.println("Operation cancelled.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private static void showAllUsers() {
        List<Account> users = Database.getUsers();
        if (users.isEmpty()) {
            System.out.println("There are no registered users.");
        } else {
            System.out.println("\n--- Registered Users ---");
            for (Account user : users) {
                System.out.printf("%s (Username: %s)\n", user.getName(), user.getUsername());
            }

        }
    }

    private static void showAllArtists() {
        List<Account> artists = Database.getApprovedArtists();
        if (artists.isEmpty()) {
            System.out.println("There are no approved artists.");
        } else {
            System.out.println("\n--- Approved Artists ---");
            for (Account artist : artists) {
                System.out.printf("%s (Username: %s)\n", artist.getName(), artist.getUsername());
            }
        }
    }
}
