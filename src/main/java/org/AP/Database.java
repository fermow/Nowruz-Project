package org.AP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    public static List<Account> accounts = new ArrayList<>();
    public static List<UnverifiedArtist> pendingArtists = new ArrayList<>();
    public static List<Song> songs = new ArrayList<>();
    public static List<Album> albums = new ArrayList<>();
    private static List<Artist> artists;

    @SuppressWarnings("unchecked")
    public static void loadAccounts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("accounts.dat"))) {
            accounts = (List<Account>) in.readObject();
            System.out.println("✅ Accounts loaded. Count: " + accounts.size());
        } catch (FileNotFoundException e) {
            System.out.println("❌ No accounts file found. Creating new list.");
            accounts = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading accounts. Creating new list.");
            e.printStackTrace();
            accounts = new ArrayList<>();
        }
        checkAdminExists();
    }
        public static List<Artist> getArtists() {
            return artists;
        }


    
    public static void checkAdminExists() {
        boolean adminExists = accounts.stream()
                .anyMatch(account -> account.getRole() == Role.ADMIN);

        if (!adminExists) {
            Account admin = new Account("Admin", 30, "admin@example.com", "admin", "admin123", Role.ADMIN);
            accounts.add(admin);
            saveAccounts();
            System.out.println("✅ Default admin account created.");
        }
    }

   
    public static void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) {
            out.writeObject(accounts);
            System.out.println("✅ Accounts have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save accounts to file.");
        }
        System.out.println("Saving accounts. Current count: " + accounts.size());
        for (Account acc : accounts) {
            System.out.println(" - " + acc.getUsername());
        }

    }


    public static Account getAccountByUsername(String username) {
        System.out.println("Searching for username: " + username);
        System.out.println("Current accounts count: " + accounts.size());

        for (Account account : accounts) {
            System.out.println("Checking account: " + account.getUsername());
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
   
    public static void loadPendingArtists() {
        File file = new File("pending_artists.dat");
        if (!file.exists()) {
            System.out.println("❌ No pending artists found. Creating a new file...");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            pendingArtists = (List<UnverifiedArtist>) in.readObject();
            System.out.println("✅ Pending artists have been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to load pending artists from file.");
        }
    }

    
    @SuppressWarnings("unchecked")
    public static void loadSongs() {
        File file = new File("songs.dat");
        if (!file.exists()) {
            System.out.println("❌ No songs found. Creating a new file...");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            songs = (List<Song>) in.readObject();
            System.out.println("✅ Songs have been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to load songs from file.");
        }
    }

   
    @SuppressWarnings("unchecked")
    public static void loadAlbums() {
        File file = new File("albums.dat");
        if (!file.exists()) {
            System.out.println("❌ No albums found. Creating a new file...");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            albums = (List<Album>) in.readObject();
            System.out.println("✅ Albums have been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to load albums from file.");
        }
    }

  
    public static void savePendingArtists() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pending_artists.dat"))) {
            out.writeObject(pendingArtists);
            System.out.println("✅ Pending artists have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save pending artists to file.");
        }
    }

   
    public static void saveSongs() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("songs.dat"))) {
            out.writeObject(songs);
            System.out.println("✅ Songs have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save songs to file.");
        }
    }

    
    public static void saveAlbums() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("albums.dat"))) {
            out.writeObject(albums);
            System.out.println("✅ Albums have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save albums to file.");
        }
    }

    
    public static void saveAccount(Account newAccount) {
        accounts.add(newAccount);
        saveAccounts();  
    }

   
    public static void addApprovedArtistToAccounts(Artist artist) {
       
        for (Account account : accounts) {
            if (account.getUsername().equals(artist.getUsername())) {
                System.out.println("❌ Artist with this username already exists.");
                return;
            }
        }
        accounts.add(artist); 
        saveAccounts(); 
        System.out.println("✅ Artist added to the list.");
    }

   
    public static void removePendingArtist(UnverifiedArtist artist) {
        pendingArtists.remove(artist);  
        savePendingArtists();  
    }

    
    public static List<Account> getApprovedArtists() {
        return accounts.stream()
                .filter(account -> account.getRole() == Role.ARTIST)
                .collect(Collectors.toList());
    }

    
    public static void addPendingArtist(UnverifiedArtist artist) {
        pendingArtists.add(artist);  
        savePendingArtists();        
        System.out.println("✅ Artist added to the pending artists list.");
    }

    
    public static List<Account> getUsers() {
        return accounts.stream()
                .filter(account -> account.getRole() == Role.USER)
                .collect(Collectors.toList());
    }

    
    public static List<UnverifiedArtist> getPendingArtists() {
        return pendingArtists;
    }
    public static void migrateOldComments() {
        for (Song song : songs) {
            if (song.getComments() != null) {
                List<Comment> migratedComments = new ArrayList<>();
                for (Object item : song.getComments()) {
                    if (item instanceof String) {
                        migratedComments.add(new Comment("MigratedUser", (String) item));
                    } else if (item instanceof Comment) {
                        migratedComments.add((Comment) item);
                    }
                }
                song.getComments().clear();
                song.getComments().addAll(migratedComments);
            }
        }
        saveSongs();
    }

    public static List<Song> getSongs() {
        return songs;
    }
    public static List<Album> getAlbums() {
        return albums;
    }
}
