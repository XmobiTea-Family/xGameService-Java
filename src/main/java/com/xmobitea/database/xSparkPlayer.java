package com.xmobitea.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.xmobitea.datetime.xDatetime;
import org.bson.types.ObjectId;

public class xSparkPlayer {
    ObjectId _id;
    String userId;
    String username;
    String password;
    String fullname;
    BasicDBObject userExternal;
    BasicDBObject userBan;
    BasicDBObject userEmail;
    BasicDBObject userNumberPhone;
    BasicDBObject userAddress;
    BasicDBObject userPassport;
    BasicDBObject location;
    long tsBirthday;
    long tsCreate;
    long tsLastSeen;
    String ipAddressCreate;
    BasicDBObject segments;
    BasicDBObject scriptData;
    BasicDBObject privateData;
    String accessToken;
    String refreshToken;

    public String getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("Username", username)
                        ));
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("Password", password)
                        ));
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("Fullname", fullname)
                        ));
    }

    public Object getUserExternal(String key) {
        return this.userExternal.get(key);
    }

    public void setUserExternal(String key, Object value) {
        this.userExternal.put(key, value);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("UserExternal." + key, value)
                        ));
    }

    public Object getUserBan(String key) {
        return this.userBan.get(key);
    }

    public void setUserBan(String key, Object value) {
        this.userBan.put(key, value);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("UserBan." + key, value)
                        ));
    }

    public BasicDBObject getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String value, boolean isConfirm) {
        this.userEmail.put("Value", value);
        this.userEmail.put("IsConfirm", isConfirm);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("UserEmail.Value", value),
                                Updates.set("UserEmail.IsConfirm", isConfirm)
                        ));
    }

    public void setPinCodeUserMail(String pinCode, long tsExpire) {
        if (pinCode == null || pinCode.isEmpty()) {
            this.userEmail.remove("PinCode");
            this.userEmail.remove("TsExpire");

            xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                    .updateOne(
                            Filters.eq(this._id),
                            Updates.combine(
                                    Updates.unset("UserEmail.PinCode"),
                                    Updates.unset("UserEmail.TsExpire")
                            ));
        } else {
            this.userEmail.put("PinCode", pinCode);
            this.userEmail.put("TsExpire", tsExpire);

            xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                    .updateOne(
                            Filters.eq(this._id),
                            Updates.combine(
                                    Updates.set("UserEmail.PinCode", pinCode),
                                    Updates.set("UserEmail.TsExpire", tsExpire)
                            ));
        }
    }

    public BasicDBObject getUserNumberPhone() {
        return this.userNumberPhone;
    }

    public void setUserNumberPhone(String value, boolean isConfirm) {
        this.userNumberPhone.put("Value", value);
        this.userNumberPhone.put("IsConfirm", isConfirm);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("UserNumberPhone.Value", value),
                                Updates.set("UserNumberPhone.IsConfirm", isConfirm)
                        ));
    }

    public BasicDBObject getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress() {

    }

    public BasicDBObject getUserPassport() {
        return this.userPassport;
    }

    public void setUserPassport() {

    }

    public BasicDBObject getLocation() {
        return this.location;
    }

    public void setLocation() {

    }

    public long getTsBirthday() {
        return this.tsBirthday;
    }

    public void setTsBirthday(long tsBirthday) {
        this.tsBirthday = tsBirthday;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("TsBirthday", tsBirthday)
                        ));
    }

    public long getTsCreate() {
        return this.tsCreate;
    }

    public long getTsLastSeen() {
        return this.tsLastSeen;
    }

    public void updateTsLastSeen() {
        this.tsLastSeen = xDatetime.getCurrentMilliseconds();

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("TsLastSeen", this.tsLastSeen)
                        ));
    }

    public String getIPAddressCreate() {
        return this.ipAddressCreate;
    }

    public Object getSegmentValue(String key) {
        return this.segments.get(key);
    }

    public void setSegmentValue(String key, Object value) {
        this.segments.put(key, value);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("Segments." + key, value)
                        ));
    }

    public void removeSegmentValue(String key) {
        this.segments.remove(key);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.unset("Segments." + key)
                        ));
    }

    public Object getScriptData(String key) {
        return this.scriptData.get(key);
    }

    public void setScriptData(String key, Object value) {
        this.scriptData.put(key, value);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("ScriptData." + key, value)
                        ));
    }

    public void removeScriptData(String key) {
        this.scriptData.remove(key);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
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

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("PrivateData." + key, value)
                        ));
    }

    public void removePrivateData(String key) {
        this.privateData.remove(key);

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.unset("PrivateData." + key)
                        ));
    }

    public boolean containsPrivateDataKey(String key) {
        return this.privateData.containsKey(key);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("AccessToken", accessToken)
                        ));
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        xDatabase.systemCollection(xDatabase.PlayerCollectionName)
                .updateOne(
                        Filters.eq(this._id),
                        Updates.combine(
                                Updates.set("RefreshToken", refreshToken)
                        ));
    }

    public boolean isNewPlayer() {
        return this.scriptData.size() == 0;
    }

    xSparkPlayer(BasicDBObject result) {
        setDocument(result);
    }

    xSparkPlayer() { }

    private void setDocument(BasicDBObject result) {
        _id = result.getObjectId("_id");
        userId = _id.toHexString();
        username = result.getString("Username");
        password = result.getString("Password");
        fullname = result.getString("Fullname");
        userExternal = (BasicDBObject) result.get("UserExternal");
        userBan = (BasicDBObject) result.get("UserBan");
        userEmail = (BasicDBObject) result.get("UserEmail");
        userNumberPhone = (BasicDBObject) result.get("UserNumberPhone");
        userAddress = (BasicDBObject) result.get("UserAddress");
        userPassport = (BasicDBObject) result.get("UserPassport");
        location = (BasicDBObject) result.get("Location");
        tsBirthday = result.getLong("TsBirthday");
        tsCreate = result.getLong("TsCreate");
        tsLastSeen = result.getLong("TsLastSeen");
        ipAddressCreate = result.getString("IPAddressCreate");
        segments = (BasicDBObject) result.get("Segments");
        scriptData = (BasicDBObject) result.get("ScriptData");
        privateData = (BasicDBObject) result.get("PrivateData");
        accessToken = result.getString("AccessToken");
        refreshToken = result.getString("RefreshToken");
    }

    @Override
    public String toString() {
        return "xSparkPlayer{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", userExternal=" + userExternal +
                ", userBan=" + userBan +
                ", userEmail=" + userEmail +
                ", userNumberPhone=" + userNumberPhone +
                ", userAddress=" + userAddress +
                ", userPassport=" + userPassport +
                ", location=" + location +
                ", tsBirthday=" + tsBirthday +
                ", tsCreate=" + tsCreate +
                ", tsLastSeen=" + tsLastSeen +
                ", ipAddressCreate='" + ipAddressCreate + '\'' +
                ", segments=" + segments +
                ", scriptData=" + scriptData +
                ", privateData=" + privateData +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }

