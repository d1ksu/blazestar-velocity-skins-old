package pl.blazestar.velocity.skins.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.DisconnectEvent.LoginStatus;
import com.velocitypowered.api.proxy.Player;
import pl.blazestar.velocity.skins.database.Database;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;

import java.util.Optional;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class DisconnectListener {

    private final ProfileService profileService;
    private final Database database;

    public DisconnectListener(ProfileService profileService, Database database){
        this.profileService = profileService;
        this.database = database;
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event){
        Player player = event.getPlayer();
        LoginStatus loginStatus = event.getLoginStatus();
        if(loginStatus == LoginStatus.SUCCESSFUL_LOGIN || loginStatus == LoginStatus.PRE_SERVER_JOIN){
            Optional<Profile> optionalProfile = profileService.getProfile(player.getUniqueId());
            if(optionalProfile.isEmpty()){
                return;
            }
            Profile profile = optionalProfile.get();
            database.update(profile, "uniqueId", profile.getUniqueId().toString());
        }
    }
}
