package eu.enderix.event.cheats;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class antinuke implements Listener {

    private final HashMap<UUID, Long> lastBreak = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if (block.getType() == Material.AIR) return;

        long now = System.currentTimeMillis();
        long last = lastBreak.getOrDefault(p.getUniqueId(), 0L);

        if (now - last < 100) {
            e.setCancelled(true);
            p.sendMessage("§c[AntiCheat] FastBreak/Nuker detekován.");
        }

        lastBreak.put(p.getUniqueId(), now);
    }
}
