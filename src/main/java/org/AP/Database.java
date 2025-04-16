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
        File file = new File("accounts.dat");
        if (!file.exists()) {
            System.out.println("❌ No accounts found. Creating a new file...");
            accounts = new ArrayList<>();
        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (List<Account>) in.readObject();
                System.out.println("✅ Accounts have been loaded from file.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("❌ Failed to load accounts from file.");
            }
        }

        checkAdminExists(); // این باید همیشه اجرا بشه، چه فایل وجود داشته باشه چه نه
    }


          // فرض بر این است که شما یک لیست از هنرمندان دارید

        // متد برای برگرداندن لیست هنرمندان
        public static List<Artist> getArtists() {
            return artists;
        }



    // چک کردن وجود ادمین در لیست اکانت‌ها
    public static void checkAdminExists() {
        boolean adminExists = accounts.stream()
                .anyMatch(account -> account.getRole() == Role.ADMIN);

        if (!adminExists) {
            // ایجاد ادمین پیش‌فرض
            Account admin = new Account("Admin", 30, "admin@example.com", "admin", "admin123", Role.ADMIN);

            accounts.add(admin);
            saveAccounts();
            System.out.println("✅ Default admin account created.");
        }
    }

    // ذخیره اکانت‌ها به فایل
    public static void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) {
            out.writeObject(accounts);
            System.out.println("✅ Accounts have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save accounts to file.");
        }
    }

    // جستجوی اکانت بر اساس نام کاربری
    public static Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                // اگر اکانت هنرمند است و هنوز تایید نشده است
                if (account.getRole() == Role.ARTIST) {
                    for (UnverifiedArtist artist : pendingArtists) {
                        if (artist.getUsername().equals(username)) {
                            System.out.println("❌ This artist is not approved yet.");
                            return null;  // هنرمند تایید نشده اجازه ورود ندارد
                        }
                    }
                }
                return account;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // بارگذاری هنرمندان در حال انتظار تایید از فایل
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

    // بارگذاری آهنگ‌ها از فایل
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

    // بارگذاری آلبوم‌ها از فایل
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

    // ذخیره هنرمندان در حال انتظار به فایل
    public static void savePendingArtists() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pending_artists.dat"))) {
            out.writeObject(pendingArtists);
            System.out.println("✅ Pending artists have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save pending artists to file.");
        }
    }

    // ذخیره آهنگ‌ها به فایل
    public static void saveSongs() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("songs.dat"))) {
            out.writeObject(songs);
            System.out.println("✅ Songs have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save songs to file.");
        }
    }

    // ذخیره آلبوم‌ها به فایل
    public static void saveAlbums() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("albums.dat"))) {
            out.writeObject(albums);
            System.out.println("✅ Albums have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to save albums to file.");
        }
    }

    // ذخیره حساب جدید
    public static void saveAccount(Account newAccount) {
        accounts.add(newAccount);  // اضافه کردن اکانت جدید به لیست
        saveAccounts();  // ذخیره‌سازی دوباره لیست به فایل
    }

    // ذخیره هنرمندان تایید شده به لیست اکانت‌ها
    public static void addApprovedArtistToAccounts(Artist artist) {
        // بررسی اینکه آیا هنرمند با همین یوزرنیم قبلاً وجود دارد یا نه
        for (Account account : accounts) {
            if (account.getUsername().equals(artist.getUsername())) {
                System.out.println("❌ Artist with this username already exists.");
                return;
            }
        }
        accounts.add(artist);  // اضافه کردن هنرمند جدید به لیست
        saveAccounts();  // ذخیره‌سازی دوباره لیست به فایل
        System.out.println("✅ Artist added to the list.");
    }

    // حذف هنرمند تایید نشده از لیست pending
    public static void removePendingArtist(UnverifiedArtist artist) {
        pendingArtists.remove(artist);  // حذف هنرمند تایید نشده از لیست pending
        savePendingArtists();  // ذخیره‌سازی تغییرات لیست pending
    }

    // دریافت هنرمندان تایید شده
    public static List<Account> getApprovedArtists() {
        return accounts.stream()
                .filter(account -> account.getRole() == Role.ARTIST)
                .collect(Collectors.toList());
    }

    // اضافه کردن هنرمند تایید نشده به لیست pending
    public static void addPendingArtist(UnverifiedArtist artist) {
        pendingArtists.add(artist);  // اضافه کردن هنرمند جدید به لیست
        savePendingArtists();        // ذخیره‌سازی دوباره لیست به فایل
        System.out.println("✅ Artist added to the pending artists list.");
    }

    // دریافت کاربران ثبت‌نام‌شده
    public static List<Account> getUsers() {
        return accounts.stream()
                .filter(account -> account.getRole() == Role.USER)
                .collect(Collectors.toList());
    }

    // دریافت هنرمندان در حال انتظار تایید
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


    // دریافت آهنگ‌ها
    public static List<Song> getSongs() {
        return songs;
    }

    // دریافت آلبوم‌ها
    public static List<Album> getAlbums() {
        return albums;
    }
}
