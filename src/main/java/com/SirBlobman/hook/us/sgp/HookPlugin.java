package com.SirBlobman.hook.us.sgp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HookPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("Enabling UltimateStacker ShopGUIPlus Hook...");
        
        if(checkHook("ShopGUIPlus")) {
            logger.warning("Could not find the ShopGUIPlus plugin.");
            setEnabled(false);
            return;
        }
        
        if(checkHook("UltimateStacker")) {
            logger.warning("Could not find the UltimateStacker plugin.");
            setEnabled(false);
            return;
        }
        
        HookProvider hookProvider = new HookProvider(this);
        hookProvider.register();
        
        logger.info("Successfully enabled UltimateStacker ShopGUIPlus Hook.");
    }
    
    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("Disabling UltimateStacker ShopGUIPlus Hook...");
        logger.info("Successfully disabled UltimateStacker ShopGUIPlus Hook.");
    }
    
    private boolean checkHook(String pluginName) {
        PluginManager manager = Bukkit.getPluginManager();
        Plugin plugin = manager.getPlugin(pluginName);
        if(plugin == null || !plugin.isEnabled()) return true;
    
        PluginDescriptionFile description = plugin.getDescription();
        String fullName = description.getFullName();
        
        Logger logger = getLogger();
        logger.info("Successfully hooked into plugin '" + fullName + "'.");
        return false;
    }
}