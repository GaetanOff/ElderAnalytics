package com.gaetandev.elderanalytics;

import com.gaetandev.elderanalytics.command.AnalyticsCommand;
import com.gaetandev.elderanalytics.listener.LoginListener;
import com.gaetandev.elderanalytics.manager.ManagerHandler;
import net.md_5.bungee.api.plugin.Plugin;

public class ElderAnalytics extends Plugin {
    private ManagerHandler managerHandler;

    @Override
    public void onEnable() {
        this.managerHandler = new ManagerHandler(this);

        this.registerCommands();
        this.registerListener();
    }

    @Override
    public void onDisable() {
        this.managerHandler.unload();
    }

    private void registerCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new AnalyticsCommand(this));
    }

    private void registerListener() {
        new LoginListener(this);
    }

    public ManagerHandler getManagerHandler() {
        return this.managerHandler;
    }
}