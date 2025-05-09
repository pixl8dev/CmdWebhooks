package me.olliejw.cmdwebhooks.Events;

import me.olliejw.cmdwebhooks.SendWebhook;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class OnMsg implements Listener {
    final String url;
    final String message;

    public OnMsg(String url, String message) {
        this.url = url;
        this.message = message;
    }

    @EventHandler
    public void onMsg(AsyncPlayerChatEvent event) {
        String pl = event.getPlayer().getDisplayName();
        SendWebhook webhook = new SendWebhook(this.url);
        String toSend = String.format(this.message, event.getPlayer().getDisplayName())
                .replace("[player]", ChatColor.stripColor(pl))
                .replace("[msg]", event.getMessage());
        webhook.setContent(toSend);

        try {
            webhook.executeAsync();
        } catch (Exception e) {
            System.out.println("[CmdWebhook] Error sending Webhook!");
            e.printStackTrace();
        }
    }
}
