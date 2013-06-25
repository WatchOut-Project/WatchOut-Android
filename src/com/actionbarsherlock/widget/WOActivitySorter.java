
package com.actionbarsherlock.widget;

import android.content.Intent;

import com.actionbarsherlock.widget.ActivityChooserModel.ActivityResolveInfo;
import com.actionbarsherlock.widget.ActivityChooserModel.ActivitySorter;
import com.actionbarsherlock.widget.ActivityChooserModel.HistoricalRecord;
import com.utopia.watchout.helpers.PackagesHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WOActivitySorter implements ActivitySorter {

    private final Map<String, ActivityResolveInfo> mPackageNameToActivityMap =
            new HashMap<String, ActivityResolveInfo>();

    public void sort(Intent intent, List<ActivityResolveInfo> activities,
            List<HistoricalRecord> historicalRecords) {
        Map<String, ActivityResolveInfo> packageNameToActivityMap =
                mPackageNameToActivityMap;
        packageNameToActivityMap.clear();

        final int activityCount = activities.size();
        for (int i = 0; i < activityCount; i++) {
            ActivityResolveInfo activity = activities.get(i);
            activity.weight = 0.0f;
            String packageName = activity.resolveInfo.activityInfo.packageName;
            // predefined weight.

            if (packageName.contains(PackagesHelper.KAKAOTALK))
                activity.weight = 109.0f;
            else if (packageName.contains(PackagesHelper.FACEBOOK))
                activity.weight = 108.0f;
            else if (packageName.contains(PackagesHelper.TWITTER))
                activity.weight = 107.0f;
            else if (packageName.contains(PackagesHelper.KAKAOSTORY))
                activity.weight = 106.0f;
            else if (packageName.contains(PackagesHelper.GOOGLE_PLUS))
                activity.weight = 99.0f;
            else if (packageName.contains(PackagesHelper.GMAIL))
                activity.weight = 98.0f;
            else if (packageName.contains(PackagesHelper.PINTEREST))
                activity.weight = 97.0f;
            else if (packageName.contains(PackagesHelper.TUMBLR))
                activity.weight = 96.0f;

            packageNameToActivityMap.put(packageName, activity);
        }
        Collections.sort(activities);
    }

}// end of class
