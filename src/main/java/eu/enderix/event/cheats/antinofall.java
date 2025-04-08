package eu.enderix.event.cheats;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageEvent;

public class antinofall implements Listener {

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && p.getGameMode() == GameMode.SURVIVAL) {
            if (p.getFallDistance() > 4 && e.getDamage() == 0) {
                p.sendMessage("§c[AntiCheat] NoFall detekován.");
                e.setCancelled(false);
                e.setDamage(4.0); // vynucené poškození
            }
        }
    }
}
