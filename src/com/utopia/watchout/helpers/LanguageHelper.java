
package com.utopia.watchout.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Locale;

public class LanguageHelper {

    public static String CODE_KOREAN = "ko";
    public static String CODE_JAPANESE = "jp";
    public static String CODE_ENGLISH = "en";

    private static SharedPreferences mPref = null;
    private static Object mSingletonLock = new Object();

    private static SharedPreferences getInstance(Context ctx) {
        synchronized (mSingletonLock) {
            if (mPref != null)
                return mPref;

            if (ctx != null) {
                mPref = ctx.getSharedPreferences("vinglef",
                        Context.MODE_PRIVATE);
            }
            return mPref;
        }
    }

    public static void setDefault(Context ctx, String code, String name) {
        Editor edit = getInstance(ctx).edit();
        edit.putString("language_name", name);
        edit.putString("language_code", code);
        edit.commit();
    }

    public static String getDefaultCode(Context ctx) {
        String code = getInstance(ctx).getString("language_code", null);

        if (code == null) {
            code = Locale.getDefault().getLanguage();
            // Note that Java uses several deprecated two-letter codes.
            // The Hebrew ("he") language code is rewritten as "iw",
            // Indonesian ("id") as "in", and Yiddish ("yi") as "ji".
            // This rewriting happens even if you construct your own Locale
            // object, not just for instances returned by the various lookup
            // methods.

            // replace depreciated two-letter codes.
            code = code.replace("iw", "iw");
            code = code.replace("in", "id");
            code = code.replace("ji", "vi");
        }
        return code;
    }

    public static String getDefaultName(Context ctx) {
        String name = getInstance(ctx).getString("language_name", null);

        if (name == null)
            name = Locale.getDefault().getDisplayLanguage();

        return name;
    }
}
