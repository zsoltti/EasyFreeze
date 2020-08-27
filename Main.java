package me.zsoltti.EasyFreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static me.zsoltti.EasyFreeze.Voidok.*;

public class Main extends JavaPlugin implements Listener {



    @EventHandler
    public void onbreak(BlockBreakEvent e){
        if(!e.isCancelled()){
            if(fagyasztvavan(e.getPlayer())){
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onplace(BlockPlaceEvent e){
        if(!e.isCancelled()){
            if(fagyasztvavan(e.getPlayer())){
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }
    @EventHandler
    public void ondrop(PlayerDropItemEvent e) {
        if (!e.isCancelled()) {
            if (fagyasztvavan(e.getPlayer())) {
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(!e.isCancelled()){
            if(fagyasztvavan(e.getPlayer())){
                e.setCancelled(true);


                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("player chat prefix")) + " " + e.getMessage());
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onhuner(FoodLevelChangeEvent e){
        if(!e.isCancelled()){
            if(e.getEntity() instanceof Player){
                Player player = (Player) e.getEntity();

                if(fagyasztvavan(player)){
                    e.setCancelled(true);
                }else{
                    e.setCancelled(false);
                }
            }
        }
    }

    @EventHandler
    public void oncommand(PlayerCommandPreprocessEvent e){
        if(!e.isCancelled()){
            if(fagyasztvavan(e.getPlayer())){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("freezed text")));
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!e.isCancelled()){
            if(fagyasztvavan(e.getPlayer())){
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void ondmg(EntityDamageByEntityEvent e) {

        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player) {

                Player player = (Player) e.getEntity();


                if (fagyasztvavan(player)) {
                    e.setCancelled(true);
                } else {
                    e.setCancelled(false);
                }
            }

            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                if (fagyasztvavan(damager)) {
                    e.setCancelled(true);
                } else {
                    e.setCancelled(false);
                }
            }
        }
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent m){
        if(fagyasztvavan(m.getPlayer())){
            fagyaszt(m.getPlayer());
        }

        if(!getConfig().getStringList("All time players").contains(m.getPlayer().getName())) {

            List<String> plist = getConfig().getStringList("All time players");
            plist.add(m.getPlayer().getName());

            getConfig().set("All time players", plist);

            saveConfig();
            kifagyaszt(m.getPlayer());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){



        if(!e.isCancelled()) {
            if (fagyasztvavan(e.getPlayer())) {
                Location from=e.getFrom();
                Location to=e.getTo();
                double x=Math.floor(from.getX());
                double z=Math.floor(from.getZ());
                if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z)
                {
                    x+=.5;
                    z+=.5;
                    e.getPlayer().teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
                }


                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("freezed text")));



            } else {
                e.setCancelled(false);
            }
        }


    }


    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this,this);

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§aEasyFreeze plugin is up! Running on " +getDescription().getVersion());
        Bukkit.broadcastMessage("");

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("easyfreeze")){
            if(sender.hasPermission("easyfreeze.reloadconfig")){
                reloadConfig();
                sender.sendMessage("§aConfig has been reloaded.");
            }
        }

        if(command.getName().equalsIgnoreCase("checkfreeze")){
            if(sender.hasPermission("easyfreeze.check")){
                if(args.length == 0){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.checkfreeze.usage")));
                    return true;
                }
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target == null){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Wrong player")));
                        return true;
                    }

                    if(fagyasztvavan(target)){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.checkfreeze.check message").replace("%player%",target.getName()).replace("%boolean%", "True")));
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.checkfreeze.check message").replace("%player%",target.getName()).replace("%boolean%", "False")));
                }

                    return true;
                }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Too much argument")));



            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Dont have permission")));
            }
        }

        if(command.getName().equalsIgnoreCase("freeze")){
            if(sender.hasPermission("easyfreeze.use")){

                if(args.length == 0){

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.freeze.usage")));
                    return true;
                }
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target == null){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Wrong player")));
                        return true;
                    }

                    if(sender instanceof Player){
                        Player player = (Player) sender;

                        if(player == target){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("You cant be the target")));
                            return true;
                        }
                    }

                    if(fagyasztvavan(target)){
                        kifagyaszt(target);

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.freeze.unfreezed target message").replace("%player%", target.getName())));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.freeze.unfreezed message").replace("%player%", sender.getName())));
                    }else{
                        fagyaszt(target);

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.freeze.freezed target message").replace("%player%", target.getName())));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("command.freeze.freezed message").replace("%player%", sender.getName())));

                    }

                    return true;
                }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Too much argument")));




            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Dont have permission")));
            }
        }

        return false;
    }
}
