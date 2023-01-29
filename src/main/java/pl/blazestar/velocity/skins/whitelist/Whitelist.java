package pl.blazestar.velocity.skins.whitelist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author d1ksu
 * @Date 24.07.2022
 */
public class Whitelist {

    private boolean status;
    private String reason;
    private final List<String> players;

    public Whitelist(Boolean status, String  reason, List<String> players){
        this.status = status;
        this.reason = reason;
        this.players = players;

    }

    public boolean isEnabled() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayer(String player){
        this.players.add(player);
    }

    public void removePlayer(String player){
        this.players.remove(player);
    }

    public boolean hasPlayer(String player){
        return this.players.contains(player);
    }
    public boolean hasReason(){
        return !this.reason.equals("");
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
