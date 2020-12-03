package me.winter.tokens.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TokenConfig {

    private static File file;

    @Getter
    private static FileConfiguration Config;

    @SneakyThrows
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Tokens").getDataFolder(), "tokens.yml");

        if (!file.exists()) {
            file.createNewFile();
        }
        Config = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    public static void save() {
        Config.save(file);
    }


}
