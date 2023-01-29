package pl.blazestar.velocity.skins.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class MessageConfiguration extends OkaeriConfig {

    private String mongoConnectionString = "";
    private String databaseName = "";
    @Exclude
    private MiniMessage miniMessage = MiniMessage.miniMessage();

    private Component apiNotResponding = miniMessage.deserialize("<red> Api nie odpowiada!");
    private Component notAllowedChars = miniMessage.deserialize("<red> Posiadasz niedozwolone znaki w nicku!");
    private Component badNickNameLength = miniMessage.deserialize("<red> Posiadasz niedozwolona dlugosc nicku!");
    private Component useCorrectNickname =
            miniMessage.deserialize("<red>Uzyj swojego poprawnego nicku. &e{username}</red>");

    private Component reconnect = miniMessage.deserialize("<red> Polacz sie ponownie!");
    public Component getApiNotResponding() {
        return apiNotResponding;
    }

    public Component getBadNickNameLength() {
        return badNickNameLength;
    }

    public Component getNotAllowedChars() {
        return notAllowedChars;
    }

    public Component getUseCorrectNickname() {
        return useCorrectNickname;
    }

    public Component getReconnect() {
        return reconnect;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getMongoConnectionString() {
        return mongoConnectionString;
    }
}
