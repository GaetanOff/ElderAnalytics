package com.gaetandev.elderanalytics.manager.managers;

import com.gaetandev.elderanalytics.manager.Manager;
import com.gaetandev.elderanalytics.manager.ManagerHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadManager extends Manager {
    private final ExecutorService threadPool;
    private final ScheduledExecutorService threadRunnablePool;

    public ThreadManager(final ManagerHandler handler) {
        super(handler);

        this.threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.threadRunnablePool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void shutdown() {
        this.threadPool.shutdown();
        this.threadRunnablePool.shutdown();
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    public ScheduledExecutorService getThreadRunnablePool() {
        return this.threadRunnablePool;
    }
}
