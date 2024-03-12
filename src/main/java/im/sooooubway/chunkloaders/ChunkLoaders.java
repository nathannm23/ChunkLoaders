package im.sooooubway.chunkloaders;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChunkLoaders extends JavaPlugin {
    private static ChunkLoaders instance;
    private ChunkLoaderManager chunkLoaderManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        chunkLoaderManager = new ChunkLoaderManager();
        getServer().getPluginManager().registerEvents(new ChunkLoaderEvents(chunkLoaderManager), this);
        Objects.requireNonNull(this.getCommand("chunkloaders")).setExecutor(new ChunkLoadersCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // utility stuff here to keep the class count down
    public static final String CHUNK_LOADERS_MENU_TITLE = "&d&lChunk Loaders";
    public static final String THRxTHR_CHUNK_LOADER_NAME = "&a&l3x3 loader";
    public static final String FIVExFIVE_CHUNK_LOADER_NAME = "&a&l5x5 loader";
    public static final int THRxTHR_CHUNK_LOADER_EXPIRY = 60*60*3; // 3 hours in seconds
    public static final int FIVExFIVE_CHUNK_LOADER_EXPIRY = 60*60; // 1 hour in seconds
    public static final String CHUNK_LOADERS_META0 = "Chunk Loader";



    public ItemStack createChunkLoaderItem(String type) {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', CHUNK_LOADERS_META0));
        lore.add(type);
        if(type.equals("3x3")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', THRxTHR_CHUNK_LOADER_NAME));
            int hours = (int)THRxTHR_CHUNK_LOADER_EXPIRY / 60 / 60;
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fExpires in " +hours + " &fhour(s)"));
        } else if(type.equals("5x5")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', FIVExFIVE_CHUNK_LOADER_NAME));
            int hours = (int)FIVExFIVE_CHUNK_LOADER_EXPIRY / 60 / 60;
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fExpires in " +hours + " &fhour(s)"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public void removeChunkLoader(ChunkLoader chunkLoader) {
    chunkLoader.unloadChunks();
    chunkLoader.removeHologram();
    chunkLoader.getLocation().getBlock().setType(Material.AIR);
    chunkLoaderManager.removeChunkLoader(chunkLoader);
    }
    public static ChunkLoaders getInstance() {
        return instance;
    }
}
