
package com.utopia.watchout.model;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;
import com.utopia.watchout.helpers.objects.FBLocationPost;
import com.utopia.watchout.helpers.objects.GMMarkerInfo;
import com.utopia.watchout.helpers.objects.GMMarkerInfo.OnInfoUpdatedListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GMMarkerTable {

    private static Map<GMMarkerInfo, Marker> mTable = new HashMap<GMMarkerInfo, Marker>();

    public static void addMakerInfo(FBLocationPost locationPost, OnInfoUpdatedListener listener) {

        Location location = locationPost.getLocation();
        long postId = locationPost.getId();

        for (GMMarkerInfo markerInfo : mTable.keySet()) {
            if (markerInfo.isSameLocation(location)) {
                markerInfo.addPostId(postId);
                return;
            }
        }
        GMMarkerInfo info = new GMMarkerInfo(location, listener);
        update(info, null);
        info.addPostId(postId);
    }

    public static Marker getMarker(GMMarkerInfo markerInfo) {
        return mTable.get(markerInfo);
    }

    public static GMMarkerInfo getMarkerInfo(Marker marker) {
        for (GMMarkerInfo markerInfo : mTable.keySet()) {
            if (mTable.get(markerInfo).equals(marker)) {
                return markerInfo;
            }
        }
        return null;
    }

    public static Set<GMMarkerInfo> getInfoTable() {
        return mTable.keySet();
    }

    public static void update(GMMarkerInfo markerInfo, Marker marker) {
        mTable.put(markerInfo, marker);
    }
}
