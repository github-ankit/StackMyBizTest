package com.liveup.stackmybiztest;

public class UserHelperClass {

    String email;
    String name;
    String status;
    String userLat, userLong;
    CharSequence date;


    public UserHelperClass() {
    }



    public UserHelperClass(String status, String email, String name, CharSequence date) {
       this.status = status;
        this.email = email;
        this.name = name;
        this.date = date;
    }

    public UserHelperClass(String email, String name, String userLat, String userLong) {
        this.email = email;
        this.name = name;
        this.userLat = userLat;
        this.userLong = userLong;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    public CharSequence getDate() {
        return date;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

