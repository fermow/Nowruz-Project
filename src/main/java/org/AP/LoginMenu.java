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
            if (account != null && account.checkPassword(password)) {
                System.out.println("✅ Login successful! Welcome, " + account.getUsername() + " 🎉");

                // اگر آرتیست بود، بررسی می‌کنیم که تأیید شده یا نه
                if (account.getRole() == Role.ARTIST) {
                    boolean isPending = Database.getPendingArtists().stream()
                            .anyMatch(a -> a.getUsername().equals(account.getUsername()));

                    if (isPending) {
                        System.out.println("❌ Your artist account is still pending approval by the admin.");
                        return null; // اجازه ورود نمی‌ده
                    }
                }

                return account; // برگشت به MainMenu برای هدایت به منوی مربوطه
            } else {
                System.out.println("❌ Invalid username or password. Try again.");
            }
        }

        System.out.println("⛔ Too many failed attempts. Returning to main menu...");
        return null;
    }

    public static Account show(Scanner scanner) {
        return signIn(scanner);
    }
}

