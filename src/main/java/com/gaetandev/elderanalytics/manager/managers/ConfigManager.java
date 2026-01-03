package com.gaetandev.elderanalytics.manager.managers;

import com.gaetandev.elderanalytics.manager.Manager;
import com.gaetandev.elderanalytics.manager.ManagerHandler;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

public class ConfigManager extends Manager {
    private String domain;

    public ConfigManager(ManagerHandler handler) {
        super(handler);

        this.load();
    }

    private void load() {
        File dataFolder = this.handler.getElderAnalytics().getDataFolder();

        //create directory
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configFile = new File(dataFolder, "config.yml");

        //copy default config if not exists
        if (!configFile.exists()) {
            try (InputStream in = this.handler.getElderAnalytics().getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //load config
        Yaml yaml = new Yaml();
        try (FileReader reader = new FileReader(configFile)) {
            Map<String, Object> config = yaml.load(reader);

            if (config != null) {
                this.domain = (String) config.getOrDefault("domain", "eldercraft.fr");
            } else {
                this.domain = "eldercraft.fr";
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.domain = "eldercraft.fr";
        }

        this.handler.getElderAnalytics().getLogger().info("Domaine configuré: " + this.domain);
    }

    public String getDomain() {
        return domain;
    }
}

