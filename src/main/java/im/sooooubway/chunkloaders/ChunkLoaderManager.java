package im.sooooubway.chunkloaders;

import org.bukkit.Location;

import java.util.*;

public class ChunkLoaderManager {
    private final List<ChunkLoader> chunkLoaders = new ArrayList<>();
    private final HashMap<UUID, Integer> playerChunkLoaders;
    public ChunkLoaderManager() {
        playerChunkLoaders = new HashMap<>();
    }
    public boolean canAddChunkLoader(UUID playerUUID) {
        int currentCount = playerChunkLoaders.getOrDefault(playerUUID, 0);
        return currentCount < ChunkLoaders.MAX_CHUNK_LOADERS_PER_PLAYER;
    }
    public void addChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.add(chunkLoader);
    }
    public void mapChunkLoaderToPlayer(UUID playerUUID, ChunkLoader chunkLoader) {
        int currentCount = playerChunkLoaders.getOrDefault(playerUUID, 0);
        playerChunkLoaders.put(playerUUID, currentCount + 1);
        addChunkLoader(chunkLoader);
    }
    public void unmapChunkLoaderFromPlayerAndRemove(UUID playerUUID, ChunkLoader chunkLoader) {
        int currentCount = playerChunkLoaders.getOrDefault(playerUUID, 0);
        playerChunkLoaders.put(playerUUID, currentCount - 1);
        removeChunkLoader(chunkLoader);
    }

    public void removeChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.remove(chunkLoader);
    }

    public ChunkLoader getChunkLoaderAt(Location location) {
        for (ChunkLoader chunkLoader : chunkLoaders) {
            Location chunkLoaderLocation = chunkLoader.getLocation();
            if (chunkLoaderLocation.equals(location)) {
                return chunkLoader;
            }
        }
        return null;
    }

    //TODO: Save and load chunk loaders using GSON probably
    public void saveChunkLoaders() {
        // Save the chunk loaders to a file
    }

    public void loadChunkLoaders() {
        // Load the chunk loaders from a file
    }
}
