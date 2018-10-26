package ca.benliam12.hub.Utils;

import ca.benliam12.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    private boolean debug = false;

    /**
     * Setup hub
     */
    public void setup()
    {
        if(this.getLocation("Hub", sm.getConfig("config")) == null)
        {
            /* Default location if no location set*/
            this.setConfigLocation("Hub", "config", sm.getConfig("config"), new Location(Bukkit.getWorld("world"),0,100,0));
        }

        try
        {
            this.debug = sm.getConfig("config").getBoolean("debug");
        }
        catch (NullPointerException e)
        {
            this.debug = true;
            FileConfiguration config = sm.getConfig("config");
            config.set("debug", true);
            sm.saveConfig("config", config);

            e.printStackTrace();
        }

        this.hub = this.getLocation("Hub", sm.getConfig("config"));
    }

    public void end()
    {
        FileConfiguration config = sm.getConfig("config");
        config.set("debug", this.debug);
        sm.saveConfig("config", config);
    }

    public void reload()
    {
        if(this.hub.getWorld() == null)
        {
            try
            {
                this.hub = this.getLocation("Hub", sm.getConfig("config"));
                Hub.log.info("[Hub] reloaded");
            }
            catch (NullPointerException e)
            {
                this.debug = true;
                FileConfiguration config = sm.getConfig("config");
                config.set("debug", true);
                sm.saveConfig("config", config);

                e.printStackTrace();
            }
        }
    }

    public void toggleDebug(Player player)
    {
        if(this.debug)
        {
            this.debug = false;
        }
        else
        {
            this.debug = true;
        }

        player.sendMessage(ChatColor.GREEN + "[Hub] " + ChatColor.YELLOW + "Set debug mode on : " + ChatColor.RESET + this.debug);
    }

    /**
     * Set new Hub location
     * @param location New Hub location
     */
    public void setHub(Location location)
    {
        this.setConfigLocation("Hub", "config", sm.getConfig("config"), location);
        this.hub = location;
    }

    /**
     * Teleport player to hub
     * @param player Player to teleport.
     */
    public void toHub(Player player)
    {
        if(this.debug)
        {
            Hub.log.info("[Hub Debug] " + this.hub.toString());
        }
        this.reload();
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
