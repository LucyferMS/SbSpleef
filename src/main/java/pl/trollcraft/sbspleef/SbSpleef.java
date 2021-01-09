package pl.trollcraft.sbspleef;

import org.bukkit.plugin.java.JavaPlugin;
import pl.trollcraft.sbspleef.commands.SpleefCommand;
import pl.trollcraft.sbspleef.configs.LoadConfig;
import pl.trollcraft.sbspleef.listeners.SpleefListener;

public class SbSpleef extends JavaPlugin {


    private static SbSpleef plugin;


    public void onEnable() {
        plugin = this;
        getCommand("spleef").setExecutor(new SpleefCommand());
        getServer().getPluginManager().registerEvents(new SpleefListener(), this);
        LoadConfig.load();

    }

    public static SbSpleef getPlugin(){
        return plugin;
    }
}

