package eu.enderix.event.cheats;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class antikillaura implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        // Detekce útoku přes zeď
        if (!attacker.hasLineOfSight(victim)) {
            e.setCancelled(true);
            attacker.sendMessage("§c[AntiCheat] Podezření na KillAura (útok přes zeď)");
        }

        // Nepřirozená rotace (volitelně)
        Vector dir = attacker.getLocation().getDirection();
        Vector toTarget = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
        double angle = dir.angle(toTarget);

        if (angle > 1.5) {
            attacker.sendMessage("§c[AntiCheat] Podezření na KillAura (otočka mimo cíl)");
        }
    }
}
