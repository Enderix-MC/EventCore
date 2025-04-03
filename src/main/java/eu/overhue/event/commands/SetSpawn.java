package eu.overhue.event.commands;

import eu.overhue.event.Utils.SpawnUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {
    private SpawnUtil spawnUtil;

    public SetSpawn(SpawnUtil spawnUtil){
        this.spawnUtil = spawnUtil;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cant use this command as console prespective!");
            return true;
        }
        Player player = (Player) sender;
        spawnUtil.set(player.getLocation());

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eOverHue &8| &fServers spawn has been set to your &aLocation!"));

        return true;
    }
}

