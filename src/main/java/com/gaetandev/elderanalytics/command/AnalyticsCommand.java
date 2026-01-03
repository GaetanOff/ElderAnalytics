package com.gaetandev.elderanalytics.command;

import com.gaetandev.elderanalytics.ElderAnalytics;
import com.gaetandev.elderanalytics.manager.managers.ConfigManager;
import com.gaetandev.elderanalytics.manager.managers.DataManager;
import com.gaetandev.elderanalytics.manager.managers.ThreadManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AnalyticsCommand extends Command {
    private final ConfigManager configManager;
    private final DataManager dataManager;
    private final ThreadManager threadManager;

    public AnalyticsCommand(final ElderAnalytics elderAnalytics) {
        super("analytics");

        this.configManager = elderAnalytics.getManagerHandler().getConfigManager();
        this.dataManager = elderAnalytics.getManagerHandler().getDataManager();
        this.threadManager = elderAnalytics.getManagerHandler().getThreadManager();
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {

        if (args.length == 0) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Utilisation: /analytics info <hostname>"));
            sender.sendMessage(new TextComponent(ChatColor.RED + "Utilisation: /analytics profile"));
        } else if (args[0].equalsIgnoreCase("info")) {
            this.threadManager.getThreadPool().execute(() -> {
                if (!sender.hasPermission("elderanalytics.admin")) {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission."));
                    return;
                }
                String hostname = args[1];
                String domain = this.configManager.getDomain();

                if (!hostname.endsWith("." + domain)) {
                    hostname += "." + domain;
                }

                this.sendInformation(sender, hostname);
            });
        }else if (args[0].equalsIgnoreCase("profile")) {
            this.threadManager.getThreadPool().execute(() -> {
                if (!sender.hasPermission("elderanalytics.profile")) {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission."));
                    return;
                }
                this.sendInformation(sender, sender.getName().toLowerCase() + "." + this.configManager.getDomain());
            });
        } else if (args[0].equalsIgnoreCase("resetalldayyserMDRRRRRRR")) {
            this.threadManager.getThreadPool().execute(() -> {
                if (!sender.hasPermission("elderanalytics.resetalldayyserMDRRRRRRR")) {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission."));
                    return;
                }

                this.dataManager.resetAllDayUser();
                sender.sendMessage(new TextComponent(ChatColor.GREEN + "Les données ont été réinitialisées."));
            });
        }
    }

    private void sendInformation(final CommandSender sender, final String hostname) {
        if (dataManager.getData().get(hostname) == null) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Aucune donnée trouvée pour ce serveur."));
            return;
        }

        final int totalPlayers = dataManager.getData().get(hostname).getTotalUsers();
        final int dayPlayers = dataManager.getData().get(hostname).getDayUsers();

        //separator
        sender.sendMessage(new TextComponent("§7§m-------------------------------"));
        sender.sendMessage(new TextComponent("§7Informations pour §a" + hostname));
        sender.sendMessage(new TextComponent(""));
        sender.sendMessage(new TextComponent("§7Joueurs total unique: §a" + totalPlayers));
        sender.sendMessage(new TextComponent("§7Joueurs aujourd'hui unique: §a" + dayPlayers));
        sender.sendMessage(new TextComponent("§7§m-------------------------------"));
    }
}
