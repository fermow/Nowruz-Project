package org.AP;

import java.util.Scanner;

public class MainMenu {
    public static void show(Scanner scanner) {
        while (true) {
            System.out.println("\n🎶 Welcome to Genius CLI 🎶");
            System.out.println("\n============================");
            System.out.println("📋 Main Menu");
            System.out.println("1️⃣  Sign In");
            System.out.println("2️⃣  Sign Up");
            System.out.println("0️⃣  Exit");
            System.out.print("👉 Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Account currentAccount = LoginMenu.show(scanner);

                    if (currentAccount != null) {
                        // Check role and route accordingly
                        switch (currentAccount.getRole()) {
                            case USER:
                                UserMenu.show(currentAccount, scanner);
                                break;
                            case ARTIST:
                                // Check if artist is approved
                                if (Database.getApprovedArtists().stream()
                                        .anyMatch(a -> a.getUsername().equals(currentAccount.getUsername()))) {
                                    ArtistMenu.show(currentAccount, scanner);
                                } else {
                                    System.out.println("❌ Your artist account is still pending approval by the admin.");
                                }
                                break;
                            case ADMIN:
                                AdminMenu.show(currentAccount, scanner);
                                break;
                            default:
                                System.out.println("⚠️ Unknown role.");
                        }
                    }
                    break;

                case "2":
                    SignUpMenu.show(scanner);
                    break;

                case "0":
                    System.out.println("👋 Goodbye!");
                    System.exit(0); // Exit the program
                    break;

                default:
                    System.out.println("❗ Invalid option. Please try again.");
            }
        }
    }
}
