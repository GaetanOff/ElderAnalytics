package com.gaetandev.elderanalytics.manager.managers;

import com.gaetandev.elderanalytics.data.CustomAdress;
import com.gaetandev.elderanalytics.manager.Manager;
import com.gaetandev.elderanalytics.manager.ManagerHandler;
import com.gaetandev.elderanalytics.runnable.SaveDataRunnable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SaveManager extends Manager {
    private DataManager dataManager;
    public SaveManager(ManagerHandler handler) {
        super(handler);

        this.dataManager = handler.getDataManager();

        this.load();

        this.handler.getThreadManager().getThreadRunnablePool()
                .scheduleAtFixedRate(
                        new SaveDataRunnable(this.handler.getElderAnalytics()),
                        0, 30, TimeUnit.MINUTES
                );
    }

    public void save() {
        Map<String, Map<String, Object>> dataToSave = new HashMap<>();

        for (Map.Entry<String, CustomAdress> entry : this.dataManager.getData().entrySet()) {
            Map<String, Object> customAdressMap = new HashMap<>();
            customAdressMap.put("address", entry.getValue().getAddress());
            customAdressMap.put("users", entry.getValue().getUsers());
            customAdressMap.put("dayUsers", entry.getValue().getDayUsers());
            dataToSave.put(entry.getKey(), customAdressMap);
        }

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(this.getHandler().getElderAnalytics().getDataFolder() + "/data.yml")) {
            yaml.dump(dataToSave, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        //create directory
        if (!this.getHandler().getElderAnalytics().getDataFolder().exists()) {
            this.getHandler().getElderAnalytics().getDataFolder().mkdir();
        }

        //check if the file exists
        try {
            new File(this.getHandler().getElderAnalytics().getDataFolder() + "/data.yml").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Yaml yaml = new Yaml();
        try (FileReader reader = new FileReader(this.getHandler().getElderAnalytics().getDataFolder() + "/data.yml")) {
            Map<String, Map<String, Object>> loadedData = yaml.load(reader);

            if (loadedData != null) {
                this.dataManager.getData().clear();
                for (Map.Entry<String, Map<String, Object>> entry : loadedData.entrySet()) {
                    String address = (String) entry.getValue().get("address");
                    List<String> users = (List<String>) entry.getValue().get("users");
                    int dayUsers = (int) entry.getValue().get("dayUsers");
                    this.dataManager.getData().put(entry.getKey(), new CustomAdress(address, users, dayUsers));
                }
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
