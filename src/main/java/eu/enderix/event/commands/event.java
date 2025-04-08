package eu.enderix.event.commands;

import eu.enderix.event.Core;
import eu.enderix.event.Utils.ConfigUtil;
import eu.enderix.event.Utils.Event.EventUtil;
import eu.enderix.event.Utils.SpawnUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class event implements CommandExecutor {

    private final Core plugin;
    private final ConfigUtil configUtil;
    private final SpawnUtil spawnUtil;
    private final List<EventUtil> events;

    public event(List<EventUtil> events, SpawnUtil spawnUtil, Core plugin) {
        this.plugin = plugin;
        this.events = events;
        this.spawnUtil = spawnUtil;
        this.configUtil = new ConfigUtil(plugin, "event-config.yml");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPouze hráči mohou používat tento příkaz!");
            return true;
        }

        if (args.length < 1) {
            sendUsage(player);
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "create", "delete" -> handleSpawnManagement(player, args);
            case "start", "stop" -> handleEventState(player, args);
            case "join" -> handleJoin(player, args);
            case "kick" -> handleKick(player, args);
            default -> sendUsage(player);
        }

        return true;
    }


    private void handleSpawnManagement(Player player, String[] args) {
        if (!player.hasPermission("event.admin")) {
            player.sendMessage(getMessage("no-permissions"));
            return;
        }

        if (args.length != 2) {
            sendUsage(player);
            return;
        }

        EventUtil event = getEvent(args[1]);
        if (event == null) {
            player.sendMessage(getMessage("wrong-event-number"));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "create" -> {
                if (event.exists()) {
                    player.sendMessage(getMessage("event-already-exists").replace("%event_number%", args[1]));
                    return;
                }
                event.set(player.getLocation());
                player.sendMessage(getMessage("event-spawn-created").replace("%event_number%", args[1]));
            }
            case "delete" -> {
                if (!event.exists()) {
                    player.sendMessage(getMessage("event-does-not-exist").replace("%event_number%", args[1]));
                    return;
                }
                event.deleteSpawnLocation();
                player.sendMessage(getMessage("event-spawn-deleted").replace("%event_number%", args[1]));
            }
        }
    }


    private void handleEventState(Player player, String[] args) {
        if (!player.hasPermission("event.admin")) {
            player.sendMessage(getMessage("no-permissions"));
            return;
        }

        if (args.length != 2) {
            sendUsage(player);
            return;
        }

        EventUtil event = getEvent(args[1]);
        if (event == null) {
            player.sendMessage(getMessage("wrong-event-number"));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> {
                if (!event.exists()) {
                    player.sendMessage(getMessage("event-does-not-exist").replace("%event-number%", args[1]));
                    return;
                }
                if (event.isActive()) {
                    player.sendMessage(getMessage("event-already-active").replace("%event-number%", args[1]));
                    return;
                }
                event.startEvent();
                player.sendMessage(getMessage("event-started"));
                Bukkit.broadcastMessage(getMessage("event-started-broadcast").replace("%event_number%", args[1]));
            }
            case "stop" -> {
                if (!event.exists()) {
                    player.sendMessage(getMessage("event-does-not-exist").replace("%event-number%", args[1]));
                    return;
                }
                if (!event.isActive()) {
                    player.sendMessage(getMessage("event-already-inactive").replace("%event-number%", args[1]));
                    return;
                }
                event.stopEvent();
                player.sendMessage(getMessage("event-stopped"));
                Bukkit.broadcastMessage(getMessage("event-stopped-broadcast").replace("%event_number%", args[1]));
            }
        }
    }


    private void handleJoin(Player player, String[] args) {
        if (args.length != 2) {
            sendUsage(player);
            return;
        }

        EventUtil event = getEvent(args[1]);
        if (event == null) {
            player.sendMessage(getMessage("wrong-event-number"));
            return;
        }

        if (!event.exists()) {
            player.sendMessage(getMessage("event-does-not-exist").replace("%event_number%", args[1]));
            return;
        }

        if (!event.isActive()) {
            player.sendMessage(getMessage("event-not-active").replace("%event_number%", args[1]));
            return;
        }

        event.teleport(player);
        player.sendMessage(getMessage("event-joined").replace("%event_number%", args[1]));
    }


    private void handleKick(Player sender, String[] args) {
        if (!sender.hasPermission("event.admin")) {
            sender.sendMessage(getMessage("no-permissions"));
            return;
        }

        if (args.length != 2) {
            sender.sendMessage(getMessage("kick-usage"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(getMessage("target-not-found").replace("%player_name%", args[1]));
            return;
        }

        if (spawnUtil == null) {
            sender.sendMessage("§cChyba: SpawnUtil není inicializován!");
            return;
        }

        spawnUtil.teleport(targetPlayer);
        targetPlayer.sendMessage(getMessage("kicked-message"));
        sender.sendMessage(getMessage("kick-player-message").replace("%player_name%", args[1]));
    }


    private EventUtil getEvent(String eventNumber) {
        try {
            int index = Integer.parseInt(eventNumber) - 1;
            if (index >= 0 && index < events.size()) {
                return events.get(index);
            }
        } catch (NumberFormatException ignored) {}
        return null;
    }


    private void sendUsage(Player player) {
        player.sendMessage(getMessage("event-usage"));
    }


    public String getMessage(String path) {
        if (!configUtil.getFile().exists()) {
            plugin.saveResource("event-config.yml", false);
        }

        String message = configUtil.getConfig().getString("messages." + path);
        if (message == null) {
            return "§cZpráva nebyla nalezena!";
        }
        return message.replaceAll("&", "§");
    }
}
