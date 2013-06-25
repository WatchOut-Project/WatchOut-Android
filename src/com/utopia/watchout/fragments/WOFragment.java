
package com.utopia.watchout.fragments;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class WOFragment {

    public static String FRAGMENT_TITLE = "fragment_title";

    public enum WOFragType {
        HELP_NOTI, HELP_SETTING, STATISTICS, MARKER_INFO,
        NULL,
    }

    private static HashMap<String, WOFragType> mFragTypeMatcher;
    static {
        // Content
        mFragTypeMatcher = new HashMap<String, WOFragType>();
        mFragTypeMatcher.put(HelpNotiFragment.class.getSimpleName(), WOFragType.HELP_NOTI);
        mFragTypeMatcher.put(HelpSettingFragment.class.getSimpleName(), WOFragType.HELP_SETTING);
        mFragTypeMatcher.put(StatisticsFragment.class.getSimpleName(), WOFragType.STATISTICS);
        mFragTypeMatcher.put(MarkerInfoFragment.class.getSimpleName(), WOFragType.MARKER_INFO);
    }

    public static WOFragType getWOFragType(Fragment fragment) {
        return mFragTypeMatcher.get(fragment.getClass().getSimpleName());
    }

    public static Fragment createWOFragment(WOFragType type) {
        switch (type) {
            case HELP_NOTI:
                return new HelpNotiFragment();
            case HELP_SETTING:
                return new HelpSettingFragment();
            case STATISTICS:
                return new StatisticsFragment();
            case MARKER_INFO:
                return new MarkerInfoFragment();
            default:
                return null;
        }
    }
}
