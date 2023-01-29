package pl.blazestar.velocity.skins.configuration.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;
import pl.blazestar.velocity.skins.whitelist.Whitelist;

import java.util.List;

/**
 * @author d1ksu
 * @Date 24.07.2022
 */
public class WhitelistSerializer implements ObjectSerializer<Whitelist> {
    @Override
    public boolean supports(Class<? super Whitelist> type) {
        return Whitelist.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Whitelist whitelist, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("status", whitelist.isEnabled());
        data.add("reason", whitelist.getReason());
        data.add("players", whitelist.getPlayers());
    }

    @Override
    public Whitelist deserialize(DeserializationData data, GenericsDeclaration generics) {
        boolean status = data.get("status", Boolean.class);
        String reason = data.get("reason", String.class);
        List<String> players = data.getAsList("players", String.class);
        return new Whitelist(status, reason, players);

    }
}
