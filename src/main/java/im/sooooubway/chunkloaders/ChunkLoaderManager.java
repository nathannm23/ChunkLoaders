package im.sooooubway.chunkloaders;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ChunkLoaderManager {
    private List<ChunkLoader> chunkLoaders = new ArrayList<>();
    private ListIterator<ChunkLoader> chunkLoaderListIterator;

    public void addChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.add(chunkLoader);
    }

    public void removeChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.remove(chunkLoader);
    }

    public ChunkLoader getChunkLoaderAt(Location location) {
        chunkLoaderListIterator = chunkLoaders.listIterator();
        while (chunkLoaderListIterator.hasNext()) {
            ChunkLoader chunkLoader = chunkLoaderListIterator.next();
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
