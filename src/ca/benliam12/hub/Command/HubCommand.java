package ca.benliam12.hub.Command;

import ca.benliam12.hub.Hub;
import ca.benliam12.hub.Utils.TeleportUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("[Hub] You must be a player to perform this Command!");
            return true;
        }
        Player player = (Player) sender;
        if(label.equalsIgnoreCase("hub"))
        {
            if(args.length != 0)
            {
                sender.sendMessage("[Hub] Unknown Command !");
                return true;
            }
            if(player.hasPermission("hub.go"))
            {
                Hub.toHub(player);
                player.sendMessage(ChatColor.GREEN + "Teleported to HUB");
            }
        }
        else if (label.equalsIgnoreCase("sethub"))
        {
            if(args.length != 0)
            {
                sender.sendMessage("[Hub] Unknown Command !");
                return true;
            }

            if(player.isOp() || player.hasPermission("hub.set"))
            {
                TeleportUtil.getInstance().setHub(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Hub set");
            }
            else
            {
               player.sendMessage(ChatColor.RED + "You don't have the permission to perform this command");
            }
        }



        return true;
    }
}
