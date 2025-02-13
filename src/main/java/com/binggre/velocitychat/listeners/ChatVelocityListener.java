package com.binggre.velocitychat.listeners;

import com.binggre.velocitysocketclient.listener.VelocitySocketListener;
import com.binggre.velocitysocketclient.socket.SocketResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatVelocityListener extends VelocitySocketListener {

    @Override
    public void onReceive(String[] messages) {
        String string = messages[0];
        String legacyMessage = messages[1];
        Component deserialize = JSONComponentSerializer.json().deserialize(string);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(deserialize);
        }
        Bukkit.getConsoleSender().sendMessage(legacyMessage);
    }

    @Override
    public @NotNull SocketResponse onRequest(String... strings) {
        return SocketResponse.empty();
    }

    @Override
    public void onResponse(SocketResponse socketResponse) {

    }
}
