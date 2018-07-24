package ca.benliam12.hub;

import ca.benliam12.hub.Command.HubCommand;
import ca.benliam12.hub.Utils.SettingManager;
import ca.benliam12.hub.Utils.TeleportUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Hub extends JavaPlugin
{

    public static Logger log = Logger.getLogger("Minecraft");

    public void onEnable()
    {
        SettingManager.getInstance().setup();
        HubCommand commands = new HubCommand();
        TeleportUtil.getInstance().setup();
        getCommand("hub").setExecutor(commands);
        getCommand("sethub").setExecutor(commands);
    }

    public void onDisable()
    {
        TeleportUtil.getInstance().end();
        SettingManager.getInstance().clear();
    }

    public static void toHub(Player player)
    {
        TeleportUtil.getInstance().toHub(player);
    }

}
