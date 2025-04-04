package eu.enderix.event.events;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class openmenu implements Listener {
    @EventHandler
    public void ballFiring(PlayerInteractEvent e){
        Player p = e.getPlayer();
        try {
            if(e.getItem().getItemMeta().getCustomModelData() == 1){
                p.performCommand("dm open list1");
            } else if(e.getItem().getItemMeta().getCustomModelData() == 2){
                p.performCommand("dm open LobbySelector");
            } else if (e.getItem().getItemMeta().getCustomModelData() == 4){
                p.performCommand("gadgetsmenu menu main");
            }
        } catch (Exception ignored) {}
    }
}
