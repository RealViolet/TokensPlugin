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
        names = {"check"},
        permission = "winter.tokens.check",
        description = "Check a players tokens",
        usage = "check <player>"
)
public class TokenCheckArgument extends CommandArgument {

    private final List<String> checkmessage = CC.translate(Tokens.getInstance().getConfig().getStringList("CoralTokens.Messages.Check"));

    public TokenCheckArgument(Tokens instance) {
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(CC.createUsage(label, "check <player>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(CC.translate("&cThat player is offline!"));
            return;
        }

        checkmessage.forEach(s -> sender.sendMessage(s.replace("%player%", target.getName()).replace("%tokens%", String.valueOf(Tokens.getInstance().getPlayerTokens().get(target.getUniqueId())))));
    }
}
