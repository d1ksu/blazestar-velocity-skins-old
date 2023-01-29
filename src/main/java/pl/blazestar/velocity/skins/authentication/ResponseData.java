package pl.blazestar.velocity.skins.authentication;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class ResponseData {

    private boolean connected;

    private UUID uniqueId;

    private boolean paid;

    private String texture;

    private String signature;

    public ResponseData(boolean connected, UUID uniqueId, boolean paid, String texture, String signature) {
        this.connected = connected;
        this.uniqueId = uniqueId;
        this.paid = paid;
        this.texture = texture;
        this.signature = signature;
    }

    @Nullable
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getTexture() {
        return this.texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public static ResponseData notResponse() {
        return new ResponseData(false, null, false, null, null);
    }
}
