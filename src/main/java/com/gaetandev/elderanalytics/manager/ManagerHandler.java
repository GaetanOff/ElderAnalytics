package com.gaetandev.elderanalytics.manager;

import com.gaetandev.elderanalytics.ElderAnalytics;
import com.gaetandev.elderanalytics.manager.managers.DataManager;
import com.gaetandev.elderanalytics.manager.managers.SaveManager;
import com.gaetandev.elderanalytics.manager.managers.ThreadManager;

public class ManagerHandler {
    private ElderAnalytics elderAnalytics;

    private ThreadManager threadManager;
    private DataManager dataManager;
    private SaveManager saveManager;

    public ManagerHandler(ElderAnalytics elderAnalytics) {
        this.elderAnalytics = elderAnalytics;

        // Initialize all managers
        threadManager = new ThreadManager(this);
        dataManager = new DataManager(this);
        saveManager = new SaveManager(this);
    }

    public void unload() {
        // Unload all managers
        this.saveManager.save();
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public ElderAnalytics getElderAnalytics() {
        return elderAnalytics;
    }
}