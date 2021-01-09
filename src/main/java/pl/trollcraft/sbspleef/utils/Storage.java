package pl.trollcraft.sbspleef.utils;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import org.bukkit.entity.Player;
import pl.trollcraft.sbspleef.SbSpleef;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {

    //config

    public static boolean spleefEnable = false;
    public static boolean spleefStart = false;
    public static HashMap<Integer, Player> spleefPlayerTopMap = new HashMap<>();
    public static HashMap<Player, Integer> spleefSnowBalls = new HashMap<>(0);
    public static ArrayList<Player> playerList = new ArrayList<>();

    public static File schemat1 = new File(SbSpleef.getPlugin().getDataFolder() + File.separator + "spleef1.schem");
    public static File schemat2 = new File(SbSpleef.getPlugin().getDataFolder() + File.separator + "spleef2.schem");
    public static File schemat3 = new File(SbSpleef.getPlugin().getDataFolder() + File.separator + "spleef3.schem");
    public static File schemat4 = new File(SbSpleef.getPlugin().getDataFolder() + File.separator + "spleef4.schem");
    public static ClipboardFormat format1 = ClipboardFormats.findByFile(schemat1);
    public static ClipboardFormat format2 = ClipboardFormats.findByFile(schemat2);
    public static ClipboardFormat format3 = ClipboardFormats.findByFile(schemat3);
    public static ClipboardFormat format4 = ClipboardFormats.findByFile(schemat4);



    public static int pasteY = 64;


    public static Integer startTpX = -21;
    public static Integer startTpY = 156;
    public static Integer startTpZ = -21;
    public static Integer endTpX = 21;
    public static Integer endTpY = 139;
    public static Integer endTpZ = 21;


}