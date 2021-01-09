package pl.trollcraft.sbspleef.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.sbspleef.utils.Storage;

import java.rmi.ConnectIOException;

public class LoadConfig {


    public static void load(){

        YamlConfiguration configuration = Configs.load("langPL.yml");

        LangPL.worldName = configuration.getString("worldName");
        LangPL.spleefCommand = configuration.getString("spleefCommand");
        LangPL.spleefRegion = configuration.getString("spleefRegion");

        LangPL.permissionDeny = configuration.getString("permissionDeny");
        LangPL.spleefPrefix = configuration.getString("spleefPrefix");
        LangPL.sbPrefix = configuration.getString("sbPrefix");
        LangPL.confSpleefOn = configuration.getString("confSpleefOn");
        LangPL.youAreNotPlayer = configuration.getString("youAreNotPlayer");
        LangPL.helpHeader = configuration.getString("helpHeader");
        LangPL.spleefJoin = configuration.getString("spleefJoin");
        LangPL.playerAlreadyJoined = configuration.getString("playerAlreadyJoined");
        LangPL.spleefAlReadyStarted = configuration.getString("spleefAlReadyStarted");
        LangPL.spleefNotOn = configuration.getString("spleefNotOn");
        LangPL.playerLeft = configuration.getString("playerLeft");
        LangPL.playerNotJoined = configuration.getString("playerNotJoined");
        LangPL.ohterNoJoined = configuration.getString("ohterNoJoined");
        LangPL.spleefNoTop = configuration.getString("spleefNoTop");
        LangPL.bcSpleefOn = configuration.getString("bcSpleefOn");
        LangPL.spleefAlreadyOn = configuration.getString("spleefAlreadyOn");
        LangPL.goodLuck = configuration.getString("goodLuck");
        LangPL.playerSpleefStarted = configuration.getString("playerSpleefStarted");
        LangPL.youStoppedSpleef = configuration.getString("youStoppedSpleef");
        LangPL.spleefNotStarted = configuration.getString("spleefNotStarted");
        LangPL.iNeedMoreArgs = configuration.getString("iNeedMoreArgs");
        LangPL.messageSend = configuration.getString("messageSend");
        LangPL.wrongBlockDestroy = configuration.getString("wrongBlockDestroy");
        LangPL.youLose = configuration.getString("youLose");
        LangPL.youKicked = configuration.getString("youKicked");
        LangPL.spleefPlayerKicked = configuration.getString("spleefPlayerKicked");
        LangPL.noReasonKick = configuration.getString("noReasonKick");
        LangPL.playerWinns = configuration.getString("playerWinns");
        LangPL.deniedCommand = configuration.getString("deniedCommand");
        LangPL.firstIn40 = configuration.getString("firstIn40");
        LangPL.firstIn20 = configuration.getString("firstIn20");
        LangPL.firstDelete = configuration.getString("firstDelete");
        LangPL.secondIn40 = configuration.getString("secondIn40");
        LangPL.secondIn20 = configuration.getString("secondIn20");
        LangPL.secondDelete = configuration.getString("secondDelete");
        LangPL.thirdIn40 = configuration.getString("thirdIn40");
        LangPL.thirdIn20 = configuration.getString("thirdIn20");
        LangPL.thirdDelete = configuration.getString("thirdDelete");

        LangPL.reload = configuration.getString("reload");
        LangPL.error = configuration.getString("error");
        LangPL.bypass = configuration.getString("bypass");

        LangPL.thirdRemove = configuration.getBoolean("thirdRemove");
    }

    public static void save(){

        YamlConfiguration configuration = Configs.load("langPL.yml");

        configuration.set("thirdRemove", LangPL.thirdRemove);

        Configs.save(configuration, "LangPL.yml");
    }


}
