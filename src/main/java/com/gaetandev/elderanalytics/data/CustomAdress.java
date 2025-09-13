package com.gaetandev.elderanalytics.data;

import java.util.List;

public class CustomAdress {
    private final String address;
    private final List<String> users;
    private int dayUsers;

    public CustomAdress(String address, List<String> users, int dayUsers) {
        this.address = address;
        this.users = users;
        this.dayUsers = dayUsers;
    }

    public void addDayUser() {
        this.dayUsers++;
    }

    public void resetDayUser() {
        this.dayUsers = 0;
    }

    public String getAddress() {
        return this.address;
    }

    public List<String> getUsers() {
        return this.users;
    }

    public int getDayUsers() {
        return this.dayUsers;
    }

    public int getTotalUsers() {
        return this.users.size();
    }
}
