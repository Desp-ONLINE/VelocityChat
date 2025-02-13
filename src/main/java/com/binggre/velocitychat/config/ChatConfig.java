package com.binggre.velocitychat.config;

import com.binggre.binggreapi.utils.ColorManager;
import com.binggre.binggreapi.utils.file.FileManager;
import com.binggre.mongolibraryplugin.base.MongoConfiguration;
import lombok.Getter;
import org.bson.Document;

import java.util.List;

@Getter
public class ChatConfig extends MongoConfiguration {

    public ChatConfig(String database, String collection) {
        super(database, collection);
    }

    private String nicknameFormat = "&f<&6%player%&f> : ";
    private String messageFormat = "&a%message%";
    private List<String> infoFormat = List.of("레벨 : %level%");

    @Override
    public void init() {
        Document configDocument = getConfigDocument();
        if (configDocument == null) {
            return;
        }
        ChatConfig newInstance = FileManager.toObject(configDocument.toJson(), ChatConfig.class);
        nicknameFormat = ColorManager.format(newInstance.nicknameFormat);
        messageFormat = ColorManager.format(newInstance.messageFormat);
        infoFormat = ColorManager.format(newInstance.infoFormat);
    }
}
