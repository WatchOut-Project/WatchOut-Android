
package com.utopia.watchout.helpers;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.utopia.watchout.R;
import com.utopia.watchout.helpers.GMIconHelper.GMMarkerType;
import com.utopia.watchout.helpers.objects.GMMarkerInfo;
import com.utopia.watchout.model.GMMarkerTable;

public class GMMarkerHelper {

    Context mContext;
    GoogleMap mMap;

    public GMMarkerHelper(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
    }

    public void updateMarker(GMMarkerInfo markerInfo) {
        for (GMMarkerInfo info : GMMarkerTable.getInfoTable()) {
            if (info.equals(markerInfo)) {
                Marker marker = GMMarkerTable.getMarker(markerInfo);
                if (marker != null)
                    marker.remove();
                addMarker(markerInfo);
                return;
            }
        }
        addMarker(markerInfo);
    }

    private void addMarker(GMMarkerInfo markerInfo) {
        LatLng latLng = new LatLng(markerInfo.getLatitude(), markerInfo.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .icon(GMIconHelper.getIcon(mContext, GMMarkerType.WATCHOUT,
                        markerInfo.getPostCount()))
                .position(latLng)
                .title(mContext.getString(R.string.loading_))
                .snippet(markerInfo.getLocationName()));
        GMMarkerTable.update(markerInfo, marker);
    }
}
