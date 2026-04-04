package net.thenextlvl.worlds.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.thenextlvl.worlds.command.brigadier.ComponentCommandExceptionType;
import org.bukkit.Registry;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@NullMarked
public final class BiomeArgument implements SimpleArgumentType<Key, String> {
    private static final ComponentCommandExceptionType ERROR_INVALID = new ComponentCommandExceptionType(
            Component.translatable("argument.id.invalid")
    );

    public BiomeArgument() {
    }

    @Override
    @SuppressWarnings("PatternValidation")
    public Key convert(final StringReader reader, final String value) throws CommandSyntaxException {
        try {
            return value.contains(":") ? Key.key(value) : Key.key("minecraft", value);
        } catch (final InvalidKeyException e) {
            throw ERROR_INVALID.create();
        }
    }

    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        Registry.BIOME.stream()
                .map(biome -> biome.getKey().asString())
                .filter(s -> s.toLowerCase(Locale.ROOT).contains(builder.getRemainingLowerCase()))
                .map(StringArgumentType::escapeIfRequired)
                .forEach(builder::suggest);
        return builder.buildFuture();
    }
}
