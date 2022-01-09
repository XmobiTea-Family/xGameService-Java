package com.xmobitea.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class xSparkFriend {
    @Data
    public class FriendItem {
        String userId;
        int status;
    }

    @Getter
    String userId;

    @Getter
    Collection<FriendItem> socialFriendUserIds;

    xSparkFriend(BasicDBObject result) {
        this.userId = result.getString("UserId");
        socialFriendUserIds = new ArrayList<>();

        var resultSocialFriendUserIds = (BasicDBList) result.get("SocialFriendUserIds");
        for (var i = 0; i < resultSocialFriendUserIds.size(); i++) {
            var resultSocialFriend = (BasicDBObject) resultSocialFriendUserIds.get(i);

            var friendItem = new FriendItem() {{
                setUserId(resultSocialFriend.getString("UserId"));
                setStatus(resultSocialFriend.getInt("Status", 0));
            }};

            socialFriendUserIds.add(friendItem);
        }
    }

    xSparkFriend(@NonNull String userId) {
        this.userId = userId;
        socialFriendUserIds = new ArrayList<>();
    }
}
