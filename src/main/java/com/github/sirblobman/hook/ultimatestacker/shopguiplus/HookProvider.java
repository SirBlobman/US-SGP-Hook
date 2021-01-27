package com.github.sirblobman.hook.ultimatestacker.shopguiplus;

import java.util.Objects;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;

import com.songoda.ultimatestacker.utils.Methods;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.spawner.external.provider.ExternalSpawnerProvider;

public final class HookProvider implements ExternalSpawnerProvider {
    private final HookPlugin plugin;
    public HookProvider(HookPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
    }
    
    public void register() {
        ShopGuiPlusApi.registerSpawnerProvider(this);
    }
    
    @Override
    public String getName() {
        PluginDescriptionFile description = this.plugin.getDescription();
        return description.getPrefix();
    }
    
    @Override
    public ItemStack getSpawnerItem(EntityType type) {
        return Methods.getSpawnerItem(type, 1);
    }
    
    @Override
    public EntityType getSpawnerEntityType(ItemStack item) {
        if(item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if(!(meta instanceof BlockStateMeta)) return null;

        BlockStateMeta stateMeta = (BlockStateMeta) meta;
        BlockState state = stateMeta.getBlockState();
        if(!(state instanceof CreatureSpawner)) return null;

        CreatureSpawner spawner = (CreatureSpawner) state;
        return spawner.getSpawnedType();
    }
}