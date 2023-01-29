package pl.blazestar.velocity.skins.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import pl.blazestar.velocity.skins.configuration.MessageConfiguration;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;

import java.util.Optional;

/**
 * @author d1ksu
 * @Date 25.07.2022
 */
public class PlayerChooseInitialServerListener {

    private final ProfileService profileService;
    private final MessageConfiguration messageConfiguration;

    public PlayerChooseInitialServerListener(ProfileService profileService, MessageConfiguration messageConfiguration){
        this.profileService = profileService;
        this.messageConfiguration = messageConfiguration;
    }

    @Subscribe
    public void onPlayerChooseInitialServer(PlayerChooseInitialServerEvent event) {
        Player player = event.getPlayer();
        Optional<Profile> optionalProfile = profileService.getProfile(player.getUniqueId());
        if(optionalProfile.isEmpty()){
            player.disconnect(messageConfiguration.getReconnect());
            return;
        }
        Profile profile = optionalProfile.get();
    }
}
