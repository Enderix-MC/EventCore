package eu.overhue.event.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class joinemssage implements Listener {
    @EventHandler
    public void joinmessage(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(player.hasPermission("joinquit.message")){
            String join = "&x&5&9&c&7&e&aOverHue &8| &f"+ PlaceholderAPI.setPlaceholders(event.getPlayer(), "%luckperms_prefix%") +"&f " + PlaceholderAPI.setPlaceholders(event.getPlayer(), "%player_name%") + " &7se pripojil na server.";
            event.setJoinMessage(join.replaceAll("&", "§"));
        } else {
            event.setJoinMessage("");
        }
    }
}
