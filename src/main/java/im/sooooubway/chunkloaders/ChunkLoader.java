package im.sooooubway.chunkloaders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class ChunkLoader {
    private String type;
    private Location location;
    private long expiryTime;
    private Chunk[] chunks;
    private ArmorStand armorStand;

    public ChunkLoader(String type, Location location) {
        this.type = type;
        this.location = location.add(0.0, 1.0, 0.0);
        if(this.type.equals("3x3")){
            this.chunks = new Chunk[9];
            this.expiryTime = ChunkLoaders.THRxTHR_CHUNK_LOADER_EXPIRY;
        }else{
            this.chunks = new Chunk[25];
            this.expiryTime = ChunkLoaders.FIVExFIVE_CHUNK_LOADER_EXPIRY;
        }
        // give the chunk loader an expiration date
        scheduleRemoval(ChunkLoaders.getInstance());
    }

    public void loadChunks() {
        // Load the chunks around the chunk loader
        Chunk chunk = location.getChunk();
        int count = 0;
        if(this.type.equals("3x3")){
            for(int x = -1; x <= 1; x++){
                for(int z = -1; z <= 1; z++){
                    Chunk c = chunk.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
                    c.setForceLoaded(true);
                    chunks[count] = c;
                    count++;
                }
            }
        }else{
            // 5x5
            for(int x = -2; x <= 2; x++){
                for(int z = -2; z <= 2; z++){
                    Chunk c = chunk.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
                    c.setForceLoaded(true);
                    chunks[count] = c;
                    count++;
                }
            }
        }
    }

    public void unloadChunks() {
        // unload the chunks around this chunk loader
        for(Chunk c : chunks){
            if(c != null){
                c.setForceLoaded(false);
            }
        }
    }
    public Location getLocation() {
        return location.toBlockLocation();
    }

    public void createHologram() {
        armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5, -1, 0.5), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        if(type.equals("3x3")){
            armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', ChunkLoaders.THRxTHR_CHUNK_LOADER_NAME));
        }else{
            armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', ChunkLoaders.FIVExFIVE_CHUNK_LOADER_NAME));
        }
    }

    public void removeHologram() {
        if (armorStand != null) {
            armorStand.setCustomNameVisible(false);
            armorStand.remove();
        }
    }
    public void scheduleRemoval(ChunkLoaders plugin) {

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.removeChunkLoader(ChunkLoader.this);
            }
        }, expiryTime * 20L); // expiryTime is in seconds. multiply by 20 bc this parameter is in game ticks
}
}

