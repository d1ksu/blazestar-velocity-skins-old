package pl.blazestar.velocity.skins.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import java.util.*;

public final class PlaceholdersUtils {

    public static Component apply(Component component, Map<String, Object> placeholders) {
        for (Map.Entry<String, Object> entry : placeholders.entrySet())
            component = component.replaceText((TextReplacementConfig) TextReplacementConfig.builder()
                    .matchLiteral("{" + (String) entry.getKey() + "}")
                    .replacement(entry.getValue().toString())
                    .build());
        return component;
    }

}