package eu.enderix.event.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public final class main implements Listener {

    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();


        FastBoard board = new FastBoard(player);

        board.updateTitle("&x&D&0&0&8&F&BE&x&C&3&0&7&F&An&x&B&6&0&5&F&8d&x&A&A&0&4&F&7e&x&9&D&0&3&F&6r&x&9&0&0&1&F&4i&x&8&3&0&0&F&3x".replaceAll("&", "§"));

        this.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public static void updateBoard(FastBoard board) {


        board.updateLines(
                "".replaceAll("&", "§"),
                "",
                "  &x&D&0&0&8&F&BΞ &x&c&8&d&6&e&5Nick &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&x&D&0&0&8&F&B%player_name%   ").replaceAll("&", "§"),
                "  &x&D&0&0&8&F&BΞ &x&c&8&d&6&e&5Rank &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&x&D&0&0&8&F&B%luckperms_highest_group_by_weight%").replaceAll("&", "§"),
                "  &x&D&0&0&8&F&BΞ &x&c&8&d&6&e&5Total &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&x&D&0&0&8&F&B%bungee_total%").replaceAll("&", "§"),
                "  &x&D&0&0&8&F&BΞ &x&c&8&d&6&e&5Online &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&x&D&0&0&8&F&B%server_online%").replaceAll("&", "§"),
                "",
                "&8mc.enderix.eu    ".replaceAll("&","§")
        );
    }
}