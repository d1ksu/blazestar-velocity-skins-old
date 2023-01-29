package pl.blazestar.velocity.skins.command.whitelist;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import pl.blazestar.velocity.skins.configuration.WhitelistConfiguration;
import pl.blazestar.velocity.skins.whitelist.Whitelist;

/**
 * @author d1ksu
 * @Date 24.07.2022
 */
public class WhitelistCommand implements SimpleCommand {

    private final WhitelistConfiguration whitelistConfiguration;

    public WhitelistCommand(WhitelistConfiguration whitelistConfiguration) {
        this.whitelistConfiguration = whitelistConfiguration;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();
        String[] arguments = invocation.arguments();
        Whitelist whitelist = whitelistConfiguration.getWhitelist();
        if(arguments.length < 1) {
            commandSource.sendMessage(whitelistConfiguration.getWhitelistSyntaxMessage());
            return;
        }
        if(arguments.length == 1){
            switch (arguments[0]) {
                case "on" -> {
                    whitelist.setStatus(true);
                    commandSource.sendMessage(Component.text("Wlaczyles whiteliste!"));
                }
                case "off" -> {
                    whitelist.setStatus(false);
                    commandSource.sendMessage(Component.text("Wylaczyles whiteliste!"));
                }
                default -> commandSource.sendMessage(whitelistConfiguration.getWhitelistSyntaxMessage());
            }
        }
        if(arguments.length == 2){
            if(arguments[1] == null){
                commandSource.sendMessage(whitelistConfiguration.getWhitelistSyntaxMessage());
                return;
            }
            switch (arguments[0]){
                case "add" -> {
                    whitelist.addPlayer(arguments[1]);
                    commandSource.sendMessage(Component.text("Dodales gracza "+ arguments[1]
                            +" do whitelisty!"));
                }
                case "remove" -> {
                    whitelist.removePlayer(arguments[1]);
                    commandSource.sendMessage(Component.text("Usunales gracza "+ arguments[1]
                            +" z whitelisty!"));
                }
                case "removeall" -> {
                    whitelist.getPlayers().clear();
                    commandSource.sendMessage(Component.text("Wyczysciles liste graczy " +
                            "z whitelisty!"));
                }
                case "status" -> {
                    if(arguments[1].equalsIgnoreCase("clear")){
                        whitelist.setReason("");
                        commandSource.sendMessage(Component.text("Wyczysciles powod whitelisty!"));
                        return;
                    }
                    whitelist.setReason(arguments[1]);
                    commandSource.sendMessage(Component.text("Ustawiles status whitelisty " +
                            "na "
                            + arguments[1]));
                }
                default ->  commandSource.sendMessage(whitelistConfiguration.getWhitelistSyntaxMessage());
            }
        }
        whitelistConfiguration.save();

    }

    @Override
    public boolean hasPermission(Invocation invocation){
        return invocation.source().hasPermission(whitelistConfiguration.getWhitelistPermission());
    }


}
