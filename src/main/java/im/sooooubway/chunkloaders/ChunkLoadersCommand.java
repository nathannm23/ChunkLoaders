package im.sooooubway.chunkloaders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChunkLoadersCommand implements CommandExecutor {
    private final ChunkLoaders plugin;
    public ChunkLoadersCommand(ChunkLoaders plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', ChunkLoaders.CHUNK_LOADERS_MENU_TITLE));
        // slot 3 - 3x3
        // slot 5 - 5x5
        // Add chunk loaders to the inventory
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        placeholder.getItemMeta().setDisplayName(" ");
        for (int i = 0; i < 9; i++) {
            if(i == 3){
                inventory.setItem(i, plugin.createChunkLoaderItem("3x3"));
            }else if(i == 5){
                inventory.setItem(i, plugin.createChunkLoaderItem("5x5"));
            }else {
                // else use a glass pane to fill the slot
                inventory.setItem(i, placeholder);
            }
        }
        player.openInventory(inventory);
        return true;
    }
}