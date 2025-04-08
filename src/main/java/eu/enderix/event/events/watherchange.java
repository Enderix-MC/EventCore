package eu.enderix.event.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class watherchange implements Listener {
    @EventHandler
    public void Weather(WeatherChangeEvent event) {
        World world = event.getWorld();
        if(world.getName().equals("Event01")){
            event.setCancelled(true);
        }
    }
}
