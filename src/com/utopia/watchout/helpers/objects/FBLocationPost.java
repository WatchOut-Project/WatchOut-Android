
package com.utopia.watchout.helpers.objects;

import android.location.Location;

public class FBLocationPost {
    private long mId;
    private double mLatitude;
    private double mLongitude;
    private double mDistanceMeters;

    public FBLocationPost(long id, double latitude, double longitude, double distanceMeters) {
        this.mId = id;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mDistanceMeters = distanceMeters;
    }

    public long getId() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public Location getLocation() {
        Location location = new Location(this.getClass().getSimpleName());
        location.setLatitude(mLatitude);
        location.setLongitude(mLongitude);
        return location;
    }

    public double getDistanceMeters() {
        return mDistanceMeters;
    }
}
