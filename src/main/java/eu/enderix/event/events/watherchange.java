package eu.enderix.event.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class watherchange implements Listener {
    @EventHandler
    public void Weather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
