package pl.blazestar.velocity.skins.profile;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class ProfileSkin {

    private String texture;
    private String signature;

    public ProfileSkin(String texture, String signature) {
        this.texture = texture;
        this.signature = signature;
    }

    public String getTexture() {
        return texture;
    }

    public String getSignature() {
        return signature;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
