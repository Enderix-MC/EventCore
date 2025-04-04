package eu.enderix.event.events;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.ArrayList;

public class item implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.getInventory().clear();

        e.getPlayer().getInventory().setItem(4, getSelector());
        e.getPlayer().getInventory().setItem(5, getCosmetics());
        e.getPlayer().getInventory().setItem(3, getLobbySelector());
    }

    public ItemStack getSelector(){
        Player player = null;
        ItemStack ServerSelector = new ItemStack(Material.PAPER);
        ItemMeta ServerSelectorMeta = ServerSelector.getItemMeta();

        ServerSelectorMeta.setDisplayName("&fsᴇʀᴠᴇʀ sᴇʟᴇᴄᴛᴏʀ".replaceAll("&", "§"));
        ArrayList<String> LoreList = new ArrayList<String>();
        LoreList.add("&7Right-Click to open".replaceAll("&","§"));
        LoreList.add("&7the server selector".replaceAll("&","§"));

        ServerSelectorMeta.setLore(LoreList);
        ServerSelectorMeta.setCustomModelData(1);
        ServerSelector.setItemMeta(ServerSelectorMeta);

        return ServerSelector;
    }

    public ItemStack getCosmetics(){
        ItemStack Cosmetics = new ItemStack(Material.PAPER);
        ItemMeta meta = Cosmetics.getItemMeta();

        meta.setDisplayName("&fᴄᴏsᴍᴇᴛɪᴄs".replaceAll("&", "§"));
        ArrayList<String> LoreList = new ArrayList<String>();
        LoreList.add("&7Right-Click to open".replaceAll("&","§"));
        LoreList.add("&7the server selector".replaceAll("&","§"));

        meta.setLore(LoreList);
        meta.setCustomModelData(4);
        Cosmetics.setItemMeta(meta);

        return Cosmetics;
    }

    public ItemStack getLobbySelector(){
        ItemStack LobbySelector = new ItemStack(Material.PAPER);
        ItemMeta meta = LobbySelector.getItemMeta();

        meta.setDisplayName("&fʟᴏʙʙʏ sᴇʟᴇᴄᴛᴏʀ".replaceAll("&", "§"));
        ArrayList<String> LoreList = new ArrayList<String>();
        LoreList.add("&7Right-Click to open".replaceAll("&","§"));
        LoreList.add("&7the server selector".replaceAll("&","§"));


        meta.setLore(LoreList);
        meta.setCustomModelData(2);
        LobbySelector.setItemMeta(meta);

        return LobbySelector;
    }

}