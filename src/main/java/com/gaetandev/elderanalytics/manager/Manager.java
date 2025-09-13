package com.gaetandev.elderanalytics.manager;

public class Manager {
    protected ManagerHandler handler;

    public Manager(final ManagerHandler handler) {
        this.handler = handler;
    }

    public ManagerHandler getHandler() {
        return handler;
    }
}
