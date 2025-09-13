package com.gaetandev.elderanalytics.listener;

import com.gaetandev.elderanalytics.ElderAnalytics;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
    private final ElderAnalytics elderAnalytics;

    public LoginListener(ElderAnalytics elderAnalytics) {
        this.elderAnalytics = elderAnalytics;

        this.elderAnalytics.getProxy().getPluginManager().registerListener(this.elderAnalytics, this);
    }

    @EventHandler
    public void onJoin(LoginEvent event) {
        final String username = event.getConnection().getName();
        final String hostname = event.getConnection().getVirtualHost().getHostName().toLowerCase();

        this.elderAnalytics.getManagerHandler().getDataManager().addData(username, hostname);
    }
}
