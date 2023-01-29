package pl.blazestar.velocity.skins.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import pl.blazestar.velocity.skins.configuration.MessageConfiguration;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;
import pl.blazestar.velocity.skins.profile.ProfileSkin;

import java.util.Optional;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class ServerPostConnectListener {

    private final ProfileService profileService;
    private final MessageConfiguration messageConfiguration;

    public ServerPostConnectListener(ProfileService profileService, MessageConfiguration messageConfiguration) {
        this.profileService = profileService;
        this.messageConfiguration = messageConfiguration;
    }

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event){
        Player player = event.getPlayer();
        Optional<Profile> optionalProfile = this.profileService.getProfile(player.getUsername());
        if (optionalProfile.isEmpty()) {
            player.disconnect(messageConfiguration.getReconnect());
            return;
        }
        Profile profile = optionalProfile.get();
    }


}
