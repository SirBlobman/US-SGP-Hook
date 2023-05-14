package com.github.sirblobman.hook.ultimatestacker.shopguiplus;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;

import com.songoda.ultimatestacker.utils.Methods;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.exception.api.ExternalSpawnerProviderNameConflictException;
import net.brcdev.shopgui.spawner.external.provider.ExternalSpawnerProvider;

public final class HookProvider implements ExternalSpawnerProvider {
    private final HookPlugin plugin;

    public HookProvider(@NotNull HookPlugin plugin) {
        this.plugin = plugin;
    }

    private @NotNull HookPlugin getPlugin() {
        return this.plugin;
    }
    
    public void register() {
        try {
            ShopGuiPlusApi.registerSpawnerProvider(this);
        } catch (ExternalSpawnerProviderNameConflictException ex) {
            Logger logger = getPlugin().getLogger();
            logger.log(Level.WARNING, "A spawner provider is already registered for UltimateStacker.");
        }
    }
    
    @Override
    public String getName() {
        HookPlugin plugin = getPlugin();
        PluginDescriptionFile description = plugin.getDescription();
        return description.getPrefix();
    }
    
    @Override
    public ItemStack getSpawnerItem(EntityType type) {
        return Methods.getSpawnerItem(type, 1);
    }
    
    @Override
    public EntityType getSpawnerEntityType(ItemStack item) {
        if(item == null) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if(!(meta instanceof BlockStateMeta)) {
            return null;
        }

        BlockStateMeta stateMeta = (BlockStateMeta) meta;
        BlockState state = stateMeta.getBlockState();
        if(!(state instanceof CreatureSpawner)) {
            return null;
        }

        CreatureSpawner spawner = (CreatureSpawner) state;
        return spawner.getSpawnedType();
    }
}
