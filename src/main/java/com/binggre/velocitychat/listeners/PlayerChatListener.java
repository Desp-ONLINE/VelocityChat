package com.binggre.velocitychat.listeners;

import com.binggre.velocitychat.VelocityChat;
import com.binggre.velocitychat.config.ChatConfig;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.signedMessage().message();
        ChatConfig chatConfig = VelocityChat.getPlugin().getChatConfig();

        String nicknameFormat = PlaceholderAPI.setPlaceholders(player, chatConfig.getNicknameFormat());
        String userFormat = PlaceholderAPI.setPlaceholders(player, chatConfig.getMessageFormat());
        List<String> infoFormat = PlaceholderAPI.setPlaceholders(player, chatConfig.getInfoFormat());

        TextComponent textComponent = Component.empty();
        TextComponent nicknameComponent = Component.text(nicknameFormat.replace("%player%", player.getName()));
        TextComponent messageComponent = Component.text(userFormat.replace("%message%", message));

        nicknameComponent = nicknameComponent.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(String.join("\n", infoFormat))));

        messageComponent = messageComponent.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("클릭 시 귓속말을 할 수 있습니다.")));
        messageComponent = messageComponent.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/귓 " + player.getName() + " "));
        textComponent = textComponent.append(nicknameComponent).append(messageComponent);

        String json = JSONComponentSerializer.json().serialize(textComponent);
        String legacyMessage = LegacyComponentSerializer.legacySection().serialize(textComponent);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(textComponent);
        }
        Bukkit.getConsoleSender().sendMessage(legacyMessage);
        VelocityChat.getPlugin().getSocketClient().send(ChatVelocityListener.class, json, legacyMessage);
    }
}