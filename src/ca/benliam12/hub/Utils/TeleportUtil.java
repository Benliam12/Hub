package ca.benliam12.hub.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TeleportUtil
{
    private static TeleportUtil instance = new TeleportUtil();
    public static TeleportUtil getInstance() {return instance;}
    private TeleportUtil() {}

    private SettingManager sm = SettingManager.getInstance();
    private Location hub;

    public void setup()
    {
        if(this.getLocation("Hub", sm.getConfig("config")) == null)
        {
            this.setConfigLocation("Hub", "config", sm.getConfig("config"), new Location(Bukkit.getWorld("world"),0,0,0));
        }

        this.hub = this.getLocation("Hub", sm.getConfig("config"));
    }

    public void setHub(Location location)
    {
        this.setConfigLocation("Hub", "config", sm.getConfig("config"), location);
        this.hub = location;
    }

    public void toHub(Player player)
    {
        player.teleport(this.hub);
    }

    /**
     * Get a location from a config file
     *
     * @param path Path to the location
     * @param config Config file
     * @return The location gotten
     */
    public Location getLocation(String path, FileConfiguration config)
    {
        if(config.get(path) != null){
            World w = Bukkit.getWorld(config.getString(path + ".world"));
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");
            float pitch = (float)config.getDouble(path + ".pitch");
            float yaw = (float)config.getDouble(path + ".yaw");
            return new Location(w,x,y,z,yaw,pitch);
        }
        return null;
    }

    /**
     * Set a location to a config file
     *
     * @param path Path to the location
     * @param name Name of the config
     * @param config Config file
     * @param location Location to save
     */
    public void setConfigLocation(String path, String name, FileConfiguration config, Location location)
    {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".pitch", location.getPitch());
        config.set(path + ".yaw" , location.getYaw());
        sm.saveConfig(name, config);
    }

}
