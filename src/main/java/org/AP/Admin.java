package org.AP;


import java.io.Serial;
import java.io.Serializable;

public class Admin extends Account implements Serializable {
    private static final long serialVersionUID = 1L;


    public Admin(String name, int age, String email, String username, String password) {
        super(name, age, email, username,password ,Role.ADMIN);
    }
    @Override
    public String toString() {
        return String.format("Admin: %s (Username: %s)", getName(), getUsername());
    }
}
