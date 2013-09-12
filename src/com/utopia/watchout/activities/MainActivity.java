
package com.utopia.watchout.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Session;
import com.squareup.otto.Subscribe;
import com.squareup.otto.WOEventBus;
import com.utopia.watchout.R;
import com.utopia.watchout.adapters.SideListAdapter;
import com.utopia.watchout.adapters.SideListAdapter.SideItem;
import com.utopia.watchout.custom.view.WOToast;
import com.utopia.watchout.events.CurrentLocationEvent;
import com.utopia.watchout.events.DrawerIndicatorEvent;
import com.utopia.watchout.events.HelpCancelEvent;
import com.utopia.watchout.events.HelpConfirmEvent;
import com.utopia.watchout.fragments.MapBasedSNSFragment.OnMarkerClicked;
import com.utopia.watchout.fragments.MarkerInfoFragment;
import com.utopia.watchout.fragments.WOFragment;
import com.utopia.watchout.fragments.WOFragment.WOFragType;
import com.utopia.watchout.helpers.ScreenHelper;
import com.utopia.watchout.helpers.objects.GMMarkerInfo;
import com.utopia.watchout.model.STTable;
import com.utopia.watchout.model.STTable.Province;

import java.util.ArrayList;

public class MainActivity extends SherlockFragmentActivity implements OnMarkerClicked {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private SideListAdapter mSideAdapter;
    private ListView mListMenu;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {
            @Override
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mSideAdapter = new SideListAdapter(this, createSideItemList());
        mSideAdapter.setNotifyOnChange(true);
        mListMenu = (ListView) findViewById(R.id.left_drawer);
        mListMenu.setAdapter(mSideAdapter);
        mListMenu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetSideMenu(position);
                SideItem item = mSideAdapter.getItem(position);
                if (!item.isHeader()) {
                    setFragment(item.getFragType(), item.getTitle(),
                            new OnBackStackChangedListener() {
                                @Override
                                public void onBackStackChanged() {
                                    mDrawerLayout.closeDrawer(mListMenu);
                                }
                            });
                }
            }
        });
        ViewGroup.LayoutParams params = mListMenu.getLayoutParams();
        params.width = ScreenHelper.getNaviDrawerWidth(this);
        mListMenu.setLayoutParams(params);

        WOEventBus.getInstance().register(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WOEventBus.getInstance().unregister(this);
    }

    // For Facebook Session
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null)
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * ************************
     * Fragment Changes
     * *************************
     */
    private void setFragment(WOFragType type, String title,
                             final OnBackStackChangedListener listener) {
        final FragmentManager fm = getSupportFragmentManager();
        switch (type) {
            case NULL: // Map
                if (fm.getBackStackEntryCount() > 0) {
                    setListenerTiming(fm, listener, true);
                    fm.popBackStack(fm.getBackStackEntryAt(0).getId(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else
                    setListenerTiming(fm, listener, false);
                setContentBackground(false);
                break;

            case HELP_NOTI:
                DialogFragment dialogFrag = (DialogFragment) WOFragment.createWOFragment(type);
                dialogFrag.show(fm, dialogFrag.getClass().getSimpleName());
                listener.onBackStackChanged();
                break;

            default:
                boolean isChange = (!type.equals(getCurrentFragType())
                        || (type.equals(WOFragType.STATISTICS)
                        && !title.equals(getCurrentFragmentTitle())));
                if (isChange) {
                    fm.popBackStack();
                    setListenerTiming(fm, listener, true);
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment frag = WOFragment.createWOFragment(type);
                    Bundle args = new Bundle();
                    args.putString(WOFragment.FRAGMENT_TITLE, title);
                    frag.setArguments(args);
                    ft.add(R.id.content, frag, frag.getClass().getSimpleName());
                    ft.addToBackStack(frag.getClass().getSimpleName());
                    ft.commit();
                    setContentBackground(true);
                } else
                    setListenerTiming(fm, listener, false);
                break;
        }
    }

    @Override
    public void onOpenMakerWindow(GMMarkerInfo markerInfo) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment frag = WOFragment.createWOFragment(WOFragType.MARKER_INFO);
        ((MarkerInfoFragment) frag).setMarkerInfo(markerInfo);
        Bundle args = new Bundle();
        args.putString(WOFragment.FRAGMENT_TITLE, markerInfo.getLocationName());
        frag.setArguments(args);
        ft.add(R.id.content, frag, frag.getClass().getSimpleName());
        ft.addToBackStack(frag.getClass().getSimpleName());
        ft.commit();
    }

    private void setListenerTiming(final FragmentManager fm,
                                   final OnBackStackChangedListener listener,
                                   boolean isChanging) {

        if (listener == null)
            return;
        if (isChanging) {
            fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (listener != null)
                        listener.onBackStackChanged();
                    fm.removeOnBackStackChangedListener(this);
                }
            });

        } else
            listener.onBackStackChanged();
    }

    private String getCurrentFragmentTitle() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.content);
        if (frag == null)
            return null;
        else
            return frag.getArguments().getString(WOFragment.FRAGMENT_TITLE);
    }

    private WOFragType getCurrentFragType() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.content);
        if (frag == null)
            return WOFragType.NULL;
        else
            return WOFragment.getWOFragType(frag);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void setContentBackground(boolean isBlack) {
        if (!isBlack) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                findViewById(R.id.content).setBackground(null);
            else
                findViewById(R.id.content).setBackgroundDrawable(null);
        } else {
            findViewById(R.id.content).setBackgroundColor(
                    getResources().getColor(android.R.color.black));
        }
    }

    /**
     * ************************
     * Side Navigation Styling
     * *************************
     */
    private ArrayList<SideItem> createSideItemList() {
        ArrayList<SideItem> sideList = new ArrayList<SideItem>();
        sideList.add(new SideItem(true));
        sideList.add(new SideItem(getString(R.string.map), WOFragType.NULL).setSelected(true));
//        sideList.add(new SideItem(getString(R.string.help_upper), true));
//        sideList.add(new SideItem(getString(R.string.help_noti), WOFragType.HELP_NOTI));
//        sideList.add(new SideItem(getString(R.string.help_setting), WOFragType.HELP_SETTING));
//        sideList.add(new SideItem(getString(R.string.statistics_upper), true));

        // Province
//        STTable.makeProvTable(this);
//        for (Province prov : STTable.ProvinceTable)
//            sideList.add(new SideItem(prov));
        sideList.add(new SideItem(true));
        mSideList = sideList;
        return sideList;
    }

    private ArrayList<SideItem> mSideList = null;

    private void resetSideMenu(int position) {
        if (mSideList == null || position == 3)
            return;
        for (SideItem item : mSideList)
            item.setSelected(false);
        mSideList.get(position).setSelected(true);
        mSideAdapter.notifyDataSetChanged();
    }

    /**
     * ************************
     * Option Menu
     * *************************
     */

    private int DEFAULT_ACTIONBAR_OPTIONS = ActionBar.DISPLAY_USE_LOGO
            | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
            | ActionBar.DISPLAY_HOME_AS_UP;
    private int mActionBarOptions = DEFAULT_ACTIONBAR_OPTIONS;
    private CharSequence mTitle = null;

    private int DEFAULT_ACTIONBAR_MODE = ActionBar.NAVIGATION_MODE_STANDARD;
    private int mActionBarMode = DEFAULT_ACTIONBAR_MODE;

    private void backupActionBarStatus() {
        mActionBarOptions = getSupportActionBar().getDisplayOptions();
        mActionBarMode = getSupportActionBar().getNavigationMode();
        mTitle = getSupportActionBar().getTitle();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListMenu);

        if (drawerOpen) {
            backupActionBarStatus();
            getSupportActionBar().setDisplayOptions(DEFAULT_ACTIONBAR_OPTIONS);
            getSupportActionBar().setNavigationMode(DEFAULT_ACTIONBAR_MODE);
            getSupportActionBar().setTitle(this.getString(R.string.menu));
            getSupportActionBar().setHomeButtonEnabled(true);
            menu.clear();
        } else if (DEFAULT_ACTIONBAR_OPTIONS == getSupportActionBar().getDisplayOptions()) {
            getSupportActionBar().setDisplayOptions(mActionBarOptions);
            getSupportActionBar().setNavigationMode(mActionBarMode);
            getSupportActionBar().setTitle(mTitle);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Subscribe
    public void onIndicatorEnable(DrawerIndicatorEvent event) {
        mDrawerToggle.setDrawerIndicatorEnabled(event.getIsEnable());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.map));
        getSupportMenuInflater().inflate(R.menu.map, menu);
        backupActionBarStatus();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.abs__home:
            case android.R.id.home:
                if (WOFragType.MARKER_INFO.equals(getCurrentFragType()))
                    getSupportFragmentManager().popBackStack();
                else {
                    if (mDrawerLayout.isDrawerOpen(mListMenu))
                        mDrawerLayout.closeDrawer(mListMenu);
                    else
                        mDrawerLayout.openDrawer(mListMenu);
                }
                return true;
            case R.id.menu_current_location:
                WOEventBus.getInstance().post(new CurrentLocationEvent());
                return true;
            case R.id.menu_write:
                Intent intent = new Intent(this, PostActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ************************
     * OnBackPressed tryToFinish
     * *************************
     */
    private boolean mFinishApplication = false;
    private static Handler mUIHandler = new Handler();
    ;

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(mListMenu)) {
            mDrawerLayout.closeDrawer(mListMenu);
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
            if (fm.getBackStackEntryCount() == 0) {
                setContentBackground(false);
                resetSideMenu(1);
            }
        } else
            tryToFinish();

    }

    private void tryToFinish() {
        if (mFinishApplication) {
            finish();
        } else {
            WOToast.show(this, getString(R.string.please_press_back_button_again_to_exit_));
            mFinishApplication = true;
            mUIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFinishApplication = false;
                }
            }, 3000);
        }
    }

    /**
     * *************************
     * Help Noti Event
     * *************************
     */
    @Subscribe
    public void onHelpNotiConfirm(HelpConfirmEvent event) {
    }

    @Subscribe
    public void onHelpNotiCancel(HelpCancelEvent event) {
    }
}
