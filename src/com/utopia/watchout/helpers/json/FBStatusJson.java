
package com.utopia.watchout.helpers.json;

import android.util.Log;

import com.utopia.watchout.WODebug;
import com.utopia.watchout.helpers.objects.FBImage;
import com.utopia.watchout.helpers.objects.FBStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FBStatusJson {

    private static final String TAG = "GraphObject";

    // Graph Object Node names
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_IMAGES = "images";
    private static final String TAG_HEIGHT = "height";
    private static final String TAG_WIDTH = "width";
    private static final String TAG_SOURCE = "source";
    private static final String TAG_PLACE = "place";
    private static final String TAG_CREATED_TIME = "created_time";

    private static SimpleDateFormat date_format = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss+SSSS", Locale.US);

    public static FBStatus getData(JSONObject jsonObject) {
        FBStatus status = null;

        try {
            long id = 0, createdTimeMillis = 0;
            String name = null, message = null, placeName = null, createdTime = null;
            FBImage[] images = null;

            if (jsonObject.has(TAG_ID))
                id = jsonObject.getLong(TAG_ID);

            if (jsonObject.has(TAG_NAME))
                name = jsonObject.getString(TAG_NAME);

            if (jsonObject.has(TAG_MESSAGE))
                message = jsonObject.getString(TAG_MESSAGE);

            if (jsonObject.has(TAG_PLACE)) {
                JSONObject placeObject = jsonObject.getJSONObject(TAG_PLACE);
                if (placeObject.has(TAG_NAME))
                    placeName = placeObject.getString(TAG_NAME);
            }

            if (jsonObject.has(TAG_IMAGES)) {
                JSONArray imageArray = jsonObject.getJSONArray(TAG_IMAGES);
                images = new FBImage[imageArray.length()];
                for (int i = 0; i < images.length; i++) {
                    JSONObject imageObject = imageArray.getJSONObject(i);
                    images[i] = new FBImage(imageObject.getInt(TAG_HEIGHT),
                            imageObject.getInt(TAG_WIDTH), imageObject.getString(TAG_SOURCE));
                }
            }

            if (jsonObject.has(TAG_CREATED_TIME)) {
                createdTime = jsonObject.getString(TAG_CREATED_TIME);
                try
                {
                    createdTimeMillis = date_format.parse(createdTime).getTime();
                } catch (Exception e) {
                    if (WODebug.DEBUG)
                        Log.e(TAG, e.getMessage());
                }
            }

            status = new FBStatus(id, name, message, placeName, images, createdTimeMillis);
        } catch (JSONException e) {
            if (WODebug.DEBUG)
                Log.e(TAG, e.getMessage());
        }

        return status;
    }
}
