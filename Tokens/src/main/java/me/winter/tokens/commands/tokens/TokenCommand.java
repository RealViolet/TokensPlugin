package me.winter.tokens.commands.tokens;

import me.winter.tokens.Tokens;
import me.winter.tokens.commands.tokens.arguments.TokenGiveArgument;
import me.winter.tokens.commands.tokens.arguments.TokenRemoveArgument;
import me.winter.tokens.utils.command.CommandInfo;
import me.winter.tokens.utils.command.argument.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.winter.tokens.commands.tokens.arguments.TokenCheckArgument;

@CommandInfo(names = {"tokens"}, permission = "winter.tokens", helpTitle = "Tokens")
public class TokenCommand extends CommandExecutor {


    public TokenCommand(Tokens instance) {
        super(instance);
        this.addArgument(new TokenCheckArgument(instance));
        this.addArgument(new TokenGiveArgument(instance));
        this.addArgument(new TokenRemoveArgument(instance));
    }

    @Override
    public boolean executeOther(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
