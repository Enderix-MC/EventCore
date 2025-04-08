package eu.enderix.event.cheats;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class antireach implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        double distance = attacker.getLocation().distance(victim.getLocation());
        if (distance > 3.2) {
            e.setCancelled(true);
            attacker.sendMessage("§c[AntiCheat] Podezření na Reach (" + String.format("%.2f", distance) + " bloků)");
        }
    }
}
