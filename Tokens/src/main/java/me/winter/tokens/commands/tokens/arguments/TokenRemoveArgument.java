package me.winter.tokens.commands.tokens.arguments;

import me.winter.tokens.utils.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.winter.tokens.Tokens;
import me.winter.tokens.utils.CC;
import me.winter.tokens.utils.command.argument.CommandArgument;

@CommandInfo(
        names = {"remove"},
        permission = "winter.tokens.give",
        description = "Remvoes a player tokens",
        usage = "remove <player> <amount>"
)
public class TokenRemoveArgument extends CommandArgument {

    public TokenRemoveArgument(Tokens instance) {

    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(CC.createUsage(label, "remove <player> <amount>"));
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
            Tokens.getInstance().getPlayerTokens().put(target.getUniqueId(), a - Integer.parseInt(args[2]));
            sender.sendMessage(CC.translate("&6&l" + target.getName() + " &cnow has &6&l" + Tokens.getInstance().getPlayerTokens().get(target.getUniqueId()) + "&c tokens. "));
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.createUsage(label, "remove <player> <amount>"));
            return;
        }

    }
}
