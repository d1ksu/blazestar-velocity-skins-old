package pl.blazestar.velocity.skins.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.util.GameProfile;
import pl.blazestar.velocity.skins.configuration.SkinConfiguration;
import pl.blazestar.velocity.skins.profile.Profile;
import pl.blazestar.velocity.skins.profile.ProfileService;
import pl.blazestar.velocity.skins.profile.ProfileSkin;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class GameProfileRequestListener {

    private final ProfileService profileService;

    private final SkinConfiguration skinConfiguration;

    public GameProfileRequestListener(ProfileService profileService, SkinConfiguration skinConfiguration) {
        this.profileService = profileService;
        this.skinConfiguration = skinConfiguration;
    }

    @Subscribe
    public void onRequest(GameProfileRequestEvent event) {
        String username = event.getUsername();
        Optional<Profile> optionalProfile = this.profileService.getProfile(username);
        if (optionalProfile.isEmpty())
            return;
        Profile profile = optionalProfile.get();
        if (profile.isPaid()) {
            ProfileSkin profileSkin = profile.getProfileSkin();
            event.setGameProfile(invokeProfile(profile.getUniqueId(), profile.getNickname(), profileSkin.getTexture(), profileSkin.getSignature()));
        } else if (this.skinConfiguration.isDefaultSkinEnabled()) {
            event.setGameProfile(invokeProfile(profile.getUniqueId(), profile.getNickname(), this.skinConfiguration.getTexture(), this.skinConfiguration.getSignature()));
        }
    }

    private GameProfile invokeProfile(UUID uniqueId, String username, String texture, String signature) {
        return new GameProfile(uniqueId, username,
                Collections.singletonList(new GameProfile.Property("textures", texture, signature)));
    }
}
