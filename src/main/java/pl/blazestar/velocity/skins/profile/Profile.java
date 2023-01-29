package pl.blazestar.velocity.skins.profile;

import pl.blazestar.velocity.skins.database.annotation.Insertable;

import java.util.UUID;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
@Insertable(collection = "profiles-skins")
public class Profile {

    private String nickname;
    private UUID uniqueId;
    private ProfileSkin profileSkin;
    private boolean paid;

    public Profile(String nickname, UUID uniqueId, boolean paid, ProfileSkin profileSkin){
        this.nickname = nickname;
        this.uniqueId = uniqueId;
        this.profileSkin = profileSkin;
        this.paid = paid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ProfileSkin getProfileSkin() {
        return profileSkin;
    }

    public void setProfileSkin(ProfileSkin profileSkin) {
        this.profileSkin = profileSkin;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
