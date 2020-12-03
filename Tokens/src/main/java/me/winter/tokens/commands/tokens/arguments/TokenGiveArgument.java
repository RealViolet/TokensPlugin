package me.winter.tokens.commands.tokens.arguments;

import me.winter.tokens.Tokens;
import me.winter.tokens.utils.CC;
import me.winter.tokens.utils.command.CommandInfo;
import me.winter.tokens.utils.command.argument.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(
        names = {"give"},
        permission = "winter.tokens.give",
        description = "Gives a player tokens",
        usage = "give <player> <amount>"
)
public class TokenGiveArgument extends CommandArgument {

    private final List<String> sendergive = CC.translate(Tokens.getInstance().getConfig().getStringList("CoralTokens.Messages.Give.Sender"));
    private final List<String> targetgive = CC.translate(Tokens.getInstance().getConfig().getStringList("CoralTokens.Messages.Give.Target"));


    public TokenGiveArgument(Tokens instance) {
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(CC.createUsage(label, "give <player> <amount>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(CC.translate("&cThat player is offline!"));
            return;
        }
        try {
                int a = Tokens.getInstance().getPlayerTokens().get(target.getUniqueId());
                Tokens.getInstance().getPlayerTokens().remove(target.getUniqueId());
                Tokens.getInstance().getPlayerTokens().put(target.getUniqueId(), a + Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.createUsage(label, "give <player> <amount>"));
            return;
        }
        sendergive.forEach(s -> sender.sendMessage(s.replace("%player%", target.getName()).replace("%tokens%", args[2])));
        targetgive.forEach(s -> target.sendMessage(s.replace("%tokens%", args[2])));
    }
}
