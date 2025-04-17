package org.AP;

import java.util.Scanner;

public class LoginMenu {

    public static Account signIn(Scanner scanner) {
        System.out.println("\n🔐 Log In 🔐");

        for (int i = 0; i < 3; i++) {
            System.out.print("👤 Username: ");
            String username = scanner.nextLine();

            System.out.print("🔒 Password: ");
            String password = scanner.nextLine();

            Account account = Database.getAccountByUsername(username);
            if (account != null) {
                if (account.checkPassword(password)) {
                    System.out.println("✅ Login successful! Welcome, " + account.getUsername() + " 🎉");

                    // Check if it's a pending artist
                    if (account.getRole() == Role.ARTIST &&
                            Database.getPendingArtists().stream()
                                    .anyMatch(a -> a.getUsername().equals(account.getUsername()))) {
                        System.out.println("❌ Your artist account is still pending approval by the admin.");
                        return null;
                    }

                    return account;
                } else {
                    System.out.println("❌ Invalid password. Try again.");
                }
            } else {
                System.out.println("❌ User not found. Try again.");
            }
        }

        System.out.println("⛔ Too many failed attempts. Returning to main menu...");
        return null;
    }
    public static Account show(Scanner scanner) {
        return signIn(scanner);
    }
}

