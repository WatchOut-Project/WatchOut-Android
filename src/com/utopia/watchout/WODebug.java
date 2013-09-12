
package com.utopia.watchout;

import android.content.Context;
import android.util.Log;

import org.holoeverywhere.widget.Toast;

public class WODebug {

    public static final boolean DEBUG = true;
    public static final String TAG = "WatchOut";

    private WODebug() {
    }

    public static void LogError(String log) {
        if (DEBUG)
            Log.e(TAG, log);
    }

    public static void LogDebug(String log) {
        if (DEBUG)
            Log.d(TAG, log);
    }

    public static void LogDebug(String tag, String log) {
        if (DEBUG)
            Log.d(tag, log);
    }

    public static void LogInfo(String log) {
        if (DEBUG)
            Log.i(TAG, log);
    }

    public static void Toast(Context context, String log) {
        if (DEBUG)
            Toast.makeText(context, log, Toast.LENGTH_SHORT).show();
    }
}
