package net.thenextlvl.worlds.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.thenextlvl.worlds.WorldsPlugin;
import net.thenextlvl.worlds.alias.WorldAlias;
import net.thenextlvl.worlds.command.brigadier.SimpleCommand;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

import static net.thenextlvl.worlds.command.WorldCommand.worldArgument;

@NullMarked
public class WorldAliasCommand extends SimpleCommand {

    private WorldAliasCommand(final WorldsPlugin plugin) {
        super(plugin, "alias", "worlds.command.alias");
    }


    public static ArgumentBuilder<CommandSourceStack, ?> create(final WorldsPlugin plugin) {
        final var command = new WorldAliasCommand(plugin);
        return command.create()
                .then(worldArgument(plugin)
                        .then(Commands.argument("alias", StringArgumentType.greedyString())
                                .executes(command))
                        .executes(command))
                .executes(command);
    }

    @Override
    public int run(final CommandContext<CommandSourceStack> context) {
        final var sender = context.getSource().getSender();
        final var world = tryGetArgument(context, "world", World.class)
                .orElseGet(() -> context.getSource().getLocation().getWorld());
        final var alias = tryGetArgument(context, "alias", String.class)
                .orElse(null);

        final var worldAlias = new WorldAlias(world);

        if (alias == null) {
            plugin.bundle().sendMessage(sender, "world.alias.info",
                    Placeholder.parsed("world", worldAlias.getWorldName()),
                    Placeholder.parsed("alias", worldAlias.getAlias()));
        } else {
            worldAlias.setAlias(alias);
            plugin.bundle().sendMessage(sender, "world.alias.set",
                    Placeholder.parsed("world", worldAlias.getWorldName()),
                    Placeholder.parsed("alias", worldAlias.getAlias()));
        }
        return SINGLE_SUCCESS;
    }
}
