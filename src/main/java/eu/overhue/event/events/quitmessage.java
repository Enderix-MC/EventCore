package eu.overhue.event.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class quitmessage implements Listener {
    @EventHandler
    public void quitmessage(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(player.hasPermission("joinquit.message")){
            String join = "&x&5&9&c&7&e&aOverHue &8| &f"+ PlaceholderAPI.setPlaceholders(event.getPlayer(), "%luckperms_prefix%") +"&f " + PlaceholderAPI.setPlaceholders(event.getPlayer(), "%player_name%") + " &7se odpojil ze serveru.";
            event.setQuitMessage(join.replaceAll("&", "§"));
        } else {
            event.setQuitMessage("");
        }
    }
}
