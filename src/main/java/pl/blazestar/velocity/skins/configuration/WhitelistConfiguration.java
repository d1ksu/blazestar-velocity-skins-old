package pl.blazestar.velocity.skins.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import pl.blazestar.velocity.skins.whitelist.Whitelist;

import java.util.ArrayList;

/**
 * @author d1ksu
 * @Date 24.07.2022
 */
public class WhitelistConfiguration extends OkaeriConfig {


    private Whitelist whitelist = new Whitelist(false, "",  new ArrayList<>());

    public Whitelist getWhitelist() {
        return whitelist;
    }

    @Exclude
    private MiniMessage miniMessage = MiniMessage.miniMessage();

    private Component noReasonWhitelist = miniMessage.deserialize("<red> Na serwerze jest wlaczona whitelista!");

    private Component whitelistSyntaxMessage = miniMessage.deserialize("<red>/whitelist [on/off/status/add/remove] args");

    private String whitelistPermission = "blazestar.proxy.whitelist";

    public Component getWhitelistSyntaxMessage() {
        return whitelistSyntaxMessage;
    }


    public String getWhitelistPermission() {
        return whitelistPermission;
    }

    public Component getNoReasonWhitelist() {
        return noReasonWhitelist;
    }

    public Component getDeserializedReason(){
        return this.miniMessage.deserialize(whitelist.getReason());
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }
}
