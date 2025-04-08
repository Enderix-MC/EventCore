package eu.enderix.event.cheats;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class antifly implements Listener {

    private final HashMap<UUID, Long> lastAlert = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        if (p.getAllowFlight() || p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) return;

        double yDiff = to.getY() - from.getY();
        Location below = p.getLocation().subtract(0, 1, 0);

        if (yDiff > 0.25 && !p.isOnGround() && below.getBlock().getType() == Material.AIR) {
            long now = System.currentTimeMillis();
            if (lastAlert.getOrDefault(p.getUniqueId(), 0L) + 3000 < now) {
                p.teleport(from);
                p.setVelocity(new Vector(0, -1, 0));
                p.sendMessage("§c[AntiCheat] Fly není povolen.");
                Bukkit.getLogger().info("[AntiCheat] " + p.getName() + " byl zastaven při pokusu o fly.");
                lastAlert.put(p.getUniqueId(), now);
            } else {
                event.setCancelled(true); // tiché zrušení bez spamu
            }
        }
    }
}
