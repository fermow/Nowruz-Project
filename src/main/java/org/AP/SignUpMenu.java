package org.AP;

import java.util.Scanner;
import java.util.regex.Pattern;

public class SignUpMenu {
    private static final int MIN_AGE = 13;
    private static final int MAX_AGE = 100;

    public static void show(Scanner scanner) {
        System.out.println("\n════════ CREATE ACCOUNT ════════");
        System.out.println("Join the Genius community!\n");

        
        String name = getValidInput(scanner, "Full Name", "^[a-zA-Z ]{3,50}$",
                "❌ Invalid name! Use 3-50 letters and spaces only");

        int age = Integer.parseInt(getValidInput(scanner, "Age",
                "^[1-9]\\d{1,2}$",
                "❌ Invalid age! Enter a number between " + MIN_AGE + "-" + MAX_AGE));

        if(age < MIN_AGE || age > MAX_AGE) {
            System.out.println("❌ You must be between " + MIN_AGE + "-" + MAX_AGE + " years old");
            return;
        }

        String email = getValidInput(scanner, "Email",
                "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
                "❌ Invalid email format (e.g. user@example.com)");

        String username = getValidInput(scanner, "Username",
                "^[a-zA-Z0-9_]{4,20}$",
                "❌ 4-20 characters (letters, numbers, _ only)");

        String password = getValidInput(scanner, "Password",
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
                "❌ Minimum 8 chars with at least 1 letter and 1 number");

        
        System.out.println("\n════════ ACCOUNT TYPE ════════");
        System.out.println("1. 🧑 Regular User");
        System.out.println("2. 🎤 Artist Account (requires verification)");
        String roleChoice = getValidInput(scanner, "Choice [1-2]", "^[1-2]$",
                "❌ Please enter 1 or 2");

    
        Account newAccount = createAccount(roleChoice, name, age, email, username, password);

        if(newAccount != null) {
            showSuccessMessage(newAccount);
        }
    }

    private static String getValidInput(Scanner scanner, String fieldName,
                                        String regex, String errorMessage) {
        while(true) {
            System.out.print(fieldName + ": ");
            String input = scanner.nextLine().trim();

            if(Pattern.matches(regex, input)) {
                return input;
            }

            System.out.println(errorMessage);
            if(fieldName.equals("Age")) {
                System.out.println("(Must be " + MIN_AGE + "-" + MAX_AGE + " years)");
            }
        }
    }

    private static Account createAccount(String roleChoice, String name, int age,
                                         String email, String username, String password) {
        try {
            if(roleChoice.equals("1")) {
                return new User(name, age, email, username, password);
            } else {
                UnverifiedArtist artist = new UnverifiedArtist(name, age, email, username, password);
                Database.addPendingArtist(artist);
                return artist;
            }
        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            return null;
        }
    }

    private static void showSuccessMessage(Account account) {
        System.out.println("\n════════ REGISTRATION SUCCESS ════════");
        System.out.println("🎉 Welcome to Genius, " + account.getName() + "!");
        System.out.println("👤 Username: @" + account.getUsername());
        System.out.println("📧 Email: " + account.getEmail());
        System.out.println("🔑 Role: " + (account instanceof User ? "Regular User" : "Artist (Pending Verification)"));

        if(account instanceof UnverifiedArtist) {
            System.out.println("\nℹ️ Your artist account will be reviewed within 24 hours.");
            System.out.println("You'll receive a notification once verified.");
        }

        System.out.println("\nYou can now log in and start using Genius!");
    }
}
