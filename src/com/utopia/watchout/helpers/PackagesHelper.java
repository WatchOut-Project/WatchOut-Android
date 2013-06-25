
package com.utopia.watchout.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class PackagesHelper {

    public static String KAKAOTALK = "com.kakao.talk";
    public static String KAKAOSTORY = "com.kakao.story";
    public static String FACEBOOK = "com.facebook.katana";
    public static String TWITTER = "com.twitter.android";
    public static String GOOGLE_PLUS = "com.google.android.apps.plus";
    public static String GMAIL = "com.google.android.gm";
    public static String PINTEREST = "com.pinterest";
    public static String TUMBLR = "com.tumblr";
    public static String FANCY = "com.thefancy.app";
    public static String FLIPBOARD = "flipboard.app";

    // com.android.email
    // com.android.mms

    public static boolean isInstalled(Context context, String package_name) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo pack : packages) {
            if (package_name != null && package_name.equals(pack.packageName))
                return true;
        }
        return false;
    }
}
