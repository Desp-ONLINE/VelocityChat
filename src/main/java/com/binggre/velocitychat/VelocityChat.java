package com.binggre.velocitychat;

import com.binggre.binggreapi.BinggrePlugin;
import com.binggre.velocitychat.commands.AdminCommand;
import com.binggre.velocitychat.config.ChatConfig;
import com.binggre.velocitychat.listeners.ChatVelocityListener;
import com.binggre.velocitychat.listeners.PlayerChatListener;
import com.binggre.velocitysocketclient.VelocityClient;
import com.binggre.velocitysocketclient.socket.SocketClient;
import lombok.Getter;

@Getter
public final class VelocityChat extends BinggrePlugin {

    public static final String DATABASE_NAME = "VelocityChat";
    public static final String COLLECTION = "Config";

    @Getter
    private static VelocityChat plugin;

    private ChatConfig chatConfig;
    private SocketClient socketClient;

    @Override
    public void onEnable() {
        plugin = this;
        chatConfig = new ChatConfig(DATABASE_NAME, COLLECTION);
        chatConfig.init();
        chatConfig.save();

        executeCommand(this, new AdminCommand());
        registerEvents(this, new PlayerChatListener());
        socketClient = VelocityClient.getInstance().getConnectClient();
        socketClient.registerListener(ChatVelocityListener.class);
    }

    @Override
    public void onDisable() {
        chatConfig.save();
    }
}