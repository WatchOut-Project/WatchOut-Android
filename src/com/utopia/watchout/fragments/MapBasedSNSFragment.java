
package com.utopia.watchout.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.otto.Subscribe;
import com.squareup.otto.WOEventBus;
import com.utopia.watchout.WODebug;
import com.utopia.watchout.events.CurrentLocationEvent;
import com.utopia.watchout.helpers.FBHelper;
import com.utopia.watchout.helpers.FBHelper.OnRequestAPIListener;
import com.utopia.watchout.helpers.GMMarkerHelper;
import com.utopia.watchout.helpers.GPSTracker;
import com.utopia.watchout.helpers.objects.FBLocationPost;
import com.utopia.watchout.helpers.objects.GMMarkerInfo;
import com.utopia.watchout.helpers.objects.GMMarkerInfo.OnInfoUpdatedListener;
import com.utopia.watchout.model.GMMarkerTable;

public class MapBasedSNSFragment extends SupportMapFragment implements OnCameraChangeListener,
        OnMarkerClickListener {

    private final String TAG = "MapBasedSNSFragment";
    private final int ZOOM_DURATION = 500;
    private final int INITIAL_ZOOM = 12;
    private final double INITIAL_LATITUDE = 37.513539d;
    private final double INITIAL_LONGITUDE = 126.985474d;

    private GoogleMap mMap;
    private GMMarkerHelper mMarkerHelper;

    private GPSTracker mGPS;

    // If user clicked Marker and System moved mapView
    private boolean mDoNotRefreshMap = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMap = this.getMap();
        mMarkerHelper = new GMMarkerHelper(getActivity(), mMap);
        mGPS = new GPSTracker(getActivity());
        
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            mMap.setOnCameraChangeListener(this);
            mMap.setOnMarkerClickListener(this);

            Location lastLocation = mGPS.getLocation();
            if (lastLocation == null)
                moveCamera(INITIAL_LATITUDE, INITIAL_LONGITUDE, INITIAL_ZOOM);
            else
                moveCamera(lastLocation, 12);
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // OnCameraChangeListener
    // /////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCameraChange(CameraPosition position) {
        if (WODebug.DEBUG)
            Log.d(TAG, "Camera Changed");

        // refresh currentView
        refreshCurrentView();

    }

    // /////////////////////////////////////////////////////////////////////////////////
    // OnMarkerClickListener
    // 1. marker is One Place => Show Info Window
    // /////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onMarkerClick(final Marker marker) {

        GMMarkerInfo markerInfo = GMMarkerTable.getMarkerInfo(marker);
        mCallback.onOpenMakerWindow(markerInfo);

        mDoNotRefreshMap = true;
        moveCamera(marker.getPosition());

        return true;
    }

    OnMarkerClicked mCallback;

    public interface OnMarkerClicked {
        public void onOpenMakerWindow(GMMarkerInfo markerInfo);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        WOEventBus.getInstance().register(this);
        try {
            mCallback = (OnMarkerClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMarkerClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        WOEventBus.getInstance().unregister(this);
    }

    @Subscribe
    public void currenLocation(CurrentLocationEvent event) {
        if (mGPS == null || !mGPS.canGetLocation())
            return;

        if (!GPSTracker.isGpsEnable(getActivity()))
            GPSTracker.showConfirmDialog(getActivity());

        moveCamera(mGPS.getLocation(), INITIAL_ZOOM);
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Move Camera
    // /////////////////////////////////////////////////////////////////////////////////

    private void moveCamera(Location location) {
        if (location != null)
            moveCamera(location.getLatitude(), location.getLongitude());
    }

    private void moveCamera(Location location, int zoom) {
        if (location != null)
            moveCamera(location.getLatitude(), location.getLongitude(), zoom);
    }

    private void moveCamera(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        moveCamera(latLng);
    }

    private void moveCamera(double latitude, double longitude, int zoom) {
        LatLng latLng = new LatLng(latitude, longitude);
        moveCamera(latLng, zoom);
    }

    private void moveCamera(LatLng latLng) {

        if (latLng != null) {
            // Move the camera instantly to latLng, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), ZOOM_DURATION, null);
        } else {
            if (WODebug.DEBUG)
                Log.e(TAG, "Move Camera : LatLng is : " + latLng);
        }
    }

    private void moveCamera(LatLng latLng, int zoom) {

        if (latLng != null) {
            // Move the camera instantly to latLng with a zoom of zoom,
            // animating the camera.
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom), ZOOM_DURATION, null);
        } else {
            if (WODebug.DEBUG)
                Log.e(TAG, "Move Camera : LatLng is : " + latLng + ", zoom : " + zoom);
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Refresh CurrentView
    // 1. ADD CurrentView Place Picker
    // 2. CALCULATE CurrentView Danger Info
    // /////////////////////////////////////////////////////////////////////////////////
    private void refreshCurrentView() {
        if (!mDoNotRefreshMap) {
            CameraPosition position = mMap.getCameraPosition();
            addPlacePickerAround(position.target.latitude, position.target.longitude,
                    (int) position.zoom);
        }
        mDoNotRefreshMap = false;
    }

    private void addPlacePickerAround(double latitude, double longitude, int zoom) {

        Location location = new Location(getClass().getName());
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        FBHelper.getLocationData(getActivity(), new OnRequestAPIListener() {
            @Override
            public void onRequestAPIStart() {
            }

            @Override
            public void onRequestAPIProcess() {
            }

            @Override
            public void onRequestAPIFinish(Object object) {
                FBLocationPost[] locationPosts = (FBLocationPost[]) object;
                for (FBLocationPost locationPost : locationPosts) {
                    if (locationPost != null)
                        addLocationPost(locationPost);
                }
            }

            @Override
            public void onRequestAPIError(String title, String message) {
                if (WODebug.DEBUG)
                    Log.e(TAG, "title : " + title + ", message : " + message);
            }
        }, location);
    }

    private int calculateDistance(int zoom) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int Measuredwidth = displaymetrics.widthPixels;
        int Measuredheight = displaymetrics.heightPixels;

        return 1000;
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Add One Place Marker
    // /////////////////////////////////////////////////////////////////////////////////
    private void addLocationPost(final FBLocationPost locationPost) {
        GMMarkerTable.addMakerInfo(locationPost, new OnInfoUpdatedListener() {
            @Override
            public void onOnInfoUpdated(GMMarkerInfo markerInfo) {
                mMarkerHelper.updateMarker(markerInfo);
            }
        });
    }
}
