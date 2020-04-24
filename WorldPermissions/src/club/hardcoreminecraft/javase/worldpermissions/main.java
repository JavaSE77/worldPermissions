package club.hardcoreminecraft.javase.worldpermissions;

/*
 * Who said java classes start with an uppercase letter? That's stupid. I am using C naming convention.
 * */
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
  public static Plugin plugin = null;
  
  public void onEnable() {
	  //register this plugin with the event manager
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)this);
    plugin = (Plugin)this;
    //get the config
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    //inform the logger
    getServer().getLogger().info("This server is running JavaSE make on World Permissions. Download updates from: https://www.spigotmc.org/resources/world-permissions.25446/");
  }
  
  /*
   * This is where the plugin does its thing. We check whenever a player enters a world to see if they are allowed to do that.
   * If they lack the permission WorldPermission.<worldnamne>, then teleport them out of the world to the
   * location defined in the config. 
   * */
  
  @EventHandler
  public void WorldChange(PlayerChangedWorldEvent Event) {
    Player player = Event.getPlayer();
    if (!player.hasPermission("WorldPermission." + Event.getPlayer().getWorld().getName()))
      if (plugin.getConfig().getString("Spawn.world") != null) {
        Location loc = new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn.world")), plugin.getConfig().getInt("Spawn.x"), plugin.getConfig().getInt("Spawn.y"), 
            plugin.getConfig().getInt("Spawn.z"));
        player.teleport(loc);
        player.sendMessage(plugin.getConfig().getString("InvalidWorldMsg").replaceAll("&", "§"));
      }  
  }
}
