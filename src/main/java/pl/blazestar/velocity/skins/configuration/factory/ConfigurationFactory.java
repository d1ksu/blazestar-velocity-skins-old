package pl.blazestar.velocity.skins.configuration.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;
import pl.blazestar.velocity.skins.configuration.transformer.StringToComponentTransformer;

import java.io.File;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public final class ConfigurationFactory {

    private final File dataFile;

    public ConfigurationFactory(File dataFile) {
        this.dataFile = dataFile;
    }

    public <T extends OkaeriConfig> T produceConfig(@NotNull Class<T> clazz, @NotNull String fileName, @NotNull ObjectSerializer<?>... serializers) {
        return this.produceConfig(clazz, new File(this.dataFile, fileName), serializers);
    }

    public <T extends OkaeriConfig> T produceConfig(@NotNull Class<T> clazz, @NotNull File file, @NotNull ObjectSerializer<?>... serializers) {
        return ConfigManager.create(clazz, initializer -> initializer
                .withConfigurer(new YamlSnakeYamlConfigurer(), registry -> {
                    registry.register(new StringToComponentTransformer());
                    for (@NotNull ObjectSerializer<?> serializer : serializers) {
                        registry.register(serializer);
                    }
                })
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }
}
