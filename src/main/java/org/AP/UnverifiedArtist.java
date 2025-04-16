package org.AP;
public class UnverifiedArtist extends Account {
    private boolean isVerified;

    public UnverifiedArtist(String name, int age, String email, String username, String password) {
        super(name, age, email, username,password ,Role.ARTIST);  // ارسال role به سازنده‌ی Account
        this.isVerified = false;  // پیش‌فرض، هنرمند تایید نشده است
    }

    // متد verify
    public void verify() {
        this.isVerified = true;  // هنرمند تایید شد
    }

    public boolean isVerified() {
        return isVerified;
    }
}
