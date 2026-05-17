package net.thenextlvl.worlds.alias;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldAliasPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "worlds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "NonSwag";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }


    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        // This could be abstracted/expanded in the future but idc for right now

        final var world = player.getWorld();
        final var alias = new WorldAlias(world);
        String a = alias.getAlias();

        if (a.isBlank()) {
            return alias.getWorldName();
        }
        return a;
    }
}
