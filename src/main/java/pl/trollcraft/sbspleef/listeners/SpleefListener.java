package pl.trollcraft.sbspleef.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.sbspleef.configs.LangPL;
import pl.trollcraft.sbspleef.utils.ChatUtils;
import pl.trollcraft.sbspleef.utils.SpleefUtils;
import pl.trollcraft.sbspleef.utils.Storage;


public class SpleefListener implements Listener {


    RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("spleef")));


    @EventHandler
    public void onDestroySnowBlock(BlockBreakEvent event){


        Player player = event.getPlayer();
        ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);


        ProtectedRegion region = manager.getRegion("spleef");
        if (region == null) {
            return;
        }
        if (SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")) {
            if(Storage.spleefEnable) {
                if (event.getBlock().getType().equals(Material.SNOW_BLOCK)) {
                    if(Storage.spleefStart) {
                        if (Storage.playerList.contains(player)) {
                            event.setDropItems(false);

                            int snowBallsP = Storage.spleefSnowBalls.get(player);
                            snowBallsP += 1;
                            Storage.spleefSnowBalls.remove(player);
                            Storage.spleefSnowBalls.put(player, snowBallsP);

                            if( snowBallsP % 2  == 0){
                                player.getInventory().addItem(snowball);
                            }

                        }
                        else if(player.hasPermission("spleef.admin.bypass")){
                            player.sendMessage(ChatUtils.color("" + LangPL.bypass));
                        }
                        else {
                            event.setCancelled(true);
                            player.sendMessage(ChatUtils.color("" + LangPL.playerNotJoined));
                        }
                    }
                    else if(player.hasPermission("spleef.admin.bypass")){
                        player.sendMessage(ChatUtils.color("" + LangPL.bypass));
                    }
                    else {
                        event.setCancelled(true);
                    }
                }
                else{
                    event.setCancelled(true);
                    player.sendMessage(ChatUtils.color(LangPL.wrongBlockDestroy));
                }
            }
            else if(player.hasPermission("spleef.admin.bypass")){
                player.sendMessage(ChatUtils.color("" + LangPL.bypass));
            }
            else{
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlaceBlok(BlockPlaceEvent event){


        Player player = event.getPlayer();


        ProtectedRegion region = manager.getRegion("spleef");
        if (region == null) {
            return;
        }
        if (SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")) {
            if(player.hasPermission("spleef.admin.bypass")){
                player.sendMessage(ChatUtils.color("" + LangPL.bypass));
            }
            else{
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onLose(PlayerInteractEvent event){


        Player player = event.getPlayer();

        if(Storage.spleefEnable && Storage.spleefStart){
            ProtectedRegion region = manager.getRegion(LangPL.spleefRegion);
            if (region == null){
                return;
            }
            if(SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")){
                if(event.getAction().equals(Action.PHYSICAL)) {
                    Material material = event.getClickedBlock().getType();
                    if (material.equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                        player.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
                        player.sendMessage(ChatUtils.color("" + LangPL.youLose));
                        SpleefUtils.playerQuit(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){

        Player player = event.getPlayer();

        if (Storage.playerList.contains(player)) {

            String noBlock = "/" + LangPL.spleefCommand;
            String outputCommand = event.getMessage();

            if (!outputCommand.contains(noBlock) || outputCommand.contains(" " + noBlock)) {
                if(!(player.hasPermission("spleef.admin.bypass"))){
                    event.setCancelled(true);
                    player.sendMessage(ChatUtils.color("" + LangPL.deniedCommand));
                }
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){

        Player player = (Player) event.getPlayer();

        ProtectedRegion region = manager.getRegion(LangPL.spleefRegion);
        if (region == null){
            return;
        }
        if(SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")) {
            if(!(player.hasPermission("spleef.admin.bypass"))){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){

        Player player = (Player) event.getEntity().getShooter();

        if(event.getEntity().getType().equals(EntityType.SNOWBALL)){
            ProtectedRegion region = manager.getRegion(LangPL.spleefRegion);
            if (region == null){
                return;
            }
            if(SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")) {
                if(!(Storage.playerList.contains(player))){
                    if(!(player.hasPermission("spleef.admin.bypass"))){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onHit(ProjectileHitEvent event){

        Player player = (Player) event.getEntity().getShooter();

        if(event.getEntity().getType().equals(EntityType.SNOWBALL)){
            ProtectedRegion region = manager.getRegion(LangPL.spleefRegion);
            if (region == null){
                return;
            }
            if(SpleefUtils.isPlayerInRegion(player, manager, region, "spleef")){
//
                Block block = event.getHitBlock();

                if(block != null){
                    if(Storage.spleefStart){
                        if(block.getType().equals(Material.SNOW_BLOCK)){
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (Storage.playerList.contains(player)) {
            if (Storage.spleefEnable) {
                if(Storage.spleefStart){
                    SpleefUtils.playerQuit(player);
                }
                else{
                    Storage.playerList.remove(player);
                }
            }
            player.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
        }
    }

}
