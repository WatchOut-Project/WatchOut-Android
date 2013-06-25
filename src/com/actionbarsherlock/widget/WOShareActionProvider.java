
package com.actionbarsherlock.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.actionbarsherlock.R;

public class WOShareActionProvider extends ShareActionProvider {

    /**
     * The default name for storing share history.
     */

    public interface OnShareMenuOpenListener {
        public void onShowSubMenu();
    }

    private ActivityChooserView mChooserView;
    private final Context mContext;
    private OnShareMenuOpenListener mShareMenuOpenListener;

    public WOShareActionProvider(Context context) {
        super(context);
        mContext = context;
        setOnShareTargetSelectedListener(new OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                mContext.startActivity(intent);
                return true;
            }
        });
    };

    public void setOnShareMenuOpenListener(OnShareMenuOpenListener listener) {
        mShareMenuOpenListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateActionView() {
        // Create the view and set its data model.
        ActivityChooserModel dataModel = ActivityChooserModel.get(mContext, null);
        dataModel.setActivitySorter(new WOActivitySorter());
        mChooserView = new ActivityChooserView(mContext);
        mChooserView.setActivityChooserModel(dataModel);

        // Lookup and set the expand action icon.
        // 0x7f010026
        TypedValue outTypedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(com.utopia.watchout.R.attr.actionModeShareDrawable,
                outTypedValue, true);
        Drawable drawable = mContext.getResources().getDrawable(outTypedValue.resourceId);
        mChooserView.setExpandActivityOverflowButtonDrawable(drawable);
        mChooserView.setProvider(this);

        // Set content description.
        mChooserView.setDefaultActionButtonContentDescription(
                R.string.abs__shareactionprovider_share_with_application);
        mChooserView.setExpandActivityOverflowButtonContentDescription(
                R.string.abs__shareactionprovider_share_with);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            mChooserView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        else
            setSubUiVisibilityListener(mOnSubUiVisibilityListener);
        return mChooserView;
    }

    private final OnGlobalLayoutListener mOnGlobalLayoutListener = new OnGlobalLayoutListener() {

        @SuppressWarnings("deprecation")
        @Override
        public void onGlobalLayout() {
            if (mChooserView.isShowingPopup()) {
                // invoke some callback here.
                mChooserView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        mOnGlobalLayoutListener);
                if (mShareMenuOpenListener != null)
                    mShareMenuOpenListener.onShowSubMenu();
            }
        }
    };

    private SubUiVisibilityListener mOnSubUiVisibilityListener = new SubUiVisibilityListener() {

        @Override
        public void onSubUiVisibilityChanged(boolean isVisible) {
            if (isVisible) {
                if (mShareMenuOpenListener != null)
                    mShareMenuOpenListener.onShowSubMenu();
                setSubUiVisibilityListener(null);
            }
        }
    };

}// end of class
