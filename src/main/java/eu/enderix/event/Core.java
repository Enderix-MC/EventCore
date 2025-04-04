package eu.enderix.event;

import eu.enderix.event.Utils.ConfigUtil;
import eu.enderix.event.Utils.Event.EventUtil;
import eu.enderix.event.Utils.SpawnUtil;
import eu.enderix.event.events.*;
import eu.enderix.event.Utils.*;
import eu.enderix.event.Utils.Event.*;
import eu.enderix.event.commands.SetSpawn;
import eu.enderix.event.commands.Spawn;
import eu.enderix.event.commands.event;
import eu.enderix.event.events.*;
import eu.enderix.event.scoreboard.main;
import fr.mrmicky.fastboard.FastBoard;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Core extends JavaPlugin implements Listener {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public static Core Instance;

    private LuckPerms luckPerms;

    public static Object getInstance() {
        return null;
    }


    @Override
    public void onLoad(){
        Core instance = this;
    }

    @Override
    public void onEnable() {

        System.out.println("§aEnderix Event core has been enabled!");
        saveDefaultConfig();


        File configFile = new File(getDataFolder(), "event-config.yml");
        if (!configFile.exists()) {
            saveResource("event-config.yml", false); // This will copy event-config.yml from the JAR to the plugin folder if it doesn't exist
        }

        // Load the config
        ConfigUtil eventConfig = new ConfigUtil(this, "event-config.yml");

        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        getServer().getPluginManager().registerEvents(this, this);

        SpawnUtil spawnUtil = new SpawnUtil(this);
        //events


        Bukkit.getPluginManager().registerEvents(new main(), this);
        Bukkit.getPluginManager().registerEvents(new damage(), this);
        Bukkit.getPluginManager().registerEvents(new hunger(), this);
        Bukkit.getPluginManager().registerEvents(new damage(), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawn(), this);
        Bukkit.getPluginManager().registerEvents(new openmenu(), this);
        Bukkit.getPluginManager().registerEvents(new quitmessage(), this);
        Bukkit.getPluginManager().registerEvents(new joinemssage(), this);
        Bukkit.getPluginManager().registerEvents(new join(spawnUtil), this);

        //commands
        // Initialize the list of events
        List<EventUtil> events = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            events.add(new EventUtil(this, String.valueOf(i)));
        }

        // Register the /event command
        getCommand("event").setExecutor(new event(events, new SpawnUtil(this), this));

        // Save the default config if it doesn't exist
        saveDefaultConfig();


    // Optionally, register your event listeners here
        getCommand("ereload").setExecutor(new ReloadCommand());
        getCommand("spawn").setExecutor(new Spawn(spawnUtil));
        getCommand("Setspawn").setExecutor(new SetSpawn(spawnUtil));
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : main.boards.values()) {
                main.updateBoard(board);
            }
        }, 0, 20);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {

                    //p.getWorld().setTime(0L);

                }
            }
        }, 100L, 100L);

    }

    private class ReloadCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (cmd.getName().equalsIgnoreCase("ereload")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.hasPermission("event.reload")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }
                }

                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Event has been reloaded!");
                return true;
            }

            return false;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        final String message = event.getMessage();
        final Player player = event.getPlayer();

        // Get a LuckPerms cached metadata for the player.
        final CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        final String group = metaData.getPrimaryGroup();

        String format = getConfig().getString(getConfig().getString("group-formats." + group) != null ? "group-formats." + group : "chat-format")
                .replace("{prefix}", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                .replace("{suffix}", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                .replace("{prefixes}", metaData.getPrefixes().keySet().stream().map(key -> metaData.getPrefixes().get(key)).collect(Collectors.joining()))
                .replace("{suffixes}", metaData.getSuffixes().keySet().stream().map(key -> metaData.getSuffixes().get(key)).collect(Collectors.joining()))
                .replace("{world}", player.getWorld().getName())
                .replace("{name}", player.getName())
                .replace("{displayname}", player.getDisplayName())
                .replace("{username-color}", metaData.getMetaValue("username-color") != null ? metaData.getMetaValue("username-color") : "")
                .replace("{message-color}", metaData.getMetaValue("message-color") != null ? metaData.getMetaValue("message-color") : "");

        format = colorize(translateHexColorCodes(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, format) : format));

        event.setFormat(format.replace("{message}", player.hasPermission("oh.colorcodes") && player.hasPermission("oh.rgbcodes")
                ? colorize(translateHexColorCodes(message)) : player.hasPermission("oh.colorcodes") ? colorize(message) : player.hasPermission("oh.rgbcodes")
                ? translateHexColorCodes(message) : message).replace("%", "%%"));
    }

    private String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    @Override
    public void onDisable() {

    }
}