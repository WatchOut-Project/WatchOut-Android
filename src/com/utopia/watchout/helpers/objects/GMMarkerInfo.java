
package com.utopia.watchout.helpers.objects;

import android.location.Location;

import java.util.HashSet;
import java.util.Set;

public class GMMarkerInfo {

    String mLocationName;
    Location mLocation;
    Set<Long> mPastIds; // postID,FBLocationPost

    public GMMarkerInfo(Location location, OnInfoUpdatedListener listener) {
        mLocation = new Location(this.getClass().getSimpleName());
        mLocation.setLatitude(location.getLatitude());
        mLocation.setLongitude(location.getLongitude());

        mPastIds = new HashSet<Long>();

        mListener = listener;

        // if we get location information from Google
        mLocationName = null;
    }

    public boolean isSameLocation(Location location) {
        return mLocation.getLatitude() == location.getLatitude()
                && mLocation.getLongitude() == location.getLongitude();
    }

    public void addPostId(long postId) {
        if (!mPastIds.contains(postId)) {
            mPastIds.add(postId);
            GMMarkerInfoUpdate();
        }
    }

    public Location getLocation() {
        return mLocation;
    }

    public double getLatitude() {
        return mLocation.getLatitude();
    }

    public double getLongitude() {
        return mLocation.getLongitude();
    }

    public String getLocationName() {
        return mLocationName;
    }

    public Set<Long> getPostIds() {
        return mPastIds;
    }

    public int getPostCount() {
        return mPastIds.size();
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Fragment Changed Listener
    // /////////////////////////////////////////////////////////////////////////////////
    public interface OnInfoUpdatedListener {
        void onOnInfoUpdated(GMMarkerInfo markerInfo);
    }

    OnInfoUpdatedListener mListener;

    private void GMMarkerInfoUpdate() {
        if (mListener != null) {
            mListener.onOnInfoUpdated(this);
        }
    }
}
