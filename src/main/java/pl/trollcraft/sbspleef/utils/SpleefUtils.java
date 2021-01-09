package pl.trollcraft.sbspleef.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.trollcraft.sbspleef.configs.LangPL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

public class SpleefUtils {



    public static void playerQuit (Player player){


        Storage.spleefPlayerTopMap.put(Storage.playerList.size(), player);
        Storage.playerList.remove(player);
        Storage.spleefSnowBalls.remove(player);
        player.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));


        if(Storage.playerList.size() == 1){

            Player winner = Storage.playerList.get(0);
            Storage.spleefPlayerTopMap.put(Storage.playerList.size(), winner);
            Storage.playerList.remove(winner);
            Storage.spleefSnowBalls.remove(winner);
            winner.teleport(new Location(Bukkit.getWorld("spleef"), SpleefUtils.spleefRandomX(), 162, SpleefUtils.spleefRandomZ()));
            Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.playerWinns.replaceAll("%player%", winner.getName()))));
            Storage.spleefStart = false;
            Storage.spleefEnable = false;
        }

    }
    public static void spleefMessage(String message){

        Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.sbPrefix + message)));


    }

    public static void spleefHelp(Player player){
        player.sendMessage(ChatUtils.color("" + LangPL.helpHeader));
        //Graczwww
        player.sendMessage(ChatUtils.color("" + "&f /spleef join - Dolacz do spleefa!"));
        player.sendMessage(ChatUtils.color("" + "&f /spleef leave - Opusc spleefa!"));
        player.sendMessage(ChatUtils.color("" + "&f /spleef top - Sprawdz "));
        //Admin
        if (player.hasPermission("spleef.admin")) {
            player.sendMessage(ChatUtils.color("" + "&f /spleef on - Wlacz spleefa!"));
            player.sendMessage(ChatUtils.color("" + "&f /spleef start - Wystartuj spleefa!"));
            player.sendMessage(ChatUtils.color("" + "&f /spleef stop - Zatrzymaj spleefa!"));
            player.sendMessage(ChatUtils.color("" + "&f /spleef end - Usun trzecia warstwe"));
            player.sendMessage(ChatUtils.color("" + "&f /spleef third - Przelacz wylaczanie trzeciej warstwy"));
            player.sendMessage(ChatUtils.color("" + "&f /spleef reload - Przeladuj plugin."));
            player.sendMessage(ChatUtils.color("" + "&f /spleef message <wiadomosc> - Wyslij wiadomosc do wszystkich graczy spleefa"));
        }
    }
    public static void spleefTop(Player player){
       /*
        Bukkit.getLogger().log(Level.INFO, "spleeftopka: " + spleefPlayerMap);
        player.sendMessage(ChatUtils.color("" + spleefPlayerMap.toString()));
        8
        */
        for (Integer i : Storage.spleefPlayerTopMap.keySet()) {
            player.sendMessage(i + ". " + Storage.spleefPlayerTopMap.get(i).getName());
        }
    }

    public static void spleefSchem(ClipboardFormat format, File schemat){
        ClipboardReader reader;
        Clipboard clipboard;
        try {
            reader = format.getReader(new FileInputStream(schemat));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try { //Pasting Operation
            // We need to adapt our world into a format that worldedit accepts. This looks like this:
            // Ensure it is using com.sk89q... otherwise we'll just be adapting a world into the same world.
            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Bukkit.getWorld("spleef"));

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,
                    -1);

// Saves our operation and builds the paste - ready to be completed.
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(0, Storage.pasteY, 0)).ignoreAirBlocks(false).build();

            try { // This simply completes our paste and then cleans up.
                Operations.complete(operation);
                editSession.flushSession();

            } catch (WorldEditException e) { // If worldedit generated an exception it will go here
                Debug.log(ChatUtils.color("" + "Sth gone wrong"));
                e.printStackTrace();
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }


    public static boolean isPlayerInRegion(Player player, RegionManager manager, ProtectedRegion region, String world) {
        boolean playerIsIn = false;
        Location location = player.getLocation();
        assert manager != null;
        if (!world.equals(location.getWorld().getName())) {
            return false;
        }
        if (region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
            playerIsIn = true;
        }
        return playerIsIn;
    }
///////////////////////////////////////////////////Randomowe koordy

    public static double spleefRandomX(){

        return (Math.random()*((Storage.endTpX-Storage.startTpX)+1))+Storage.startTpX;

    }
    public static double spleefRandomY(){

        return (Math.random()*((Storage.endTpY-Storage.startTpY)+1))+Storage.startTpY;

    }

    public static double spleefRandomZ(){

        return (Math.random()*((Storage.endTpZ-Storage.startTpZ)+1))+Storage.startTpZ;

    }


}