//    @Data
//    public class Builder {
//
//        @BsonId()
//        @BsonRepresentation(BsonType.OBJECT_ID)
//        private String userId;
//
//        @BsonProperty("Username")
//        private String username;
//
//        @BsonProperty("Password")
//        private String password;
//
//        @BsonProperty("Fullname")
//        private String fullname;
//
//        @BsonProperty("UserExternal")
//        private Document userExternal;
//
//        @BsonProperty("UserBan")
//        private Document userBan;
//
//        @BsonProperty("UserEmail")
//        private Document userEmail;
//
//        @BsonProperty("UserNumberPhone")
//        private Document userNumberPhone;
//
//        @BsonProperty("UserAddress")
//        private Document userAddress;
//
//        @BsonProperty("UserPassport")
//        private Document userPassport;
//
//        @BsonProperty("Location")
//        private Document location;
//
//        @BsonProperty("TsBirthday")
//        private long tsBirthday;
//
//        @BsonProperty("TsCreate")
//        private long tsCreate;
//
//        @BsonProperty("TsLastSeen")
//        private long tsLastSeen;
//
//        @BsonProperty("IPAddressCreate")
//        private String ipAddressCreate;
//
//        @BsonProperty("Segments")
//        private Document segments;
//
//        @BsonProperty("ScriptData")
//        private Document scriptData;
//
//        @BsonProperty("PrivateData")
//        private Document privateData;
//
//        @BsonProperty("AccessToken")
//        private String accessToken;
//
//        public xSparkPlayer build() {
//            var sparkPlayer = new xSparkPlayer();
//
//            sparkPlayer.userId = userId;
//            sparkPlayer.username = username;
//            sparkPlayer.password = password;
//            sparkPlayer.fullname = fullname;
//            sparkPlayer.userExternal = userExternal;
//            sparkPlayer.userBan = userBan;
//            sparkPlayer.userEmail = userEmail;
//            sparkPlayer.userNumberPhone = userNumberPhone;
//            sparkPlayer.userAddress = userAddress;
//            sparkPlayer.userPassport = userPassport;
//            sparkPlayer.location = location;
//            sparkPlayer.tsBirthday = tsBirthday;
//            sparkPlayer.tsCreate = tsCreate;
//            sparkPlayer.tsLastSeen = tsLastSeen;
//            sparkPlayer.ipAddressCreate = ipAddressCreate;
//            sparkPlayer.segments = segments;
//            sparkPlayer.scriptData = scriptData;
//            sparkPlayer.privateData = privateData;
//            sparkPlayer.accessToken = accessToken;
//
//            return sparkPlayer;
//        }
//    }
}