package com.example.giuaki.model;

public class Users {
    private int id;

    private String username,password, phonenumber, email;

    public Users(int id, String username, String password, String phonenumber, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public Users(String username, String password, String phonenumber, String email) {
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
