
package com.utopia.watchout.helpers.json;

import android.util.Log;

import com.utopia.watchout.WODebug;
import com.utopia.watchout.helpers.objects.FBLocationPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FBLocationPostJson {

    private static final String TAG = "Json";

    // JSON Node names
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_DISTANCE_METERS = "distance_meters";

    public static FBLocationPost[] getArrayData(JSONObject jsonObject) {

        FBLocationPost[] location_post_array = null;

        try {
            JSONArray jsonArray = null;
            if (jsonObject.has(TAG_DATA))
                jsonArray = jsonObject.getJSONArray(TAG_DATA);
            else
                return null;

            location_post_array = new FBLocationPost[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                long id = 0;
                double latitude = 0, longitude = 0, distance_meters = 0;
                if (jsonObj.has(TAG_ID))
                    id = jsonObj.getLong(TAG_ID);
                if (jsonObj.has(TAG_LATITUDE))
                    latitude = jsonObj.getDouble(TAG_LATITUDE);
                if (jsonObj.has(TAG_LONGITUDE))
                    longitude = jsonObj.getDouble(TAG_LONGITUDE);
                if (jsonObj.has(TAG_DISTANCE_METERS))
                    distance_meters = jsonObj.getDouble(TAG_DISTANCE_METERS);
                FBLocationPost location_post = new FBLocationPost(id, latitude, longitude,
                        distance_meters);
                location_post_array[i] = location_post;
            }

        } catch (JSONException e) {
            if (WODebug.DEBUG)
                Log.e(TAG, e.getMessage());
        }

        return location_post_array;
    }
}
