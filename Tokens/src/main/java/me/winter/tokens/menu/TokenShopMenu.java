package me.winter.tokens.menu;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import me.winter.tokens.Tokens;
import me.winter.tokens.utils.CC;
import me.winter.tokens.utils.ItemBuilder;
import me.winter.tokens.utils.menu.Button;
import me.winter.tokens.utils.menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenShopMenu extends Menu {

    private final List<String> notenoughtokens = CC.translate(Tokens.getInstance().getConfig().getStringList("CoralTokens.Messages.NotEnoughTokens"));
    private final List<String> spenttokens = CC.translate(Tokens.getInstance().getConfig().getStringList("CoralTokens.Messages.Spent"));

    @Override
    public String getTitle(Player player) {
        return CC.translate(Tokens.getInstance().getConfig().getString("Shop.Gui.Name"));
    }

    @Override
    public boolean isPlaceholder() {
        return Tokens.getInstance().getConfig().getBoolean("Shop.Gui.PlaceholderItem");
    }

    @Override
    public int getSize() {
        return Tokens.getInstance().getConfig().getInt("Shop.Gui.Size");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(Tokens.getInstance().getConfig().getInt("Shop.Gui.TokenAmountItem.Slot") - 1, getTokenAmount());
        for (String key : Tokens.getInstance().getConfig().getConfigurationSection("Shop.Gui.Items").getKeys(false)) {
            List<String> commands = Tokens.getInstance().getConfig().getStringList("Shop.Gui.Items." + key + ".Commands");
            String name = CC.translate(Tokens.getInstance().getConfig().getString("Shop.Gui.Items." + key + ".Item.Name"));
            int playertokens = Tokens.getInstance().getPlayerTokens().get(player.getUniqueId());
            int cost = Tokens.getInstance().getConfig().getInt("Shop.Gui.Items." + key + ".Cost");
            List<String> lore = CC.translate(Tokens.getInstance().getConfig().getStringList("Shop.Gui.Items." + key + ".Item.Lore"));
            buttons.put(Tokens.getInstance().getConfig().getInt("Shop.Gui.Items." + key + ".Slot"), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.valueOf(Tokens.getInstance().getConfig().getString("Shop.Gui.Items." + key + ".Item.Material"))).name(name).lore(lore).build();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    if (playertokens < cost) {
                        for (String notenoughtoken : notenoughtokens) {
                            player.sendMessage(notenoughtoken.replace("%tokens%", String.valueOf(playertokens)));
                        }
                        player.closeInventory();
                        return;
                    }
                    for (String command : commands) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                    }
                    Tokens.getInstance().getPlayerTokens().put(player.getUniqueId(), playertokens - cost);
                    for (String spenttoken : spenttokens) {
                        player.sendMessage(spenttoken.replace("%item%", getButtonItem(player).getItemMeta().getDisplayName()).replace("%tokens%"
                                , Tokens.getInstance().getPlayerTokens().get(player.getUniqueId()).toString()));
                    }
                    player.closeInventory();
                }
            });
        }

        return buttons;
    }

    public Button getTokenAmount() {
        return new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                List<String> stringList = Tokens.getInstance().getConfig().getStringList("Shop.Gui.TokenAmountItem.Item.Lore");
                List<String> replaced = new ArrayList<>();
                for (String s : stringList) {
                    replaced.add(s.replace("%tokens%", Tokens.getInstance().getPlayerTokens().get(player.getUniqueId()).toString()));
                }
                return new ItemBuilder(Material.valueOf(Tokens.getInstance().getConfig().getString("Shop.Gui.TokenAmountItem.Item.Material")))
                        .lore(replaced)
                        .name(Tokens.getInstance().getConfig().getString("Shop.Gui.TokenAmountItem.Item.Name"))
                        .build();
            }
        };
    }
}
