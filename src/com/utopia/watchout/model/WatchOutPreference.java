
package com.utopia.watchout.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Preference Helper
 * 
 * @author The Finest Artist
 */
public class WatchOutPreference {

    private static SharedPreferences mPref = null;
    private static Object mSingletonLock = new Object();

    private static SharedPreferences getInstance(Context ctx) {
        synchronized (mSingletonLock) {
            if (mPref != null)
                return mPref;

            if (ctx != null) {
                mPref = ctx.getSharedPreferences("watchoutRef",
                        Context.MODE_PRIVATE);
            }
            return mPref;
        }
    }

    public static void clearAuth(Context ctx) {
        Editor edit = getInstance(ctx).edit();
        edit.remove("fb_access_token");
        edit.commit();
    }

    public static String getFBAccessToken(Context ctx) {
        return getInstance(ctx).getString("fb_access_token", null);
    }

    public static void setFBAccessToken(Context ctx, String token) {
        Editor edit = getInstance(ctx).edit();
        edit.putString("fb_access_token", token);
        edit.commit();
    }

    public static boolean getIsSuggestGPSfromMap(Context ctx) {
        return getInstance(ctx).getBoolean("is_suggest_gps_from_map", false);
    }

    public static void setIsSuggestGPSfromMap(Context ctx, boolean isSuggested) {
        Editor edit = getInstance(ctx).edit();
        edit.putBoolean("is_suggest_gps_from_map", isSuggested);
        edit.commit();
    }

    public static boolean getIsSuggestGPSfromPlacePick(Context ctx) {
        return getInstance(ctx).getBoolean("is_suggest_gps_from_place_pick", false);
    }

    public static void setIsSuggestGPSfromPlacePick(Context ctx, boolean isSuggested) {
        Editor edit = getInstance(ctx).edit();
        edit.putBoolean("is_suggest_gps_from_place_pick", isSuggested);
        edit.commit();
    }

    private WatchOutPreference() {
    }

}// end of class
