package com.example.whoami;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String userName;
    private Boolean isAdmin;

    protected User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, userName='%s', isAdmin='%s']", id, userName, isAdmin.toString());
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}