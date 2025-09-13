package com.gaetandev.elderanalytics.runnable;

import com.gaetandev.elderanalytics.ElderAnalytics;

public class SaveDataRunnable implements Runnable {
    private ElderAnalytics elderAnalytics;

    public SaveDataRunnable(ElderAnalytics elderAnalytics) {
        this.elderAnalytics = elderAnalytics;
    }

    @Override
    public void run() {
        this.elderAnalytics.getManagerHandler().getSaveManager().save();
    }
}
