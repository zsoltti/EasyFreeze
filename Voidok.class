package me.zsoltti.EasyFreeze;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Voidok {

    static Plugin plugin = Main.getPlugin(Main.class);

    public static void loadConfig(){
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public static boolean fagyasztvavan(Player player){

        Boolean finalb = false;


        if(plugin.getConfig().getBoolean("players." + player.getName() + ".freeze")){

            finalb = true;
        }

        return finalb;
    }




    public static void fagyaszt(Player player){

        plugin.getConfig().set("players." + player.getName() + ".freeze", true);
        plugin.saveConfig();
    }


    public static void kifagyaszt(Player player){

        plugin.getConfig().set("players." + player.getName() + ".freeze", false);
        plugin.saveConfig();
    }
}
