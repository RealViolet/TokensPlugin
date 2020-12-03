package me.winter.tokens;

import lombok.Getter;
import me.winter.tokens.listener.JoinListener;
import me.winter.tokens.listener.KillListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.winter.tokens.commands.TokenShopCommand;
import me.winter.tokens.commands.tokens.TokenCommand;
import me.winter.tokens.utils.TokenConfig;
import me.winter.tokens.utils.menu.MenuListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Tokens extends JavaPlugin {

    @Getter
    private static Tokens instance;

    @Getter
    public HashMap<UUID, Integer> playerTokens;

    @Override
    public void onEnable() {
        instance = this;
        playerTokens = new HashMap<>();
        saveDefaultConfig();
        setupConfig();
        new TokenCommand(this);
        new TokenShopCommand(this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        List<String> s = TokenConfig.getConfig().getStringList("Tokens");
        for (String str : s) {
            String[] words = str.split(":");
            playerTokens.put(UUID.fromString(words[0]), Integer.valueOf(words[1]));
        }
        TokenConfig.getConfig().set("Tokens", null);
    }

    @Override
    public void onDisable() {
        if (!playerTokens.isEmpty()) {
            saveTokens();
        }
    }

    private void setupConfig() {
        TokenConfig.setup();
        TokenConfig.getConfig().addDefault("Tokens", null);
        TokenConfig.getConfig().options().copyDefaults(true);
    }

    public void saveTokens() {
        List<String> s = TokenConfig.getConfig().getStringList("Tokens");
        for (UUID u : playerTokens.keySet()) {
            s.add(u + ":" + playerTokens.get(u));
        }
        TokenConfig.getConfig().set("Tokens", s);
        TokenConfig.save();
    }
}

