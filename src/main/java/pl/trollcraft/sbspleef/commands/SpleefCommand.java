package pl.trollcraft.sbspleef.commands;

import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.sbspleef.configs.Configs;
import pl.trollcraft.sbspleef.configs.LangPL;
import pl.trollcraft.sbspleef.configs.LoadConfig;
import pl.trollcraft.sbspleef.utils.AutoMelt;
import pl.trollcraft.sbspleef.utils.ChatUtils;
import pl.trollcraft.sbspleef.utils.SpleefUtils;
import pl.trollcraft.sbspleef.utils.Storage;


public class SpleefCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatUtils.color("" + "Musisz byc graczem zeby to zrobic!"));
            return true;
        }

        Player player = (Player) sender;
        if(args.length == 0) {

            SpleefUtils.spleefHelp(player);

        }
        else if(args.length == 1){

            if(args[0].equals("join")){
                if(Storage.spleefEnable){
                    if(!(Storage.spleefStart)){
                        if (!(Storage.playerList.contains(player))) {
                            Storage.playerList.add(player);

                            sender.sendMessage(ChatUtils.color("" + LangPL.spleefJoin));
                            player.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), SpleefUtils.spleefRandomY(), SpleefUtils.spleefRandomZ()));
                            player.getInventory().clear();

                            ItemStack shovel = new ItemStack (Material.DIAMOND_SHOVEL, 1);
                            ItemMeta shovelMeta = shovel.getItemMeta();
                            shovelMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
                            shovel.setItemMeta(shovelMeta);

                            player.getInventory().addItem(shovel);
                        }
                        else{
                            sender.sendMessage(ChatUtils.color("" + LangPL.playerAlreadyJoined));
                        }
                    }
                    else{
                        sender.sendMessage(ChatUtils.color("" + LangPL.spleefAlReadyStarted));
                    }
                }
                else{
                    sender.sendMessage(ChatUtils.color("" + LangPL.spleefNotOn));
                }
            }
            else if(args[0].equals("leave")){
                if (Storage.playerList.contains(player)) {
                    if(Storage.spleefStart){
                        SpleefUtils.playerQuit(player);
                    }
                    else{
                        Storage.playerList.remove(player);
                    }
                    player.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
                    player.sendMessage(ChatUtils.color("" + LangPL.playerLeft));
                    player.getInventory().clear();
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.playerNotJoined));
                }
            }
            else if(args[0].equals("top")){
                //
                if(!(Storage.spleefPlayerTopMap.isEmpty())){
                    player.sendMessage(ChatUtils.color("" + LangPL.spleefPrefix));
                    SpleefUtils.spleefTop(player);
                } else {
                    player.sendMessage(ChatUtils.color("" + LangPL.spleefNoTop));
                }

            }

            //////////////////////////////////////////////////////////Admin commands
            else if(args[0].equals("on")){
                if (player.hasPermission("spleef.admin.on")) {
                    if (!(Storage.spleefEnable)) {
                        Storage.spleefEnable = true;
                        Storage.spleefPlayerTopMap.clear();
                        Storage.spleefSnowBalls.clear();
                        player.sendMessage(ChatUtils.color("" + LangPL.confSpleefOn));
                        SpleefUtils.spleefSchem(Storage.format1, Storage.schemat1);
//                        player.sendMessage(ChatUtils.color("" + "Cos sie zjebalo xD"));
                        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.bcSpleefOn)));
                    } else {
                        player.sendMessage(ChatUtils.color("" + LangPL.spleefAlreadyOn));
                    }
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else if(args[0].equals("start")){
                if (player.hasPermission("spleef.admin.start")) {
                    if (!(Storage.spleefStart)) {
                        if (Storage.spleefEnable) {
                            SpleefUtils.spleefSchem(Storage.format1, Storage.schemat1);

                            if(Storage.playerList.size() == 1){
                                player.sendMessage(ChatUtils.color("" + LangPL.error));
                                return true;
                            }
                            for( Player playerToTp : Storage.playerList){
                                Storage.spleefSnowBalls.put(playerToTp, 0);
                                playerToTp.sendMessage(ChatUtils.color("" + LangPL.goodLuck));
                                playerToTp.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), SpleefUtils.spleefRandomY(), SpleefUtils.spleefRandomZ()));
                                playerToTp.sendMessage(ChatUtils.color("" + LangPL.playerSpleefStarted));

                            }
                            Storage.spleefStart = true;
                            player.sendMessage(ChatUtils.color("" + LangPL.confSpleefOn));
                            AutoMelt.startMelDown();
                        }
                        else {
                            player.sendMessage(ChatUtils.color("" + LangPL.spleefNotOn));
                        }
                    }
                    else {
                        player.sendMessage(ChatUtils.color("" + LangPL.spleefAlReadyStarted));
                    }
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }

            }
            else if(args[0].equals("stop")) {
                if (player.hasPermission("spleef.admin.stop")) {
                    SpleefUtils.spleefSchem(Storage.format1, Storage.schemat1);
                    if (Storage.spleefEnable) {
                        Storage.spleefEnable = false;
                        AutoMelt.indexRun = 0;
                        if (Storage.spleefStart) {
                            Storage.spleefStart = false;
                            player.sendMessage(ChatUtils.color("" + LangPL.youStoppedSpleef));
                            if (!Storage.playerList.isEmpty()) {
                                try {
                                    for (int i = 0; i < Storage.playerList.size(); i++) {
                                        Player playerToTp = Storage.playerList.get(i);
                                        SpleefUtils.playerQuit(playerToTp);
                                        playerToTp.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
                                        playerToTp.sendMessage(ChatUtils.color("" + LangPL.playerLeft));
                                        playerToTp.getInventory().clear();
                                    }
                                } catch (Exception error) {
                                    error.printStackTrace();
                                }
                                Storage.playerList.clear();
                            }
                        }
                        else {
                            player.sendMessage(ChatUtils.color("" + LangPL.spleefNotStarted));
                            Storage.playerList.clear();
                        }
                    }
                    else {
                        player.sendMessage(ChatUtils.color("" + LangPL.spleefNotOn));
                    }
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else if(args[0].equals("message")) {
                if (player.hasPermission("spleef.admin")) {
                    player.sendMessage(ChatUtils.color("" + LangPL.iNeedMoreArgs));
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                }
                SpleefUtils.spleefHelp(player);
            }
            else if(args[0].equals("end")){
                if (player.hasPermission("spleef.admin")) {
                    Bukkit.broadcastMessage(ChatUtils.color("" + LangPL.thirdDelete)); //TODO
                    LangPL.thirdRemove = false;
                    SpleefUtils.spleefSchem(Storage.format4, Storage.schemat4);
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else if(args[0].equals("debug")){
                if (player.hasPermission("spleef.admin")) {
                    player.sendMessage(ChatUtils.color("" + "Lista graczy ktorzy dolaczyli: " + Storage.playerList));
                    player.sendMessage(ChatUtils.color("" + "Lista graczy ktorzy odpadli: " + Storage.spleefPlayerTopMap));
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else if(args[0].equals("third")){
                if (player.hasPermission("spleef.admin")) {
                    if (LangPL.thirdRemove) {
                        LangPL.thirdRemove = false;
                    } else {
                        LangPL.thirdRemove = true;
                    }
                    player.sendMessage(ChatUtils.color("" + LangPL.reload));
                    LoadConfig.save();
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else if(args[0].equals("reload")){
                if (player.hasPermission("spleef.admin")) {
                    if (Storage.spleefEnable) {
                        Storage.spleefEnable = false;
                        if (Storage.spleefStart) {
                            Storage.spleefStart = false;
                            player.sendMessage(ChatUtils.color("" + LangPL.youStoppedSpleef));
                            if (!Storage.playerList.isEmpty()) {
                                try {
                                    for (int i = 0; i < Storage.playerList.size(); i++) {
                                        SpleefUtils.playerQuit(Storage.playerList.get(i));
                                    }
                                } catch (Exception error) {
                                    error.printStackTrace();
                                }
                                Storage.playerList.clear();
                            }
                        }
                        else {
                            Storage.playerList.clear();
                        }
                    }
                    LoadConfig.load();
                    player.sendMessage(ChatUtils.color("" + LangPL.reload));
                }
                else{
                    player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                    SpleefUtils.spleefHelp(player);
                }
            }
            else{
                SpleefUtils.spleefHelp(player);
            }


        }
        else if(args[0].equals("kick")){
            if (player.hasPermission("spleef.admin")) {
                String reason = "";
                if (args.length > 2) {
                    for (int i = 2; i < args.length; i++) {
                        if (i == 2) {
                            reason += args[i];
                        } else {
                            reason += " " + args[i];
                        }
                    }
                }
                if (reason.equalsIgnoreCase("")) {
                    reason = LangPL.noReasonKick;
                }
                Player playerKick;
                for (Player listPlayer : Storage.playerList) {
                    if (listPlayer.getName().equalsIgnoreCase(args[1])) {
                        playerKick = listPlayer;
                        if (Storage.spleefStart) {
                            SpleefUtils.playerQuit(playerKick);
                        } else {
                            Storage.playerList.remove(playerKick);
                        }
                        playerKick.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
                        String finalReason = reason;
                        Player finalPlayerKick = playerKick;
                        Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.spleefPlayerKicked.replaceAll("%reason%", finalReason).replaceAll("%player%", finalPlayerKick.getName()))));
                        playerKick.sendMessage(ChatUtils.color("" + LangPL.youKicked.replaceAll("%reason%", finalReason)));
                        playerKick.getInventory().clear();
                    }
                }
            }
            else{
                player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                SpleefUtils.spleefHelp(player);
            }
        }
        else if(args[0].equals("message")) {
            if (player.hasPermission("spleef.admin")) {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += " " + args[i];
                }
                player.sendMessage(ChatUtils.color("" + LangPL.messageSend));
                SpleefUtils.spleefMessage(message);
            }
            else{
                player.sendMessage(ChatUtils.color("" + LangPL.permissionDeny));
                SpleefUtils.spleefHelp(player);
            }
        }
        else{
            SpleefUtils.spleefHelp(player);
        }

        return true;
    }
}
