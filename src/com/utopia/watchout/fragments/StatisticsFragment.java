
package com.utopia.watchout.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.nineoldandroids.animation.ValueAnimator;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.utopia.watchout.R;
import com.utopia.watchout.WODebug;
import com.utopia.watchout.helpers.ScreenHelper;
import com.utopia.watchout.model.STTable;
import com.utopia.watchout.model.STTable.Local;
import com.utopia.watchout.model.STTable.Province;
import com.utopia.watchout.model.STTable.ProvinceType;

public class StatisticsFragment extends SherlockFragment implements
        OnNavigationListener {

    String mTitle = null;
    Province mProv = null;
    int mItemPosition = 0;
    ImageView mMainImage;
    LinearLayout mStatisticsLaytout;
    TextView mChildCount;
    View mChildBar;
    TextView mAdolescentCount;
    View mAdolescentBar;
    TextView mYoungsterCount;
    View mYoungsterBar;
    TextView mAdultCount;
    View mAdultBar;
    TextView mSeniorCount;
    View mSeniorBar;
    ImageView mBGImage;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mTitle == null)
            mTitle = (String) getArguments().getString(
                    WOFragment.FRAGMENT_TITLE);
        if (mProv == null)
            mProv = STTable.getProvince(activity, mTitle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container,
                false);
        view.findViewById(R.id.st_layout).setMinimumHeight(
                ScreenHelper.getContentHeight(getActivity()));
        mMainImage = (ImageView) view.findViewById(R.id.st_main_image);

        mStatisticsLaytout = (LinearLayout) view
                .findViewById(R.id.st_statistics);
        mChildCount = (TextView) view.findViewById(R.id.st_child_count);
        mChildBar = view.findViewById(R.id.st_child_bar);
        mAdolescentCount = (TextView) view
                .findViewById(R.id.st_adolescent_count);
        mAdolescentBar = view.findViewById(R.id.st_adolescent_bar);
        mYoungsterCount = (TextView) view.findViewById(R.id.st_youngster_count);
        mYoungsterBar = view.findViewById(R.id.st_youngster_bar);
        mAdultCount = (TextView) view.findViewById(R.id.st_adult_count);
        mAdultBar = view.findViewById(R.id.st_adult_bar);
        mSeniorCount = (TextView) view.findViewById(R.id.st_senior_count);
        mSeniorBar = view.findViewById(R.id.st_senior_bar);

        mBGImage = (ImageView) view.findViewById(R.id.st_main_image_bg);
        setBG();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetView(mProv.getType(), mItemPosition);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        WODebug.LogDebug("onCraeateOptionMenu : StatisticsFragment");

        SherlockFragmentActivity activity = (SherlockFragmentActivity) getSherlockActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

        final String[] dropdownValues = mProv.getLocalNameList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, this);

        inflater.inflate(R.menu.statistics, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) shareItem
                .getActionProvider();
        shareActionProvider.setShareHistoryFileName(null);

        // Share Image of map and text of statistics data
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "data text");
        shareIntent.setType("text/plain");
        shareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        mItemPosition = itemPosition;
        resetView(mProv.getType(), mItemPosition);
        return false;
    }

    private void setBG() {
        switch (mProv.getType()) {
            case SouthKorea:
                mBGImage.setBackgroundResource(R.drawable.southkorea);
                break;
            case Seoul:
                mBGImage.setBackgroundResource(R.drawable.seoul_bg);
                break;
            case Busan:
                mBGImage.setBackgroundResource(R.drawable.busan_bg);
                break;
            case Daegu:
                mBGImage.setBackgroundResource(R.drawable.daegu_bg);
                break;
            case Incheon:
                mBGImage.setBackgroundResource(R.drawable.incheon_bg);
                break;
            case Gwangju:
                mBGImage.setBackgroundResource(R.drawable.gwangju_bg);
                break;
            case Daejeon:
                mBGImage.setBackgroundResource(R.drawable.daejeon_bg);
                break;
            case Ulsan:
                mBGImage.setBackgroundResource(R.drawable.ulsan_bg);
                break;
            case SeJong:
                mBGImage.setBackgroundResource(R.drawable.sejong_0);
                break;
            case Gyeonggi:
                mBGImage.setBackgroundResource(R.drawable.gyeonggi_bg);
                break;
            case Gangwon:
                mBGImage.setBackgroundResource(R.drawable.gangwon_bg);
                break;
            case Chungcheongbuk:
                mBGImage.setBackgroundResource(R.drawable.chungbuk_bg);
                break;
//            case Chungcheongnam:
//                mBGImage.setBackgroundResource(R.drawable.c);
//                break;
            case Jeollabuk:
                mBGImage.setBackgroundResource(R.drawable.jeonbuk_bg);
                break;
            case Jeollanam:
                mBGImage.setBackgroundResource(R.drawable.jeonnam_bg);
                break;
            case Gyeongsangbuk:
                mBGImage.setBackgroundResource(R.drawable.gyeongbuk_bg);
                break;
            case Gyeongsangnam:
                mBGImage.setBackgroundResource(R.drawable.gyeongnam_bg);
                break;
            case Jeju:
                mBGImage.setBackgroundResource(R.drawable.jeju_bg);
                break;
            default:
                break;
        }
    }

    public void resetView(ProvinceType type, int position) {

        Local local = mProv.getLocalList()[position];
        mMainImage.setImageResource(local.getDrawableId());
        float height = mStatisticsLaytout.getHeight()
                - mChildCount.getHeight()
                - mStatisticsLaytout.findViewById(R.id.st_child_name)
                .getHeight();

        float local_max = (float) 0.0;

        if (position == 0) {
            for (int j = 0; j < 5; j++) {
                if (local_max < local.getCount(j))
                    local_max = local.getCount(j);
            }
        } else {
            for (int i = 1; i < mProv.getLocalList().length; i++) {
                Local eachlocal = mProv.getLocalList()[i];
                for (int j = 0; j < 5; j++) {
                    if (local_max < eachlocal.getCount(j))
                        local_max = eachlocal.getCount(j);
                }
            }
        }

        ValueAnimator anim1 = ValueAnimator.ofInt(0,
                (int) ((float) (local.getCount(0)) / local_max * height));
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mChildBar
                        .getLayoutParams();
                layoutParams.height = val;
                mChildBar.setLayoutParams(layoutParams);

            }
        });
        anim1.setDuration(500);
        anim1.start();
        mChildCount.setText(String.valueOf(local.getCount(0)));

        ValueAnimator anim2 = ValueAnimator.ofInt(0,
                (int) ((float) (local.getCount(1)) / local_max * height));
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAdolescentBar
                        .getLayoutParams();
                layoutParams.height = val;
                mAdolescentBar.setLayoutParams(layoutParams);
            }
        });
        anim2.setDuration(500);
        anim2.start();
        mAdolescentCount.setText(String.valueOf(local.getCount(1)));

        ValueAnimator anim3 = ValueAnimator.ofInt(0,
                (int) ((float) (local.getCount(2)) / local_max * height));
        anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mYoungsterBar
                        .getLayoutParams();
                layoutParams.height = val;
                mYoungsterBar.setLayoutParams(layoutParams);
            }
        });
        anim3.setDuration(500);
        anim3.start();
        mYoungsterCount.setText(String.valueOf(local.getCount(2)));

        ValueAnimator anim4 = ValueAnimator.ofInt(0,
                (int) ((float) (local.getCount(3)) / local_max * height));
        anim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAdultBar
                        .getLayoutParams();
                layoutParams.height = val;
                mAdultBar.setLayoutParams(layoutParams);
            }
        });
        anim4.setDuration(500);
        anim4.start();
        mAdultCount.setText(String.valueOf(local.getCount(3)));

        ValueAnimator anim5 = ValueAnimator.ofInt(0,
                (int) ((float) (local.getCount(4)) / local_max * height));
        anim5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mSeniorBar
                        .getLayoutParams();
                layoutParams.height = val;
                mSeniorBar.setLayoutParams(layoutParams);
            }
        });
        anim5.setDuration(500);
        anim5.start();
        mSeniorCount.setText(String.valueOf(local.getCount(4)));
    }

}
