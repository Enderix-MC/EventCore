package eu.enderix.event.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ItemSwitch implements Listener {
    @EventHandler(priority= EventPriority.NORMAL, ignoreCancelled=false)
    public void onSwitchItem(PlayerItemHeldEvent e) {

        Player p = e.getPlayer();

        if (p.getInventory().getHeldItemSlot() == 4) {
            e.setCancelled(true);
        }
        if (p.getInventory().getHeldItemSlot() == 5) {
            e.setCancelled(true);
        }
        if (p.getInventory().getHeldItemSlot() == 3) {
            e.setCancelled(true);
        }
    }
}
