package org.AP;

import java.io.Serial;
import java.io.Serializable;

public class User extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    ;

    public User(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password,Role.USER);
            this.setUsername(username) ;
            this.setAge(age);
            this.setEmail(email);
            this.setPassword(password);
            this.setRole(Role.USER);

    }
    public String toString() {
        return String.format("User: %s (Username: %s)", getName(), getUsername());
    }



}
