package me.winter.tokens.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.winter.tokens.Tokens;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Tokens.getInstance().getPlayerTokens().containsKey(event.getPlayer().getUniqueId())) {
            return;
        } else {
            Tokens.getInstance().getPlayerTokens().put(event.getPlayer().getUniqueId(), 0);
        }
    }

}
