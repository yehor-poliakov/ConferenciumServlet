package org.poliakov.conferencium.model.user;

import org.mindrot.jbcrypt.BCrypt;

public class UserBuilder {
    private static final String SALT = BCrypt.gensalt();
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
//        this.password = BCrypt.hashpw(password, SALT);
        this.password = password;
        return this;
    }

    public UserBuilder setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public User build() {
        return new User(id, firstName, lastName, email, password, role);
    }
}
