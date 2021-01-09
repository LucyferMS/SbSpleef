package pl.trollcraft.sbspleef.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.sbspleef.SbSpleef;
import pl.trollcraft.sbspleef.configs.LangPL;

public class AutoMelt {

        public static int indexRun = 0;

        public static void startMelDown(){



                new BukkitRunnable(){

                        @Override
                        public void run(){
                                if(!(Storage.spleefStart)){
                                        indexRun = 0;
                                        cancel();
                                }

                                switch(indexRun){

                                        case 0:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.firstIn40)));
                                                break;
                                        case 1:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.firstIn20)));
                                                break;
                                        case 2:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.firstDelete)));
                                                SpleefUtils.spleefSchem(Storage.format2, Storage.schemat2);
                                                break;
                                        case 3:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.secondIn40)));
                                                break;
                                        case 4:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.secondIn20)));
                                                break;
                                        case 5:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }
                                                Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.secondDelete)));
                                                SpleefUtils.spleefSchem(Storage.format3, Storage.schemat3);
                                                break;
                                        case 6:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }

                                                if(LangPL.thirdRemove){
                                                        Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.thirdIn40)));
                                                }
                                                else{
                                                        indexRun = 0;
                                                        cancel();
                                                }
                                                break;
                                        case 7:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }

                                                if(LangPL.thirdRemove){
                                                        Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.thirdIn20)));
                                                }
                                                else{
                                                        indexRun = 0;
                                                        cancel();
                                                }
                                                break;
                                        case 8:
                                                if(!(Storage.spleefStart)){
                                                        indexRun = 0;
                                                        cancel();
                                                        break;
                                                }

                                                if(LangPL.thirdRemove){
                                                        Bukkit.getWorld("spleef").getPlayers().forEach(p -> p.sendMessage(ChatUtils.color("" + LangPL.thirdDelete)));
                                                        SpleefUtils.spleefSchem(Storage.format4, Storage.schemat4);
                                                }
                                                else{
                                                        indexRun = 0;
                                                        cancel();
                                                }
                                                break;
                                        default:
                                                indexRun = 0;
                                                cancel();
                                                break;
                                }
                                indexRun++;
                        }


                }.runTaskTimer(SbSpleef.getPlugin(), 20, 20*20);


        }


}
