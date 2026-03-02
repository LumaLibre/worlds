package net.thenextlvl.worlds.alias;

import net.thenextlvl.worlds.api.alias.Alias;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import static org.bukkit.persistence.PersistentDataType.STRING;

@NullMarked
public class WorldAlias implements Alias {

    private static final NamespacedKey ALIAS_KEY = new NamespacedKey("worlds", "alias");

    private final World world;

    private @Nullable String cachedAlias;

    public WorldAlias(World world) {
        this.world = world;
    }

    @Override
    public String getAlias() {
        if (cachedAlias != null) {
            return cachedAlias;
        }
        String result = world.getPersistentDataContainer().get(ALIAS_KEY, STRING);
        cachedAlias = result != null ? result : "";
        return cachedAlias;
    }

    @Override
    public void setAlias(String alias) {
        world.getPersistentDataContainer().set(ALIAS_KEY, STRING, alias);
        cachedAlias = alias;
    }

    @Override
    public String getWorldName() {
        return world.getName();
    }

    @Override
    public World getWorld() {
        return world;
    }
}
