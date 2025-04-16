package org.AP;
public class UnverifiedArtist extends Account {
    private boolean isVerified;

    public UnverifiedArtist(String name, int age, String email, String username, String password) {
        super(name, age, email, username,password ,Role.ARTIST);  
        this.isVerified = false; 
    }

    
    public void verify() {
        this.isVerified = true; 
    }

    public boolean isVerified() {
        return isVerified;
    }
}
