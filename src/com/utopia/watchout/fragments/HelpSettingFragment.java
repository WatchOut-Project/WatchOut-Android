
package com.utopia.watchout.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.utopia.watchout.R;
import com.utopia.watchout.helpers.ScreenHelper;

public class HelpSettingFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.setting_layout).setMinimumHeight(
                ScreenHelper.getContentHeight(getActivity()));

        return view;
    }

}
