
package com.utopia.watchout.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.utopia.watchout.R;

public class GMIconHelper {

    public enum GMMarkerType {
        WATCHOUT, CHILD, ADULT, HELP
    }

    public static BitmapDescriptor getIcon(Context context, GMMarkerType type) {
        return getIcon(context, type, 1);
    }

    public static BitmapDescriptor getIcon(Context context, GMMarkerType type, int postCount) {
        switch (type) {
            case WATCHOUT:
                Bitmap bitmap = drawTextToBitmap(context, R.drawable.marker_watchout, ""
                        + postCount);
                return BitmapDescriptorFactory.fromBitmap(bitmap);
            case CHILD:
                return BitmapDescriptorFactory.fromResource(R.drawable.marker_child);
            case ADULT:
                return BitmapDescriptorFactory.fromResource(R.drawable.marker_adult);
            case HELP:

                break;

            default:
                break;
        }
        return null;
    }

    public static Bitmap drawTextToBitmap(Context context, int resId, String text) {

        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null)
            bitmapConfig = Bitmap.Config.ARGB_8888;
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(context.getResources().getColor(android.R.color.white));
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setTextSize((int) (12 * scale));

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 4;
        int y = (bitmap.getHeight() + bounds.height()) / 5;

        canvas.drawText(text, x * scale, y * scale, paint);

        return bitmap;
    }
}
