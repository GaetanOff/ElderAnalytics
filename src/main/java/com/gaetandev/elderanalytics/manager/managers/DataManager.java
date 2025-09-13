package com.gaetandev.elderanalytics.manager.managers;

import com.gaetandev.elderanalytics.data.CustomAdress;
import com.gaetandev.elderanalytics.manager.Manager;
import com.gaetandev.elderanalytics.manager.ManagerHandler;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager extends Manager {
    private Map<String, CustomAdress> data;
    public DataManager(ManagerHandler handler) {
        super(handler);

        this.data = Maps.newConcurrentMap();
    }

    public void addData(String username, String hostname) {
        if (this.data.containsKey(hostname)) {
            CustomAdress customAdress = this.data.get(hostname);
            List<String> users = customAdress.getUsers();
            if (!users.contains(username)) {
                users.add(username);
                customAdress.addDayUser();
            }
        } else {
            List<String> users = new ArrayList<>();
            users.add(username);
            this.data.put(hostname, new CustomAdress(hostname, users, 1));
        }
    }

    public void resetAllDayUser() {
        this.data.forEach((hostname, customAdress) -> customAdress.resetDayUser());

        this.handler.getThreadManager().getThreadPool().execute(() -> this.handler.getSaveManager().save());
    }

    public Map<String, CustomAdress> getData() {
        return data;
    }

    public void setData(Map<String, CustomAdress> data) {
        this.data = data;
    }
}
