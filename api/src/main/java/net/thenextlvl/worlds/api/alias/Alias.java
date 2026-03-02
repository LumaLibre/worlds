package net.thenextlvl.worlds.api.alias;

import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Alias {

    String getAlias();

    void setAlias(String alias);

    String getWorldName();

    World getWorld();
}
