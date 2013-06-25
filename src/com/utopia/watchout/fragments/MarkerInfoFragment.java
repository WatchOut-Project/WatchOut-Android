
package com.utopia.watchout.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.squareup.otto.WOEventBus;
import com.utopia.watchout.R;
import com.utopia.watchout.adapters.MapInfoListAdapter;
import com.utopia.watchout.events.DrawerIndicatorEvent;
import com.utopia.watchout.helpers.FBHelper;
import com.utopia.watchout.helpers.FBHelper.OnRequestAPIListener;
import com.utopia.watchout.helpers.ScreenHelper;
import com.utopia.watchout.helpers.objects.FBStatus;
import com.utopia.watchout.helpers.objects.GMMarkerInfo;

public class MarkerInfoFragment extends SherlockFragment implements OnRequestAPIListener {

    GMMarkerInfo mMarkerInfo = null;
    MapInfoListAdapter mAdapter;

    public void setMarkerInfo(GMMarkerInfo info) {
        mMarkerInfo = info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mMarkerInfo == null)
            getActivity().getSupportFragmentManager().popBackStack();

        View view = inflater.inflate(R.layout.marker_list, null, false);

        View background = view.findViewById(R.id.marker_background);
        background.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ScreenHelper
                .getContentHeight(getActivity())));
        background.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        mAdapter = new MapInfoListAdapter(getActivity());
        mAdapter.setNotifyOnChange(true);
        listView.setAdapter(mAdapter);

        for (long postId : mMarkerInfo.getPostIds())
            FBHelper.getStatusData(getActivity(), this, postId);

        return view;
    }

    Menu mOptionMenu = null;
    MenuInflater mInflater = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        WOEventBus.getInstance().post(new DrawerIndicatorEvent(false));

        mOptionMenu = menu;
        mInflater = inflater;

        SherlockFragmentActivity activity = (SherlockFragmentActivity) getSherlockActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        if (mLocation != null)
            actionBar.setTitle(mLocation);
    }

    private String mLocation = null;
    private boolean isLocationUpdated = false;

    private void onCreateOptionsMenu(String location) {
        mLocation = location;
        if (!isLocationUpdated)
            onCreateOptionsMenu(mOptionMenu, mInflater);
        isLocationUpdated = true;
    }

    /**
     * OnRequestAPIListener
     */

    @Override
    public void onRequestAPIStart() {

    }

    @Override
    public void onRequestAPIProcess() {

    }

    @Override
    public void onRequestAPIError(String title, String message) {

    }

    @Override
    public void onRequestAPIFinish(Object object) {
        FBStatus status = (FBStatus) object;
        mAdapter.add(status);
        mAdapter.notifyDataSetChanged();
        onCreateOptionsMenu(status.getPlaceName());

    }
}
