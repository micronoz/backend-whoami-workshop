package com.example.whoami;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;

    protected User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, userName='%userName']", id, userName);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}