package eu.enderix.event.Utils.Event;

import eu.enderix.event.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EventUtil {

    private final FileConfiguration config;
    private final String eventId;
    private final Core plugin; // Instance hlavní třídy

    public EventUtil(Core plugin, String eventId) {
        this.plugin = plugin;
        this.config = plugin.getConfig(); // Získání výchozí konfigurace
        this.eventId = eventId;
    }

    /**
     * Zkontroluje, zda event existuje (má nastavený spawn).
     */
    public boolean exists() {
        boolean exists = config.get("events." + eventId + ".location") != null;
        Bukkit.getLogger().info("[Event] Checking existence of " + eventId + ": " + exists);
        return exists;
    }

    /**
     * Nastaví spawnpoint pro event na danou lokaci.
     */
    public void set(Location loc) {
        Bukkit.getLogger().info("[Event] Setting location for event " + eventId + " at " + loc);
        config.set("events." + eventId + ".location", loc);
        config.set("events." + eventId + ".active", false); // Nový event je automaticky neaktivní
        plugin.saveConfig(); // Uložení změn
    }

    /**
     * Smaže spawnpoint eventu.
     */
    public void deleteSpawnLocation() {
        Bukkit.getLogger().info("[Event] Deleting location for event " + eventId);
        config.set("events." + eventId + ".location", null);
        config.set("events." + eventId + ".active", false); // Po smazání spawnu je event neaktivní
        plugin.saveConfig(); // Uložení změn
    }

    /**
     * Teleportuje hráče na spawn eventu, pokud je aktivní a spawn existuje.
     */
    public void teleport(Player player) {
        if (!isActive()) {
            //player.sendMessage("§cTento event není aktivní!");
            return;
        }

        Location loc = (Location) config.get("events." + eventId + ".location");
        if (loc != null) {
            Bukkit.getLogger().info("[Event] Teleporting player to event " + eventId);
            player.teleport(loc);
            //player.sendMessage("§aByl jsi teleportován na event " + eventId + "!");
        } else {
            //player.sendMessage("§cEvent nemá nastavený spawn!");
        }
    }

    /**
     * Aktivuje event, aby se na něj hráči mohli připojovat.
     */
    public void startEvent() {
        if (!exists()) {
            Bukkit.getLogger().warning("[Event] Cannot start event " + eventId + " because it has no spawn!");
            return;
        }

        Bukkit.getLogger().info("[Event] Starting event " + eventId);
        config.set("events." + eventId + ".active", true);
        plugin.saveConfig();
    }

    /**
     * Deaktivuje event, aby se na něj hráči nemohli připojovat.
     */
    public void stopEvent() {
        Bukkit.getLogger().info("[Event] Stopping event " + eventId);
        config.set("events." + eventId + ".active", false);
        plugin.saveConfig();
    }

    /**
     * Zkontroluje, zda je event aktivní.
     */
    public boolean isActive() {
        return config.getBoolean("events." + eventId + ".active", false);
    }
}
