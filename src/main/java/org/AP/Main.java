package org.AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);  

        Database.loadAccounts();
        Database.loadSongs();
        Database.loadAlbums();
        Database.migrateOldComments();
        System.out.println("Welcome to Genius!");
        MainMenu.show(scanner);
    }
}

