package com.example.login.db;

public class FailedAttempt {
    private String username;
    private String password;
    private String timestamp;
    public FailedAttempt(String username, String password, String timestamp) {
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
