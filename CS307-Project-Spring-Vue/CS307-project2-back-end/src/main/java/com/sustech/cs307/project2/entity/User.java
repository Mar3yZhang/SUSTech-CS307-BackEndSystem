package com.sustech.cs307.project2.entity;




public class User {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private int role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username=" + username +
                ", password=" + password +
                ", role=" + role +
                "}";
    }
}
