package com.xmobitea.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.Filters;
import lombok.NonNull;
import org.bson.types.ObjectId;
import com.xmobitea.datetime.xDatetime;

import java.util.HashMap;
import java.util.Map;

public final class xDatabase {
    private final static String runtime = "Runtime.";
    private final static String meta = "Meta.";
    private final static String system = "System.";

    public final static String PlayerCollectionName = "Player";
    public final static String ExternalFBAuthenticationCollectionName = "ExternalFBAuthentication";
    public final static String FriendCollectionName = "Friend";
    public final static String InAppPurchaseCollectionName = "InAppPurchase";

    private static String _gameName;
    private static MongoDatabase db;

    public static void init(@NonNull String gameName, @NonNull String databaseName, MongoClientSettings settings) {
        _gameName = gameName;

        var client = MongoClients.create(settings);
        db = client.getDatabase(databaseName);
    }

    public static <T> MongoCollection<T> runtimeCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(runtime + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> runtimeCollection(@NonNull String collectionName) {
        return runtimeCollection(collectionName, BasicDBObject.class);
    }

    public static <T> MongoCollection<T> metaCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(meta + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> metaCollection(@NonNull String collectionName) {
        return metaCollection(collectionName, BasicDBObject.class);
    }

    public static <T> MongoCollection<T> systemCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(system + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> systemCollection(@NonNull String collectionName) {
        return systemCollection(collectionName, BasicDBObject.class);
    }

    public static <T> MongoCollection<T> runtimeGameCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(runtime + _gameName + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> runtimeGameCollection(@NonNull String collectionName) {
        return systemGameCollection(collectionName, BasicDBObject.class);
    }

    public static <T> MongoCollection<T> metaGameCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(meta + _gameName + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> metaGameCollection(@NonNull String collectionName) {
        return systemGameCollection(collectionName, BasicDBObject.class);
    }

    public static <T> MongoCollection<T> systemGameCollection(@NonNull String collectionName, Class<T> t) {
        return db.getCollection(system + _gameName + collectionName, t);
    }

    public static MongoCollection<BasicDBObject> systemGameCollection(@NonNull String collectionName) {
        return systemGameCollection(collectionName, BasicDBObject.class);
    }

    public static xSparkPlayer loadPlayer(@NonNull String userId) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq(new ObjectId(userId))).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByUsernameAndPassword(@NonNull String username, @NonNull String password) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(new BasicDBObject().append("Username", username).append("Password", password)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByUsername(@NonNull String username) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("Username", username)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByEmail(@NonNull String email) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserEmail.Value", email)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByDeviceInfo(@NonNull String deviceInfo) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserExternal.DeviceInfo", deviceInfo)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByFacebookId(@NonNull String facebookId) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserExternal.FacebookId", facebookId)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByPublicAddress(@NonNull String publicAddress) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserExternal.PublicAddress", publicAddress)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer loadPlayerByCustomId(@NonNull String customId) {
        var collection = systemCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserExternal.CustomId", customId)).limit(1).first();

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer createPlayerByDeviceInfo(@NonNull String deviceInfo, @NonNull Map segments, @NonNull String ipAddressCreate) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("Username", "");
        result.put("Password", "");
        result.put("Fullname", "");
        result.put("UserExternal", new BasicDBObject().append("DeviceInfo", deviceInfo));
        result.put("UserBan", new BasicDBObject());
        result.put("UserEmail", new BasicDBObject());
        result.put("UserNumberPhone", new BasicDBObject());
        result.put("UserAddress", new BasicDBObject());
        result.put("UserPassport", new BasicDBObject());
        result.put("Location", new BasicDBObject());
        result.put("TsBirthday", 0);
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("IPAddressCreate", ipAddressCreate);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());
        result.put("AccessToken", "");
        result.put("RefreshToken", "");

        var collection = systemCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer createPlayerByUsernameAndPassword(@NonNull String username, @NonNull String password, @NonNull Map segments, @NonNull String ipAddressCreate) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("Username", username);
        result.put("Password", password);
        result.put("Fullname", "");
        result.put("UserExternal", new BasicDBObject());
        result.put("UserBan", new BasicDBObject());
        result.put("UserEmail", new BasicDBObject());
        result.put("UserNumberPhone", new BasicDBObject());
        result.put("UserAddress", new BasicDBObject());
        result.put("UserPassport", new BasicDBObject());
        result.put("Location", new BasicDBObject());
        result.put("TsBirthday", 0);
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("IPAddressCreate", ipAddressCreate);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());
        result.put("AccessToken", "");
        result.put("RefreshToken", "");

        var collection = systemCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer createPlayerByFacebookId(@NonNull String facebookId, @NonNull Map segments, @NonNull String ipAddressCreate, String email) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("Username", "");
        result.put("Password", "");
        result.put("Fullname", "");
        result.put("UserExternal", new BasicDBObject().append("FacebookId", facebookId));
        result.put("UserBan", new BasicDBObject());

        if (email != null && !email.isEmpty()) {
            result.put("UserEmail", new BasicDBObject().append("Value", email).append("IsConfirm", true));
        }
        else {
            result.put("UserEmail", new BasicDBObject());
        }

        result.put("UserEmail", new BasicDBObject());
        result.put("UserNumberPhone", new BasicDBObject());
        result.put("UserAddress", new BasicDBObject());
        result.put("UserPassport", new BasicDBObject());
        result.put("Location", new BasicDBObject());
        result.put("TsBirthday", 0);
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("IPAddressCreate", ipAddressCreate);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());
        result.put("AccessToken", "");
        result.put("RefreshToken", "");

        var collection = systemCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer createPlayerByPublicAddress(@NonNull String publicAddress, @NonNull Map segments, @NonNull String ipAddressCreate) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("Username", "");
        result.put("Password", "");
        result.put("Fullname", "");
        result.put("UserExternal", new BasicDBObject().append("PublicAddress", publicAddress));
        result.put("UserBan", new BasicDBObject());
        result.put("UserEmail", new BasicDBObject());
        result.put("UserNumberPhone", new BasicDBObject());
        result.put("UserAddress", new BasicDBObject());
        result.put("UserPassport", new BasicDBObject());
        result.put("Location", new BasicDBObject());
        result.put("TsBirthday", 0);
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("IPAddressCreate", ipAddressCreate);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());
        result.put("AccessToken", "");
        result.put("RefreshToken", "");

