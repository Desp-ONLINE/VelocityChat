package com.binggre.velocitychat.commands.arguments;

import com.binggre.binggreapi.command.CommandArgument;
import com.binggre.velocitychat.VelocityChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadArgument implements CommandArgument {

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        VelocityChat.getPlugin().getChatConfig().init();
        commandSender.sendMessage("리로드 완료");
        return true;
    }

    @Override
    public String getArg() {
        return "리로드";
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "- config를 리로드합니다.";
    }

    @Override
    public String getPermission() {
        return "velocitychat.admin.reload";
    }

    @Override
    public String getPermissionMessage() {
        return "§c권한이 없습니다.";
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }
}
