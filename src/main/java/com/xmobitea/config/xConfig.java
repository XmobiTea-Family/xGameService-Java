package com.xmobitea.config;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.xmobitea.database.xDatabase;
import com.xmobitea.debug.xDebug;
import lombok.NonNull;

import java.util.Hashtable;

public final class xConfig {
    private static Hashtable<String, BasicDBObject> configDic = new Hashtable<>();

    public static void syncAllConfig() {
        var collection = xDatabase.systemGameCollection("Config");
        var results = collection.find();
        var cursor = results.iterator();

        while (cursor.hasNext()) {
            var result = cursor.next();
            var configName = result.getString("ConfigName");
            if (configName == null || configName.isEmpty()) {
                xDebug.logError("ConfigName null at " + result.toJson());
            }
            else {
                configDic.put(configName, result);
                xDebug.log("Update success config " + configName);
            }
        }
    }

    public static BasicDBObject syncConfig(@NonNull String configName) {
        var collection = xDatabase.systemGameCollection("Config");
        var result = collection.find(Filters.eq("ConfigName", configName)).skip(1).first();

        if (result == null) {
            xDebug.logError("Can not found " + configName);
            return null;
        }

        configDic.put(configName, result);
        xDebug.log("Update success config " + configName);
        return result;
    }

    public static BasicDBObject getConfig(@NonNull String configName) {
        return configDic.getOrDefault(configName, null);
    }
}
