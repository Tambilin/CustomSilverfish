package me.tambilin.customsilverfish;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.util.config.Configuration;

/**
 * CustomSilverfish Project.
 * CustomSilverfish.class
 * - Main Class for handling the plugin settings.
 * @author Tambilin
 */
public class CustomSilverfish extends JavaPlugin{
    
    public static CustomSilverfish plugin;
    public final Logger logger = Logger.getLogger("Minecraft");
    //The configation variable
    //Variables that correspond to user defined data.
    public FileConfiguration config;
    public Boolean configBoolean;
    public String configString;
    

    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " is now disabled.");
    }
    
    @Override
    public void onEnable(){       
        try {
            config = getConfig();
            File CustomSilverfish = new File(getDataFolder(), "config.yml");
            if (!config.contains("general.Info")) {
                config.set("general.Info", "#Configuartion File For CustomSilverfish. Block Format: BlockID:SubID/PercentageSpawn/NumberOfFish(1-5)#.. e.g 1/100/2#3/20/1 (SmoothStone Blocks Spawn 2 Silverfish 100% of the time and Dirt Blocks Spawn 1 20% of the time.)");
            }
            if (!config.contains("general.Blocks")) {
                config.set("general.Blocks", "1/100/2#3/20/1#98/50/3#");
            }
            if (!config.contains("general.Permissions")) {
                config.set("general.Permissions", false);
            }
            saveConfig();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        configString = config.getString("general.Blocks");
        configBoolean = config.getBoolean("general.Permissions");
        
        
        PluginManager pm = getServer().getPluginManager();
        getServer().getPluginManager().registerEvents(new ServerBlockChangeListener(this, configString, configBoolean), this);
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " is now enabled.");
    }
    
    
    public static void main(String[] args) {
        //Do Nothing. Netbeans IDE Testing Use Only!
    }

}