        var collection = systemCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static xSparkPlayer createPlayerByCustomId(@NonNull String customId, @NonNull Map segments, @NonNull String ipAddressCreate) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("Username", "");
        result.put("Password", "");
        result.put("Fullname", "");
        result.put("UserExternal", new BasicDBObject().append("CustomId", customId));
        result.put("UserBan", new BasicDBObject());
        result.put("UserEmail", new BasicDBObject());
        result.put("UserNumberPhone", new BasicDBObject());
        result.put("UserAddress", new BasicDBObject());
        result.put("UserPassport", new BasicDBObject());
        result.put("Location", new BasicDBObject());
        result.put("TsBirthday", 0);
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("IPAddressCreate", ipAddressCreate);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());
        result.put("AccessToken", "");
        result.put("RefreshToken", "");

        var collection = systemCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xSparkPlayer(result);
        return null;
    }

    public static void saveFBExternal(@NonNull String userId, @NonNull String facebookId, @NonNull String facebookName, @NonNull String facebookEmail, @NonNull String facebookToken) {
        systemCollection(ExternalFBAuthenticationCollectionName).updateOne(
                Filters.eq("UserId", userId),
                Updates.combine(
                        Updates.set("ExternalId", facebookId),
                        Updates.set("ExternalDisplayName", facebookName),
                        Updates.set("ExternalEmail", facebookEmail),
                        Updates.set("Token", facebookToken),
                        Updates.set("TsUpdate", xDatetime.getCurrentMilliseconds())
                ), new UpdateOptions()
                        .upsert(true));
    }

    public static BasicDBObject loadFBExternal(@NonNull String facebookId) {
        return systemCollection(ExternalFBAuthenticationCollectionName).find(Filters.eq("ExternalId", facebookId)).limit(1).first();
    }

    public static xSparkFriend loadFriend(@NonNull String userId) {
        var collection = systemCollection(FriendCollectionName);
        var result = collection.find(Filters.eq("UserId", userId)).limit(1).first();

        if (result != null) return new xSparkFriend(result);
        return null;
    }

    public static void saveFriend(@NonNull xSparkFriend sparkFriend) {

    }

    public static xGamePlayer loadGamePlayer(@NonNull String userId) {
        var collection = systemGameCollection(PlayerCollectionName);
        var result = collection.find(Filters.eq("UserId", userId)).limit(1).first();

        if (result != null) return new xGamePlayer(result);

        return createGamePlayer(userId, new HashMap<String, Integer>() {
            {
                put("UserRole", 101);
            }
        });
    }

    public static xGamePlayer createGamePlayer(@NonNull String userId, @NonNull Map segments) {
        var currentMilliseconds = xDatetime.getCurrentMilliseconds();

        var result = new BasicDBObject();
        result.put("UserId", userId);
        result.put("DisplayName", "");
        result.put("UserBan", new BasicDBObject());
        result.put("PushRegistrations", new BasicDBList());
        result.put("Location", new BasicDBObject());
        result.put("TsCreate", currentMilliseconds);
        result.put("TsLastSeen", currentMilliseconds);
        result.put("Segments", new BasicDBObject(segments));
        result.put("ScriptData", new BasicDBObject());
        result.put("PrivateData", new BasicDBObject());

        var collection = systemGameCollection(PlayerCollectionName);
        var insertOneResult = collection.insertOne(result);

        if (result != null) return new xGamePlayer(result);
        return null;
    }
}