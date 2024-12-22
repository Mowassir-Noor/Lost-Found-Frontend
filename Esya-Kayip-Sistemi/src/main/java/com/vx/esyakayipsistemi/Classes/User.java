package com.vx.esyakayipsistemi.Classes;

public class User {
    private String username;
    private String email;
    private String password;

//user sinif  icin constructor
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

  //getter fonksiyonlari
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
