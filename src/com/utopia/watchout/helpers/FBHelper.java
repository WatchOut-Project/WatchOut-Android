
package com.utopia.watchout.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.utopia.watchout.R;
import com.utopia.watchout.WODebug;
import com.utopia.watchout.helpers.json.FBLocationPostJson;
import com.utopia.watchout.helpers.json.FBStatusJson;
import com.utopia.watchout.helpers.objects.FBStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This FacebookHelper Class is implemented and designed for one user based
 * Application. Session opening system is based on assumption on having access
 * token.
 */
public class FBHelper {

    protected static final String TAG = "FacebookHelper";
    private final static String INITIAL_ACCESS_TOKEN = "CAACK9CY9QZCABANLBw0mLTkgzRVMMMxJA8DQIA1tJD8KGDBfwstRg0fY4knYJkPUIpXhmOkau4fJ26u6ozZBDHZBG5Wg0UmxUZBv7E9CRkqDQooV7bZBOGXZBBaZApi1omOUpN4GzyQEBVmANP1YtUTIjpTp6N7cZCcygxiVxTloG3qL5TpobeVUZAoqNgOxsW9NnuP6gyiAFrTAJhBUpuyXZCdghU7ZBkwtuJZA18uUTV9ZBExRnBfnuRIA0";
    public static final List<String> PERMISSIONS_ACCESSTOKEN = Arrays.asList("user_photos",
            "user_status",
            "user_checkins", "read_stream", "publish_stream");
    public static final List<String> PERMISSIONS_READ = Arrays.asList("user_photos", "user_status",
            "user_checkins", "read_stream");
    public static final List<String> PERMISSIONS_PUBLISH = Arrays.asList("publish_stream");

