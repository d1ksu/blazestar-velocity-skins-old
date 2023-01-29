package pl.blazestar.velocity.skins.listener;

import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import net.kyori.adventure.text.Component;
import pl.blazestar.velocity.skins.authentication.AuthenticationService;
import pl.blazestar.velocity.skins.configuration.MessageConfiguration;
import pl.blazestar.velocity.skins.configuration.WhitelistConfiguration;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;
import pl.blazestar.velocity.skins.profile.ProfileSkin;
import pl.blazestar.velocity.skins.utils.PlaceholdersUtils;
import pl.blazestar.velocity.skins.whitelist.Whitelist;

import java.awt.*;
import java.util.Map;
import java.util.Optional;


public class PreLoginListener {
    private final ProfileService profileService;

    private final MessageConfiguration messageConfiguration;

    private final WhitelistConfiguration whitelistConfiguration;

    public PreLoginListener(ProfileService profileService,
                            MessageConfiguration messageConfiguration,
                            WhitelistConfiguration whitelistConfiguration) {
        this.profileService = profileService;
        this.messageConfiguration = messageConfiguration;
        this.whitelistConfiguration = whitelistConfiguration;
    }

    @Subscribe()
    public EventTask onPreLogin(PreLoginEvent event) {
        return EventTask.async(() -> {
            if(!event.getResult().isAllowed()){
                return;
            }
            String nickname = event.getUsername();
            Whitelist whitelist = whitelistConfiguration.getWhitelist();
            if(whitelist.isEnabled()){
                if(!whitelist.hasPlayer(nickname)){
                    if(whitelist.hasReason()){
                        event.setResult(PreLoginEvent.PreLoginComponentResult.denied(this.whitelistConfiguration.getDeserializedReason()));
                    } else {
                        event.setResult(PreLoginEvent.PreLoginComponentResult.denied(this.whitelistConfiguration.getNoReasonWhitelist()));
                    }
                    return;
                }
            }
            if (nickname.length() < 3 || nickname.length() > 16) {
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(this.messageConfiguration.getBadNickNameLength()));
                return;
            }
            if (!nickname.matches("[a-zA-Z0-9_]+")) {
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(this.messageConfiguration.getNotAllowedChars()));
                return;
            }
            Optional<Profile> optionalProfile = this.profileService.getProfile(nickname);
            if (optionalProfile.isEmpty()) {
                Optional<Profile> createdOptional = this.profileService.createProfile(nickname);
                if (createdOptional.isEmpty()) {
                    event.setResult(PreLoginEvent.PreLoginComponentResult.denied(this.messageConfiguration.getApiNotResponding()));
                    return;
                }
                event.setResult(invokeLogin(createdOptional.get(), nickname));
            } else {
                event.setResult(invokeLogin(optionalProfile.get(), nickname));
            }
        });
    }

    private PreLoginEvent.PreLoginComponentResult invokeLogin(Profile profile, String username) {
        if (profile.getNickname().equalsIgnoreCase(username) && !profile.getNickname().equals(username))
            return PreLoginEvent.PreLoginComponentResult.denied(PlaceholdersUtils.apply(this.messageConfiguration.getUseCorrectNickname(), Map.of("nickname", profile
                    .getNickname())));
        if (profile.isPaid())
            return PreLoginEvent.PreLoginComponentResult.forceOnlineMode();
        return PreLoginEvent.PreLoginComponentResult.forceOfflineMode();
    }
}