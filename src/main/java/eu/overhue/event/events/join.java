package eu.overhue.event.events;

import eu.overhue.event.Utils.SpawnUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class join implements Listener {

    private SpawnUtil spawnUtil;


    public join(SpawnUtil spawnUtil){
        this.spawnUtil = spawnUtil;
    }
    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        spawnUtil.teleport2(player);

        player.sendTitle("&x&5&9&c&7&e&a&lOverHue.eu".replaceAll("&", "§"), "§fUzij si eventy ;)");
        player.setGameMode(GameMode.ADVENTURE);
        player.setWalkSpeed(0.5f);
        for(int i=0; i < 200; i++){
            player.sendMessage("\n");
        }
        player.sendMessage("\n\n\n&f⻬\uF801⻭\uF801".replaceAll("&", "§"));
        for(int i=0; i < 5; i++){
            player.sendMessage("\n");
        }
    }
}
