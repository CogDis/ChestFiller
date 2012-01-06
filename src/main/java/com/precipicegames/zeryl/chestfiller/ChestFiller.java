package com.precipicegames.zeryl.chestfiller;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

/**
 *
 * @author Zeryl
 */
public class ChestFiller extends JavaPlugin {
    public boolean state = false;
    
    private final ChestFillerPlayerListener playerListener = new ChestFillerPlayerListener(this);

    public HashMap<Player, String> items = new HashMap();
    
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now enabled.");
    }
    
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " is now disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("cfitem")) {
            if((args.length > 0) && args[0].equalsIgnoreCase("clear")) {
                items.remove((Player) sender);
                return true;
            }
            else if(args.length > 0) {
                items.put((Player) sender, (String) args[0]);
                sender.sendMessage("New item for ChestFiller: " + args[0]);
                return true;
            }
            else {
                String item;
                if(items.containsKey((Player) sender)) {
                    item = items.get((Player) sender).toString();
                }
                else {
                    item = "Nothing";
                }
                sender.sendMessage("You curently have " + item + " selected to fill chests with");
            }
        }
        return true;
    }
}