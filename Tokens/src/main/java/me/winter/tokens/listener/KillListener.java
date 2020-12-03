package me.winter.tokens.listener;

import me.winter.tokens.Tokens;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {


    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        if (event.getEntity().getKiller() == null) {
            return;
        }
        Tokens.getInstance().getPlayerTokens().put(event.getEntity().getKiller().getUniqueId()
                , Tokens.getInstance().getPlayerTokens().get(event.getEntity().getKiller().getUniqueId()) + 1);
    }

}
