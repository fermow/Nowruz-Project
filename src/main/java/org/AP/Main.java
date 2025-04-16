package org.AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // بارگذاری داده‌ها از فایل
        Scanner scanner = new Scanner(System.in);  // ایجاد شیء Scanner در اینجا

        Database.loadAccounts();
        Database.loadSongs();
        Database.loadAlbums();
        Database.migrateOldComments();


        // شروع برنامه
        System.out.println("Welcome to Genius!");
        MainMenu.show(scanner);
    }
}

