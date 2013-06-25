
package com.utopia.watchout.utils;

import android.view.View;

/**
 * Helper class for Watch Out Adapters
 * 
 * @author The Finest Artist
 */
public class Utils {

    public static View findViewByIdInTag(View view, int id) {
        if (view == null)
            return null;

        View result = (View) view.getTag(id);

        if (result == null)
            result = view.findViewById(id);

        view.setTag(id, result);
        return result;
    }

    private Utils() {
    }

}// end of class
