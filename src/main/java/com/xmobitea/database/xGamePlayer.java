package com.xmobitea.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.xmobitea.datetime.xDatetime;
import org.bson.types.ObjectId;

public class xGamePlayer {
    ObjectId _id;
    String userId;
    String displayName;
    BasicDBObject userBan;
    BasicDBList pushRegistrations;
    BasicDBObject location;
    long tsCreate;
    long tsLastSeen;
    BasicDBObject segments;
    BasicDBObject scriptData;
    BasicDBObject privateData;

    public String getUserId() {
        return this.userId;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("DisplayName", displayName)
                        ));
    }

    public Object getUserBan(String key) {
        return this.userBan.get(key);
    }

    public void setUserBan(String key, Object value) {
        this.userBan.put(key, value);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("UserBan." + key, value)
                        ));
    }

    public BasicDBObject getPush(String pushId) {
        BasicDBObject returnValue = null;

        for (var i = 0; i < pushRegistrations.size(); i++) {
            var pushRegistration = (BasicDBObject) pushRegistrations.get(i);

            if (pushRegistration.getString("PushId").equals(pushId)) {
                returnValue = pushRegistration;
                break;
            }
        }

        return returnValue;
    }

    public void addPush(BasicDBObject pushData) {
        var thisPushData = getPush(pushData.getString("PushId"));

        if (thisPushData != null) { }
        else {
            this.pushRegistrations.add(pushData);

            xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                    .updateOne(
                            Filters.eq("_id", this._id),
                            Updates.combine(
                                    Updates.push("PushRegistrations", pushData)
                            ));
        }
    }

    public void removePush(String pushId) {
        var thisPushData = getPush(pushId);

        if (thisPushData != null) {
            this.pushRegistrations.remove(thisPushData);

            xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                    .updateOne(
                            Filters.eq("_id", this._id),
                            Updates.combine(
                                    Updates.pull("PushRegistrations", new BasicDBObject().append("PushId", pushId))
                            ));
        }
    }

    public void removeAllPush() {
        this.pushRegistrations.clear();

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("PushRegistrations", new BasicDBObject())
                        ));
    }

    public BasicDBObject getLocation() {
        return this.location;
    }

    public void setLocation() {

    }

    public long getTsCreate() {
        return this.tsCreate;
    }

    public long getTsLastSeen() {
        return this.tsLastSeen;
    }

    public void updateTsLastSeen() {
        this.tsLastSeen = xDatetime.getCurrentMilliseconds();

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("TsLastSeen", this.tsLastSeen)
                        ));
    }

    public Object getSegmentValue(String key) {
        return this.segments.get(key);
    }

    public void setSegmentValue(String key, Object value) {
        this.segments.put(key, value);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("Segments." + key, value)
                        ));
    }

    public void removeSegmentValue(String key) {
        this.segments.remove(key);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.unset("Segments." + key)
                        ));
    }

    public Object getScriptData(String key) {
        return this.scriptData.get(key);
    }

    public void setScriptData(String key, Object value) {
        this.scriptData.put(key, value);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("ScriptData." + key, value)
                        ));
    }

    public void removeScriptData(String key) {
        this.scriptData.remove(key);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.unset("ScriptData." + key)
                        ));
    }

    public boolean containsScriptDataKey(String key) {
        return this.scriptData.containsKey(key);
    }

    public Object getPrivateData(String key) {
        return this.privateData.get(key);
    }

    public void setPrivateData(String key, Object value) {
        this.privateData.get(key);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.set("PrivateData." + key, value)
                        ));
    }

    public void removePrivateData(String key) {
        this.privateData.remove(key);

        xDatabase.systemGameCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq("_id", this._id),
                        Updates.combine(
                                Updates.unset("PrivateData." + key)
                        ));
    }

    public boolean containsPrivateDataKey(String key) {
        return this.privateData.containsKey(key);
    }

    public boolean isNewPlayer() {
        return this.scriptData.size() == 0;
    }

    xGamePlayer(BasicDBObject result) {
        setDocument(result);
    }

    xGamePlayer() { }

    private void setDocument(BasicDBObject result) {
        _id = result.getObjectId("_id");
        userId = result.getString("UserId");
        displayName = result.getString("DisplayName");
        userBan = (BasicDBObject) result.get("UserBan");
        pushRegistrations = (BasicDBList) result.get("PushRegistrations");
        location = (BasicDBObject) result.get("Location");
        tsCreate = result.getLong("TsCreate");
        tsLastSeen = result.getLong("TsLastSeen");
        segments = (BasicDBObject) result.get("Segments");
        scriptData = (BasicDBObject) result.get("ScriptData");
        privateData = (BasicDBObject) result.get("PrivateData");
    }
}