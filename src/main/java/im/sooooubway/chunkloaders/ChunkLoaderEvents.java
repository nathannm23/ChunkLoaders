package im.sooooubway.chunkloaders;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class ChunkLoaderEvents implements Listener {
    private final ChunkLoaderManager chunkLoaderManager;

    public ChunkLoaderEvents(ChunkLoaderManager chunkLoaderManager) {
        this.chunkLoaderManager = chunkLoaderManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        if (item.getType() == Material.BEACON && meta.hasLore()) {
            List<String> lore = meta.getLore();
            assert lore != null;
            if (lore.get(0).equals(ChunkLoaders.CHUNK_LOADERS_META0)) {
                UUID playerUUID = event.getPlayer().getUniqueId();
                if (chunkLoaderManager.canAddChunkLoader(playerUUID)) {
                    // if the 0th line is "Chunk Loader", then the 1st line is the type
                    String type = lore.get(1);
                    ChunkLoader chunkLoader = new ChunkLoader(type, event.getBlockPlaced().getLocation().toBlockLocation(), event.getPlayer().getUniqueId());
                    chunkLoaderManager.mapChunkLoaderToPlayer(playerUUID, chunkLoader);
                    chunkLoader.loadChunks();
                    chunkLoader.createHologram();
                    event.getPlayer().sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "&a&lChunk Loader placed."));
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "&cYou have already reached the maximum number of active chunk loaders."));
                }
            }
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.BEACON) {
            Location beaconLocation = event.getBlock().getLocation().toBlockLocation();
            ChunkLoader chunkLoader = chunkLoaderManager.getChunkLoaderAt(beaconLocation);
            if (chunkLoader != null) {
                // unload the chunks and remove it from the list
                chunkLoader.removeHologram();
                chunkLoader.unloadChunks();
                chunkLoaderManager.unmapChunkLoaderFromPlayerAndRemove(event.getPlayer().getUniqueId(), chunkLoader);
                event.getPlayer().sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "&a&lChunk Loader removed!"));
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', ChunkLoaders.CHUNK_LOADERS_MENU_TITLE))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            // check for meta before giving any items
            // this is to secure the menu and also stop
            // it from giving the glass placeholders which dont have meta
            if(clickedItem != null && clickedItem.hasItemMeta()){
                if (clickedItem.getType() == Material.BEACON) {
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(clickedItem);
                    } else {
                        player.sendMessage(
                                ChatColor.translateAlternateColorCodes('&', "&c&lYour inventory is full."));
                    }
                }
            }
        }
    }
}