    // /////////////////////////////////////////////////////////////////////////////////
    // Get Location data Methods (Used in GoogleMap refresh method)
    // /////////////////////////////////////////////////////////////////////////////////
    public static void getLocationData(final Context context, final OnRequestAPIListener listener,
            final Location location) {
        if (listener != null)
            listener.onRequestAPIStart();
        getActiveSessionWithToken(context, new OnSessionOpenListener() {

            @Override
            public void onSessionOpenFinish(Session session) {
                if (listener != null)
                    listener.onRequestAPIProcess();

                String distance = "distance(latitude, longitude, '" + location.getLatitude()
                        + "', '" + location.getLongitude() + "')";
                String fqlQuery = "SELECT id, latitude, longitude , " + distance
                        + "FROM location_post "
                        + "WHERE author_uid = me() "
                        + "ORDER BY " + distance + "ASC";
                Bundle params = new Bundle();
                params.putString("q", fqlQuery);
                Request request = new Request(session,
                        "/fql",
                        params,
                        HttpMethod.GET,
                        new Request.Callback() {
                            public void onCompleted(Response response) {

                                if (listener != null) {
                                    FacebookRequestError error = response.getError();

                                    // Send result as LocationPost Array
                                    if (error == null)
                                        listener.onRequestAPIFinish(FBLocationPostJson
                                                .getArrayData(response.getGraphObject()
                                                        .getInnerJSONObject()));
                                    else
                                        listener.onRequestAPIError(error.getErrorType(),
                                                error.getErrorMessage());
                                }
                            }
                        });
                try {
                    request.executeAsync();
                } catch (IllegalStateException exception) {
                    if (WODebug.DEBUG)
                        Log.e(TAG, exception.getMessage());
                }
            }

            @Override
            public void onSessionOpenError() {
                if (listener != null)
                    listener.onRequestAPIError(
                            context.getString(R.string.facebook_session_open_error),
                            context.getString(R.string.couldnt_open_session_with_accesstoken));
            }
        });
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Get Status data Methods
    // /////////////////////////////////////////////////////////////////////////////////
    public static void getStatusData(final Context context, final OnRequestAPIListener listener,
            final long postId) {
        if (listener != null)
            listener.onRequestAPIStart();
        getActiveSessionWithToken(context, new OnSessionOpenListener() {

            @Override
            public void onSessionOpenFinish(Session session) {
                if (listener != null)
                    listener.onRequestAPIProcess();

                RequestBatch requestBatch = new RequestBatch();
                requestBatch.add(new Request(Session.getActiveSession(),
                        postId + "", null, null, new Request.Callback() {
                            public void onCompleted(Response response) {

                                if (listener != null) {
                                    FacebookRequestError error = response.getError();

                                    if (error == null) {
                                        GraphObject graphObject = response.getGraphObject();
                                        FBStatus status = FBStatusJson.getData(graphObject
                                                .getInnerJSONObject());
                                        listener.onRequestAPIFinish(status);
                                    } else
                                        listener.onRequestAPIError(error.getErrorType(),
                                                error.getErrorMessage());
                                }
                            }
                        }));
                try {
                    requestBatch.executeAsync();
                } catch (IllegalStateException exception) {
                    if (WODebug.DEBUG)
                        Log.e(TAG, exception.getMessage());
                }
            }

            @Override
            public void onSessionOpenError() {
                if (listener != null)
                    listener.onRequestAPIError(
                            context.getString(R.string.facebook_session_open_error),
                            context.getString(R.string.couldnt_open_session_with_accesstoken));
            }
        });
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Publish Location Status Methods
    // /////////////////////////////////////////////////////////////////////////////////
    public static void publishStatus(final Context context, final OnRequestAPIListener listener,
            final String message, final String photo_path, final String place_id) {
        if (listener != null)
            listener.onRequestAPIStart();
        getActiveSessionWithToken(context, new OnSessionOpenListener() {

            @Override
            public void onSessionOpenFinish(Session session) {
                if (listener != null)
                    listener.onRequestAPIProcess();

                Bundle postParams = new Bundle();
                postParams.putString("message", message);
                if (photo_path != null) {
                    try {
                        File file = new File("/", photo_path);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        byte[] data = null;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        data = baos.toByteArray();
                        if (data != null) {
                            postParams.putByteArray("picture", data);
                        }
                    } catch (Exception e) {
                        if (WODebug.DEBUG)
                            Log.d(TAG, context.getString(R.string.photo_streaming_error) + " : "
                                    + e.toString());
                    }
                }

                if (place_id != null)
                    postParams.putLong("place", Long.parseLong(place_id));

                Request request = new Request(session, "me/photos", postParams,
                        HttpMethod.POST, new Callback() {

                            @Override
                            public void onCompleted(Response response) {

                                if (listener != null) {
                                    FacebookRequestError error = response.getError();
                                    if (error == null) {
                                        JSONObject graphResponse = response.getGraphObject()
                                                .getInnerJSONObject();

                                        try {
                                            graphResponse.getString("id");
                                        } catch (JSONException e) {
                                            if (WODebug.DEBUG)
                                                Log.i(TAG,
                                                        context.getString(R.string.facebook_publish_status_failed)
                                                                + " : " + e.getMessage());
                                            if (listener != null)
                                                listener.onRequestAPIError(
                                                        context.getString(R.string.facebook_publish_status_failed),
                                                        e.getMessage());
                                        }

                                        listener.onRequestAPIFinish(null);

                                    } else {
                                        if (WODebug.DEBUG)
                                            Log.i(TAG,
                                                    context.getString(R.string.facebook_publish_status_failed)
                                                            + " : " + error.getErrorMessage());
                                        listener.onRequestAPIError(
                                                context.getString(R.string.facebook_publish_status_failed),
                                                error.getErrorMessage());
                                    }
                                }
                            }
                        });
                try {
                    request.executeAsync();
                } catch (IllegalStateException exception) {
                    if (WODebug.DEBUG)
                        Log.e(TAG, exception.getMessage());
                }
            }

            @Override
            public void onSessionOpenError() {
                if (listener != null)
                    listener.onRequestAPIError(
                            context.getString(R.string.facebook_session_open_error),
                            context.getString(R.string.couldnt_open_session_with_accesstoken));
            }
        });
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Session open Methods *****OnSessionOpenListner should not be null
    // /////////////////////////////////////////////////////////////////////////////////
    private static void getActiveSessionWithToken(final Context context,
            final OnSessionOpenListener sessionListener) {

        if (sessionListener == null)
            return;

        if (context == null) {
            sessionListener.onSessionOpenError();
            return;
        }

        // If there is usable activeSession
        Session activeSession = Session.getActiveSession();
        if (activeSession != null
                && isContainPermission(activeSession.getPermissions(), PERMISSIONS_READ)
                && isContainPermission(activeSession.getPermissions(), PERMISSIONS_PUBLISH)) {

            WODebug.LogDebug(TAG, "ActiveSession, state : " + activeSession.getState().name()
                    + "\nPermissions : " + activeSession.getPermissions().toString()
                    + "\nAccessToken : " + activeSession.getAccessToken()
                    + "\nExpire : " + activeSession.getExpirationDate());

            sessionListener.onSessionOpenFinish(activeSession);
        } else {
            AccessToken accesToken = AccessToken.createFromExistingAccessToken(
                    INITIAL_ACCESS_TOKEN, null,
                    null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, PERMISSIONS_ACCESSTOKEN);
            Session.openActiveSessionWithAccessToken(context,
                    accesToken, new StatusCallback() {
                        @Override
                        public void call(Session session, SessionState state, Exception exception) {

                            if (WODebug.DEBUG)
                                Log.d(TAG, "New Session, state : " + state.name()
                                        + ", permissions : "
                                        + session.getPermissions().toString());

                            if (session.isOpened()
                                    && isContainPermission(session.getPermissions(),
                                            PERMISSIONS_READ)
                                    && isContainPermission(session.getPermissions(),
                                            PERMISSIONS_PUBLISH)) {
                                sessionListener.onSessionOpenFinish(session);
                            }

                            sessionListener.onSessionOpenError();

                        }
                    });
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // Private Helper
    // /////////////////////////////////////////////////////////////////////////////////
    private static boolean isContainPermission(List<String> sessionPermission,
            List<String> needPermission) {
        boolean isContain = true;
        for (String permission : needPermission) {
            boolean hasPermission = false;
            for (String myPermission : sessionPermission) {
                if (myPermission.equals(permission))
                    hasPermission = true;
            }
            if (!hasPermission) {
                isContain = false;
                break;
            }
        }
        return isContain;
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // OnRequestAPIListener Methods
    // /////////////////////////////////////////////////////////////////////////////////
    public interface OnRequestAPIListener {
        void onRequestAPIStart();

        void onRequestAPIProcess();

        void onRequestAPIError(String title, String message);

        void onRequestAPIFinish(Object object);
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // OnSessionOpenListener Methods
    // /////////////////////////////////////////////////////////////////////////////////
    private interface OnSessionOpenListener {

        void onSessionOpenError();

        void onSessionOpenFinish(Session session);
    }

}// end of class
