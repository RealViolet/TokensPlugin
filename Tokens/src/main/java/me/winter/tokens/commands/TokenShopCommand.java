package me.winter.tokens.commands;

import me.winter.tokens.menu.TokenShopMenu;
import me.winter.tokens.utils.command.CommandInfo;
import me.winter.tokens.utils.command.CommandWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


@CommandInfo(names = "tokenshop", helpTitle = "Tokens")
public class TokenShopCommand extends CommandWrapper {

    public TokenShopCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            return;
        }
        if (!(sender instanceof Player)) {
            return;
        }
        new TokenShopMenu().openMenu((Player) sender);
    }
}
