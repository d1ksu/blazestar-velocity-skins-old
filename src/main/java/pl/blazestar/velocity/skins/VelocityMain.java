package pl.blazestar.velocity.skins;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import pl.blazestar.velocity.skins.authentication.AuthenticationService;
import pl.blazestar.velocity.skins.command.whitelist.WhitelistCommand;
import pl.blazestar.velocity.skins.configuration.MessageConfiguration;
import pl.blazestar.velocity.skins.configuration.SkinConfiguration;
import pl.blazestar.velocity.skins.configuration.WhitelistConfiguration;
import pl.blazestar.velocity.skins.configuration.factory.ConfigurationFactory;
import pl.blazestar.velocity.skins.configuration.serializer.WhitelistSerializer;
import pl.blazestar.velocity.skins.database.Database;
import pl.blazestar.velocity.skins.listener.*;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;

import java.io.File;
import java.nio.file.Path;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
@Plugin(
        id = "blazestar-velocity-skins",
        name = "blazestar-velocity-skins",
        version = "1.0",
        authors = "d1ksu"
)
public class VelocityMain {

    private static VelocityMain instance;
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File dataDirectory;

    private ProfileService profileService;
    private Database database;


    @Inject
    public VelocityMain(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory){
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory.toFile();
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event){
        instance = this;
        ConfigurationFactory configurationFactory = new ConfigurationFactory(dataDirectory);
        SkinConfiguration skinConfiguration =
                configurationFactory.produceConfig(SkinConfiguration.class, "skinConfiguration.yml");
        MessageConfiguration messageConfiguration =
                configurationFactory.produceConfig(MessageConfiguration.class, "messages.yml");
        WhitelistConfiguration whitelistConfiguration =
                configurationFactory.produceConfig(WhitelistConfiguration.class, "whitelist.yml",
                        new WhitelistSerializer());
        this.database = new Database(
                messageConfiguration.getMongoConnectionString(),
                messageConfiguration.getDatabaseName());
        AuthenticationService authenticationService = new AuthenticationService(logger, skinConfiguration);
        this.profileService = new ProfileService(authenticationService, database, logger);
        profileService.loadAll();
        EventManager eventManager = proxyServer.getEventManager();
        eventManager.register(this, new GameProfileRequestListener(profileService, skinConfiguration));
        eventManager.register(this, new PlayerChooseInitialServerListener(profileService, messageConfiguration));
        eventManager.register(this, new ServerPostConnectListener(profileService, messageConfiguration));
        eventManager.register(this,
                new PreLoginListener(profileService, messageConfiguration, whitelistConfiguration));
        eventManager.register(this, new DisconnectListener(profileService, database));

        CommandManager commandManager = proxyServer.getCommandManager();
        commandManager.register(commandManager.metaBuilder("proxywhitelist")
                .aliases("proxywl")
                .build(), new WhitelistCommand(whitelistConfiguration));

    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        for(Profile profile : profileService.getProfiles().values()){
            database.update(profile, "uniqueId", profile.getUniqueId().toString());
        }
    }
    public static VelocityMain getInstance() {
        return instance;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public Logger getLogger() {
        return logger;
    }
}
