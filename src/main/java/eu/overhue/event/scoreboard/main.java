package eu.overhue.event.scoreboard;

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

        board.updateTitle(" §f\uE1B0§r§f\uE1AF§r§f\uE1AE§r§f\uE1AD§r§f\uE1AC§r§f\uE1AB§r§f\uE1AE§r");

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
                "         &8ɴ ᴇ ᴛ ᴡ ᴏ ʀ ᴋ".replaceAll("&", "§"),
                "",
                "  &x&d&b&c&0&6&4Ξ &x&c&8&d&6&e&5Nick &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&x&d&b&c&0&6&4%player_name%   ").replaceAll("&", "§"),
                "  &x&d&b&c&0&6&4Ξ &x&c&8&d&6&e&5Rank &8| ".replaceAll("&", "§") + PlaceholderAPI.setPlaceholders(board.getPlayer(), "&f%luckperms_prefix%").replaceAll("&", "§"),
                "  &x&d&b&c&0&6&4Ξ &x&c&8&d&6&e&5Ucet &8| &x&d&b&c&0&6&4Developing   ".replaceAll("&", "§"),
                "",
                "       &8ᴍᴄ.ᴏᴠᴇʀʜᴜᴇ.ᴇᴜ    ".replaceAll("&","§")
        );
    }
}