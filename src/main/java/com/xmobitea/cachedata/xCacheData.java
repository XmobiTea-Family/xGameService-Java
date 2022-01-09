package com.xmobitea.cachedata;

import lombok.NonNull;

import java.util.Hashtable;

public final class xCacheData {

    private static Hashtable<String, Object> dataDic = new Hashtable<>();

    public static <T> T getData(@NonNull String key) {
        return (T)dataDic.getOrDefault(key, null);
    }

    public static <T> void setData(@NonNull String key, @NonNull T value) {
        dataDic.put(key, value);
    }

    public static void removeData(@NonNull String key) {
        dataDic.remove(key);
    }
}
